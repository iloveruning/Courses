package com.hfut.glxy.service.impl;

import com.hfut.glxy.dto.PageResult;
import com.hfut.glxy.entity.*;
import com.hfut.glxy.mapper.*;
import com.hfut.glxy.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/22 19:56 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseDao courseDao;
    @Resource
    private ManagerDao managerDao;
    @Resource
    private CourseGroupDao courseGroupDao;
    @Resource
    private TeacherDao teacherDao;
    @Resource
    private PictureDao pictureDao;
    @Resource
    private CourseGroup_CourseDao courseGroup_courseDao;
    @Resource
    private Teacher_CourseDao teacher_courseDao;
    @Resource
    private Manager_CourseDao manager_courseDao;
    @Resource
    private Course_PictureDao course_pictureDao;
    @Resource
    private CourseGroup_TeacherDao courseGroup_teacherDao;

    /**
     * @param course
     * @return
     * @Date 2017/11/23 15:17
     * @author students_ManagementSchool
     * @condition 增加课程，并进行事务管理，因为在此添加过程中，涉及到对多个表的操作，必须保证事务的完整性和一致性
     * @since JDK 1.8
     */
    @Override
    @Transactional
    public int addCourse(Course course, Manager manager) throws Exception {

        /*String courseGroup_id = course.getCourseGroup_id();*/
        /*String teacher_id = course.getTeacher_id();*/

        /*String [] teacher_ids=course.getTeacher_ids();*/

        String picture_id = course.getPicture_id();
        String course_id=course.getId();

        //检验课程组是否存在，防止伪造数据带来的不可控后果
        /*CourseGroup courseGroup = courseGroupDao.queryCourseGroupById(courseGroup_id);
        if (courseGroup == null) {
            return 0;
        }*/

        /*for (String teacher_id:teacher_ids){
            //判断负责的教师是否存在，用于防止数据伪造
            Teacher teacher = teacherDao.queryTeacherById(teacher_id);
            if (teacher == null) {
                return 0;
            }
        }*/

        int addCourseSuccess;
        int addManagerSuccess;
        int addCourseGroup_courseSuccess;
        int addTeacher_courseSuccess;
        int addManager_courseSuccess;
        int addCourse_pictureSuccess;

        //将课程添加到课程表
        addCourseSuccess = courseDao.addCourse(course);
        //将给课程指定的管理员增加到管理员表
        addManagerSuccess = managerDao.addManager(manager);

        if (addCourseSuccess == 0 || addManagerSuccess == 0) {
            //进行回滚
            throw new RuntimeException("添加失败");
        }

        //将课程组与课程建立关联并增加到相应的关联表
        /*addCourseGroup_courseSuccess = courseGroup_courseDao.addRelation(courseGroup_id, course.getId());

        if (addCourseGroup_courseSuccess == 0) {
            //进行回滚
            throw new RuntimeException("添加失败");
        }

        for (String teacher_id:teacher_ids){
            //将教师与课程建立关联并增加到相应的关联表
            addTeacher_courseSuccess = teacher_courseDao.addRelation(teacher_id, course.getId());

            if (addTeacher_courseSuccess == 0) {
                //进行回滚
                throw new RuntimeException("添加失败");
            }
        }*/

        //将管理员与课程建立关联并增加到相应的关联表
        addManager_courseSuccess = manager_courseDao.addRelation(manager.getId(), course.getId());
        if (addManager_courseSuccess == 0) {
            //进行回滚
            throw new RuntimeException("添加失败");
        }

        //将教师与课程组建立关联并增加到相应的关联表
        /*for(String teacher_id:teacher_ids){
            int addCourseGroup_teacherSuccess=courseGroup_teacherDao.addCourseGroup_Teacher(courseGroup_id,teacher_id);
            if (addCourseGroup_teacherSuccess!=1){
                throw new RuntimeException("课程组教师关联失败");
            }
        }*/

        //未上传图片
        if (picture_id == null) {
            return 1;
        }

        //判断上传的图片是否真的存在，若不存在，说明数据伪造，拒绝请求
        Picture picture = pictureDao.queryPictureById(picture_id);
        if (picture == null) {
            throw new RuntimeException("图片不存在");
        }

        //向课程组表-图片中添加
        addCourse_pictureSuccess = course_pictureDao.addCourse_picture(course_id, picture_id);
        if (addCourse_pictureSuccess == 0) {
            //进行回滚
            throw new RuntimeException("添加失败");
        }

        return 1;
    }

    /**
     * @param course
     * @param manager
     * @return
     * @Date 2017/11/30 17:18
     * @author students_ManagementSchool
     * @condition 更新课程信息
     * @since JDK 1.8
     */
    @Override
    @Transactional
    public int updateCourse(Course course, Manager manager) throws Exception {

        String picture_id = course.getPicture_id();
        String course_id = course.getId();

        String manager_id = manager_courseDao.queryManager(course_id);
        manager.setId(manager_id);

        int updateCourseSuccess;
        int updateManagerSuccess;
        //更新课程表
        updateCourseSuccess = courseDao.updateCourse(course);
        //更新管理员表
        updateManagerSuccess = managerDao.updateManager(manager);
        if (updateCourseSuccess == 0 || updateManagerSuccess == 0) {
            //进行回滚
            throw new RuntimeException("更新失败");
        }

        //未上传图片
        if (picture_id == null) {
            return 1;
        }

        //未更新图片
        String currentPictureId = course_pictureDao.getCurrentPictureIdByCourse(course_id);
        if (picture_id.equals(currentPictureId)) {
            return 1;
        }

        //已更新图片
        int deleteCourse_pictureSuccess = course_pictureDao.deleteCourse_picture(course_id);
        int updateCourse_pictureSuccess = course_pictureDao.addCourse_picture(course_id, picture_id);

        if (deleteCourse_pictureSuccess == 0 || updateCourse_pictureSuccess == 0) {
            //进行回滚
            throw new RuntimeException("更新失败");
        }

        return 1;
    }

    /**
         *
         * @Date 2017/12/6 20:46
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition   将课程放入回收站
    */
    @Override
    @Transactional
    public int putToDustbin(String course_id) throws Exception {

        try{
            //获取当前课程管理员的id
            String manager_id=manager_courseDao.queryManager(course_id);

            String courseGroup_id=courseGroup_courseDao.queryCourseGroupByCourse(course_id);
            if (courseGroup_id==null){
                return 0;
            }

            //从课程表删除该课程
            courseDao.putToDustbin(course_id);
            //将该课程从该课程组移除
            courseGroup_courseDao.deleteRelationByCourse(course_id);
            //将该课程与管理员解除关系
            manager_courseDao.deleteRelationByCourse(course_id);
            //将该课程管理员从管理员表删除
            managerDao.putToDustbin(manager_id);
            //获取负责该课程的所有教师
            String [] teacher_ids=teacher_courseDao.getAllTeachersByCourse(course_id);
            //删除负责该课程的所有教师
            teacher_courseDao.deleteRelationByCourse(course_id);
            //判断某教师是否还教授输与该课程组的其他课程
            for (String teacher_id:teacher_ids){
                int count=0;
                List<String> course_ids;
                course_ids=teacher_courseDao.getAllCoursesByTeacher(teacher_id);
                for (String courseId:course_ids){
                    String courseGroupId=courseGroup_courseDao.queryCourseGroupByCourse(courseId);
                    if (courseGroupId.equals(courseGroup_id)){
                        count++;
                    }
                }
                //该教师不再负责任何属于该课程组的课程
                if (count==0){
                    int deleteCourseGroup_teacherSuccess=courseGroup_teacherDao.deleteRelation(courseGroup_id,teacher_id);
                    if (deleteCourseGroup_teacherSuccess!=1){
                        throw new RuntimeException("出现异常，请迅速支援！！！");
                    }
                }else{
                    //不执行任何操作
                }
            }

        }catch(RuntimeException e){
            //进行回滚
            throw new RuntimeException("删除失败");
        }

        return 1;
    }

    /**
         *
         * @Date 2017/12/2 10:54
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition 根据课程id获取课程
    */
    @Override
    public Map queryCourseById(String course_id) throws Exception{

        Map map=new HashMap();
        List<Teacher> teachers=new ArrayList<>();

        //查课程
        Course course=courseDao.queryCourseById(course_id);
        //查图片
        String picture_id=course_pictureDao.getCurrentPictureIdByCourse(course_id);
        Picture picture=pictureDao.queryPictureById(picture_id);
        //查课程组
        String courseGroup_id=courseGroup_courseDao.queryCourseGroupByCourse(course_id);
        CourseGroup courseGroup=courseGroupDao.queryCourseGroupById(courseGroup_id);
        //查教师
        /*String teacher_id=teacher_courseDao.getCurrentTeacherIdByCourse(course_id);*/
        String[] teacher_ids=teacher_courseDao.getCurrentTeacherIdByCourse(course_id);
        for (String teacher_id:teacher_ids){
            Teacher teacher=teacherDao.queryTeacherById(teacher_id);
            teachers.add(teacher);
        }
        //查管理员
        String manager_id=manager_courseDao.queryManager(course_id);
        Manager manager=managerDao.queryManagerById(manager_id);

        map.put("course",course);
        map.put("manager",manager);
        map.put("picture",picture);
        map.put("courseGroup",courseGroup);
        map.put("teachers",teachers);

        return map;
    }

    /**
     * @return
     * @Date 2017/11/25 16:29
     * @author students_ManagementSchool
     * @condition 获取全部课程
     * @since JDK 1.8
     */
    @Override
    public List<Course> getAllCourses() {

        /*List<Map> maps=new ArrayList<>();*/
        List<Course> courses= courseDao.getAllCourses();

        /*List<Course> courses= courseDao.queryCourseByPage(30,11);*/

        if (courses.isEmpty()){
            return null;
        }

        /*for (Course course:courses){

            Map map=new HashMap();
            List<Teacher> teachers=new ArrayList<>();

            String course_id=course.getId();
            //查图片
            String picture_id=course_pictureDao.getCurrentPictureIdByCourse(course_id);
            Picture picture=pictureDao.queryPictureById(picture_id);
            //查课程组
            String courseGroup_id=courseGroup_courseDao.queryCourseGroupByCourse(course_id);
            CourseGroup courseGroup=courseGroupDao.queryCourseGroupById(courseGroup_id);
            //查教师
            *//*String teacher_id=teacher_courseDao.getCurrentTeacherIdByCourse(course_id);*//*
            String[] teacher_ids=teacher_courseDao.getCurrentTeacherIdByCourse(course_id);
            for (String teacher_id:teacher_ids){
                Teacher teacher=teacherDao.queryTeacherById(teacher_id);
                teachers.add(teacher);
            }
            *//*Teacher teacher=teacherDao.queryTeacherById(teacher_id);*//*
            //查管理员
            String manager_id=manager_courseDao.queryManager(course_id);
            Manager manager=managerDao.queryManagerById(manager_id);

            map.put("course",course);
            map.put("manager",manager);
            map.put("picture",picture);
            map.put("courseGroup",courseGroup);
            map.put("teachers",teachers);

            maps.add(map);
        }*/

        return courses;
    }


    /**
         *
         * @Date 2018/1/15 22:34
         * @author students_ManagementSchool
         * @param startPage
         * @param pageSize
         * @return
         * @since JDK 1.8
         * @condition  分页查询课程
    */
    @Override
    public PageResult<Map> getCoursesByPage(int startPage, int pageSize) throws Exception{

        PageResult<Map> coursePageResult=new PageResult<>();

        int totalCount=courseDao.getCourseTotalCount();
        coursePageResult.setiTotalDisplayRecords(totalCount);
        coursePageResult.setiTotalRecords(totalCount);

        List<Map> maps=new ArrayList<>();
        /*List<Course> courses= courseDao.getAllCourses();*/

        List<Course> courses= courseDao.queryCourseByPage(startPage,pageSize);

        if (courses.isEmpty()){
            return null;
        }

        for (Course course:courses){

            Map map=new HashMap();
            List<Teacher> teachers=new ArrayList<>();

            String course_id=course.getId();
            //查图片
            String picture_id=course_pictureDao.getCurrentPictureIdByCourse(course_id);
            Picture picture=pictureDao.queryPictureById(picture_id);
            //查课程组
            String courseGroup_id=courseGroup_courseDao.queryCourseGroupByCourse(course_id);
            CourseGroup courseGroup=courseGroupDao.queryCourseGroupById(courseGroup_id);
            //查教师
            /*String teacher_id=teacher_courseDao.getCurrentTeacherIdByCourse(course_id);*/
            String[] teacher_ids=teacher_courseDao.getCurrentTeacherIdByCourse(course_id);
            for (String teacher_id:teacher_ids){
                Teacher teacher=teacherDao.queryTeacherById(teacher_id);
                teachers.add(teacher);
            }
            /*Teacher teacher=teacherDao.queryTeacherById(teacher_id);*/
            //查管理员
            String manager_id=manager_courseDao.queryManager(course_id);
            Manager manager=managerDao.queryManagerById(manager_id);

            map.put("course",course);
            map.put("manager",manager);
            map.put("picture",picture);
            map.put("courseGroup",courseGroup);
            map.put("teachers",teachers);

            maps.add(map);
        }

        coursePageResult.setData(maps);


        return coursePageResult;
    }

}

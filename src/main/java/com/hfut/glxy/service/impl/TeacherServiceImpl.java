package com.hfut.glxy.service.impl;

/*import com.hfut.glxy.dao.*;*/
import com.hfut.glxy.entity.CourseGroup;
import com.hfut.glxy.entity.Picture;
import com.hfut.glxy.entity.Teacher;
import com.hfut.glxy.mapper.*;
import com.hfut.glxy.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/26 15:25 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherDao teacherDao;
    @Resource
    private PictureDao pictureDao;
    @Resource
    private CourseGroupDao courseGroupDao;
    @Resource
    private CourseGroup_TeacherDao courseGroup_teacherDao;
    @Resource
    private Teacher_PictureDao teacher_pictureDao;

    /*
         *
         * @Date 2017/11/26 15:32
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @param teacher
         * @return
         * @since JDK 1.8
         * @condition 将增加教师与增加相应关系视为一个事务，一兴俱兴
    */
    @Override
    @Transactional/*(rollbackFor = Exception.class)*/
    /*public int addTeacher(String courseGroup_id, Teacher teacher) throws Exception {

        int addTeacherSuccess;
        int addRelationSuccess;

        addTeacherSuccess = teacherDao.addTeacher(teacher);
        addRelationSuccess = courseGroup_teacherDao.addCourseGroup_Teacher(courseGroup_id, teacher.getId());

        if (addTeacherSuccess == 1 && addRelationSuccess == 1) {
            return 1;
        }
        return 0;
    }*/
    /**
     *
     * @Date 2017/11/28 14:36
     * @author students_ManagementSchool
     * @param teacher
     * @return
     * @since JDK 1.8
     * @condition
     */
    public int addTeacher(Teacher teacher) throws Exception{

        /*String courseGroup_id=teacher.getCourseGroup_id();*/
        String picture_id=teacher.getPicture_id();
        String teacher_id=teacher.getId();

        //判断课程组是否真的存在，若不存在，说明数据伪造，拒绝请求
        /*CourseGroup courseGroup=courseGroupDao.queryCourseGroupById(courseGroup_id);
        if (courseGroup==null){
            return 0;
        }*/

        int addTeacherSuccess;
        int addCourseGroup_teacherSuccess;

        //向教师表中添加一条记录
        addTeacherSuccess=teacherDao.addTeacher(teacher);

        if (addTeacherSuccess!=1){
            throw new RuntimeException("添加教师失败");
        }

        //向课程组-教师表中添加一条记录
        /*addCourseGroup_teacherSuccess=courseGroup_teacherDao.addCourseGroup_Teacher(courseGroup_id,teacher_id);

        if (addCourseGroup_teacherSuccess!=1||addTeacherSuccess!=1){
            //进行回滚
            throw new RuntimeException("添加失败");
        }*/

        //未上传图片
        if (picture_id==null){
            return 1;
        }

        //判断上传的图片是否真的存在，若不存在，说明数据伪造，拒绝请求
        Picture picture=pictureDao.queryPictureById(picture_id);
        if (picture==null){
            //进行回滚
            throw new RuntimeException("添加失败");
        }

        int addTeacher_pictureSuccess;
        //向教师—图片表中添加一条记录
        addTeacher_pictureSuccess=teacher_pictureDao.addTeacher_picture(teacher_id,picture_id);

        if (addTeacher_pictureSuccess==0){
            //进行回滚
            throw new RuntimeException("添加失败");
        }

        return addTeacher_pictureSuccess;
    }

    /*
         *
         * @Date 2017/11/26 16:42
         * @author students_ManagementSchool
         * @param teacher
         * @return
         * @since JDK 1.8
         * @condition   更新教师
    */
    /*@Override
    public int updateTeacher(Teacher teacher) throws Exception {

        int isSuccess;
        isSuccess=teacherDao.updateTeacher(teacher);

        return isSuccess;
    }*/

    @Override
    @Transactional
    public int updateTeacher(Teacher teacher) throws Exception{

        /*String courseGroup_id=teacher.getCourseGroup_id();*/
        String teacher_id=teacher.getId();
        String picture_id=teacher.getPicture_id();

        int updateTeacherSuccess;
        updateTeacherSuccess=teacherDao.updateTeacher(teacher);
        //更新教师失败
        if (updateTeacherSuccess==0){
            //进行回滚
            throw new RuntimeException("更新失败");
        }

        int deleteTeacher_PictureSuccess;
        int addTeacher_PictureSuccess;

        //如果未上传图片
        if (picture_id==null){
            return 1;
        }

        //判断上传的图片是否真的存在，若不存在，说明数据伪造，拒绝请求
        Picture picture=pictureDao.queryPictureById(picture_id);
        if (picture==null){
            //进行回滚
            throw new RuntimeException("更新失败");
        }

        //如果没有初始图片
        String currentPicture_id=teacher_pictureDao.getCurrentPictureIdByTeacher(teacher_id);
        if (currentPicture_id==null){
            addTeacher_PictureSuccess=teacher_pictureDao.addTeacher_picture(teacher_id,picture_id);

            if (addTeacher_PictureSuccess==0) {
                //进行回滚
                throw new RuntimeException("更新失败");
            }else{
                return addTeacher_PictureSuccess;
            }

        }

        //如果图片未发生变化
        if (currentPicture_id.equals(picture_id)){
            return 1;
        }

        //如果上传新图片
        deleteTeacher_PictureSuccess=teacher_pictureDao.deleteTeacher_picture(teacher_id);
        addTeacher_PictureSuccess=teacher_pictureDao.addTeacher_picture(teacher_id,picture_id);
        //如果操作失败
        if (deleteTeacher_PictureSuccess!=1||addTeacher_PictureSuccess!=1){
            //进行回滚
            throw new RuntimeException("更新失败");
        }

        return 1;
    }

    /*
         *
         * @Date 2017/11/26 17:21
         * @author students_ManagementSchool
         * @param  teacher_id
         * @return
         * @since JDK 1.8
         * @condition  将某教师放入回收站
    */
    @Override
    @Transactional
    /**
         *
         * @Date 2017/11/28 15:57
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition  删除教师
    */
    public int deleteTeacher(String teacher_id) throws Exception{

        /*int deleteTeacherSuccess;
        int deleteTeacher_pictureSuccess;
        int deleteCourseGroup_teacherSuccess;*/

        /*deleteTeacherSuccess=teacherDao.putToDustbin(teacher_id);
        deleteCourseGroup_teacherSuccess=courseGroup_teacherDao.deleteRelationByTeacher(teacher_id);
        deleteTeacher_pictureSuccess=teacher_pictureDao.deleteTeacher_picture(teacher_id);*/

        try{
            teacherDao.putToDustbin(teacher_id);
            courseGroup_teacherDao.deleteRelationByTeacher(teacher_id);
            teacher_pictureDao.deleteTeacher_picture(teacher_id);
            /*System.out.println("测测测测测测测测测测测测测测测测测试");*/
        }catch(RuntimeException e){
            throw new RuntimeException("删除失败");
        }

        /*//删除教师与课程组关联失败
        if (deleteTeacherSuccess!=1||deleteCourseGroup_teacherSuccess!=1){
            return 0;
        }
        //没有上传图片
        String picture_id=teacher_pictureDao.getCurrentPictureIdByTeacher(teacher_id);
        if (picture_id==null){
            return 1;
        }
        //删除图片失败
        deleteTeacher_pictureSuccess=teacher_pictureDao.deleteTeacher_picture(teacher_id);
        if (deleteTeacher_pictureSuccess==0){
            return 0;
        }*/
        //删除成功
        return 1;
    }


        /**
         *
         * @Date 2017/11/28 16:51
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition  根据id查询教师
         */
        @Override
        public Map queryTeacherById(String teacher_id) throws Exception{

            Map map=new HashMap();
            Teacher teacher;
            Picture picture;

            String picture_id;

            teacher=teacherDao.queryTeacherById(teacher_id);


            map.put("teacher",teacher);


            picture_id=teacher_pictureDao.getCurrentPictureIdByTeacher(teacher_id);
            if (picture_id==null){}
            else{
                picture=pictureDao.queryPictureById(picture_id);
                map.put("picture",picture);
            }

        return map;
    }

    /**   
         * 
         * @Date 2017/11/28 17:04
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  
    */
    @Override
    public List<Map> getAllTeachers() throws Exception{

        List<Map> maps=new ArrayList<>();
        Map map;
        /*String courseGroup_id;*/
        String picture_id;
        Picture picture;

        List<Teacher> teachers=teacherDao.getAllTeachers();

        for (Teacher teacher:teachers){

            /*courseGroup_id=courseGroup_teacherDao.getCurrentCourseGroupIdByTeacher(teacher.getId());*/
            picture_id=teacher_pictureDao.getCurrentPictureIdByTeacher(teacher.getId());
            picture=pictureDao.queryPictureById(picture_id);

            map=new HashMap();

            /*map.put("courseGroup_id",courseGroup_id);*/
            map.put("teacher",teacher);
            map.put("picture",picture);

            maps.add(map);
        }

        return maps;
    }
}

package com.hfut.glxy.service.impl;

import com.hfut.glxy.entity.Course;
import com.hfut.glxy.entity.CourseGroup;
import com.hfut.glxy.entity.Office;
import com.hfut.glxy.entity.Teacher;
import com.hfut.glxy.mapper.*;
import com.hfut.glxy.service.RelationService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/28 11:10 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Service
@Component("relationService")
public class RelationServiceImpl implements RelationService{

    @Resource
    private CourseGroupDao courseGroupDao;
    @Resource
    private TeacherDao teacherDao;
    @Resource
    private CourseGroup_TeacherDao courseGroup_teacherDao;
    @Resource
    private CourseGroup_CourseDao courseGroup_courseDao;
    @Resource
    private Teacher_CourseDao teacher_courseDao;
    @Resource
    private OfficeDao officeDao;
    @Resource
    private Unit_OfficeDao unit_officeDao;

    /**
         *
         * @Date 2017/12/28 11:44
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition   更新课程所处的课程组
    */
    @Override
    @Transactional
    public Integer updateRelation_courseGroup_course(Course course) throws Exception{

        String courseGroup_id=course.getCourseGroup_id();
        String course_id=course.getId();
        String [] teacher_ids=course.getTeacher_ids();

        //检验课程组是否存在，防止伪造数据带来的不可控后果
        CourseGroup courseGroup = courseGroupDao.queryCourseGroupById(courseGroup_id);
        if (courseGroup == null) {
            return 0;
        }

        if(teacher_ids==null){

        }else{
            for(String teacher_id:teacher_ids) {
                //判断负责的教师是否存在，用于防止数据伪造
                Teacher teacher = teacherDao.queryTeacherById(teacher_id);
                if (teacher == null) {
                    //进行回滚
                    throw new RuntimeException("教师不存在");
                }
            }
        }

        //课程组-课程关联表中没有相应数据
        //获取课程未更换课程组之前的课程组id
        String currentCourseGroup_id = courseGroup_courseDao.queryCourseGroupByCourse(course_id);
        if (currentCourseGroup_id == null) {
            throw new RuntimeException("未知错误");
        }

        //未更新课程组
        if (currentCourseGroup_id.equals(courseGroup_id)) {
            //不执行任何操作
        }
        //已更新课程组
        else {

            //更新课程组与课程关联表
            //将课程与先前课程组解除关系
            int deleteCourseGroup_courseSuccess = courseGroup_courseDao.putToDustbin(currentCourseGroup_id, course_id);
            if (deleteCourseGroup_courseSuccess!=1){
                throw new RuntimeException("删除课程组课程关系出现异常");
            }
            //判断新课程组与课程间是否有过关系
            int hasRelation=courseGroup_courseDao.ifRelationExists(courseGroup_id,course_id);
            //新课程组与该课程间未存在过关系
            if (hasRelation==0){
                //向课程组课程表中增加一条新纪录
                int addCourseGroup_courseSuccess=courseGroup_courseDao.addRelation(courseGroup_id,course_id);
                if (addCourseGroup_courseSuccess!=1){
                    throw new RuntimeException("增加课程组课程关系出现异常");
                }
            }
            //新课程组与该课程间存在过关系，即该课程以前属于过该课程组
            else{
                int recoverCourseGroup_courseSuccess=courseGroup_courseDao.recoverRelation(courseGroup_id,course_id);
                if (recoverCourseGroup_courseSuccess!=1){
                    throw new RuntimeException("恢复课程组课程关系出现异常");
                }
            }

            if (teacher_ids==null){
                return 1;
            }
            //更新先前课程组与教师关系表
            for (String teacher_id:teacher_ids){

                //判断该教师是否负责其它属于该课程组的课程
                int count=0;
                List<String> course_ids;
                course_ids=teacher_courseDao.getAllCoursesByTeacher(teacher_id);
                for (String courseId:course_ids){
                    String courseGroupId=courseGroup_courseDao.queryCourseGroupByCourse(courseId);
                    if (courseGroupId.equals(currentCourseGroup_id)){
                        count++;
                    }
                }

                //该教师不再负责属于该课程组的任何课程
                if (count==0){
                    //将该教师与当前课程组取消关联
                    int deleteCourseGroup_teacherSuccess=courseGroup_teacherDao
                            .deleteRelation(currentCourseGroup_id,teacher_id);

                    if (deleteCourseGroup_teacherSuccess!=1){
                        throw new RuntimeException("教师与课程组取关失败");
                    }
                }
                //该教师仍然负责该课程组的某课程
                else{
                    //仍保留该教师与该课程组的联系
                }

                //对当前的新的课程组进行与教师的关联操作

                //判断某教师与某课程组是否存在关联以及当前是否在该课程组任教
                Integer isDelete=courseGroup_teacherDao
                        .ifRelationExists(courseGroup_id,teacher_id);

                //如果该教师未曾从属该课程组
                if (isDelete==null){
                    int addCourseGroup_teacherSuccess=courseGroup_teacherDao.addCourseGroup_Teacher(courseGroup_id,teacher_id);
                    if (addCourseGroup_teacherSuccess!=1){
                        throw new RuntimeException("教师与课程组关联失败");
                    }
                }
                //如果该教师曾在该课程组任教，但现在未任教
                else if (isDelete==1){
                    int recoverCourseGroup_teacherSuccess=courseGroup_teacherDao.recoverRelation(courseGroup_id,teacher_id);
                    if (recoverCourseGroup_teacherSuccess!=1){
                        throw new RuntimeException("教师与课程组恢复失败");
                    }
                }
                //如果该教师曾在该课程组任教，现在依然任教
                else if (isDelete==0){
                    //不进行任何操作，直接进入下一位教师的判断
                }

            }

        }

        return 1;
    }

    /**   
         * 
         * @Date 2017/12/28 11:48
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition  更新课程的负责教师
    */
    @Override
    @Transactional
    public Integer updateRelation_teacher_course(Course course) throws Exception{

        int isSuccess;

        String courseGroup_id=course.getCourseGroup_id();
        String course_id=course.getId();
        String [] teacher_ids=course.getTeacher_ids();

        //检验课程组是否存在，防止伪造数据带来的不可控后果
        CourseGroup courseGroup = courseGroupDao.queryCourseGroupById(courseGroup_id);
        if (courseGroup == null) {
            return 0;
        }

        if(teacher_ids==null){

        }else{
            for(String teacher_id:teacher_ids) {
                //判断负责的教师是否存在，用于防止数据伪造
                Teacher teacher = teacherDao.queryTeacherById(teacher_id);
                if (teacher == null) {
                    //进行回滚
                    throw new RuntimeException("教师不存在");
                }
            }
        }



        //获取先前负责该课程的所有教师
        String[] currentTeacher_ids = teacher_courseDao.getCurrentTeacherIdByCourse(course_id);

        //未给当前课程指定教师
        if (teacher_ids==null){
            if (currentTeacher_ids==null){
                //均未指定负责教师
                isSuccess=1;
            }
            else{
                //以前有教师负责，现在没有教师负责
                for (String currentTeacher_id:currentTeacher_ids){
                    //取消课程与教师间的关系
                    int deleteTeacher_course=teacher_courseDao.putToDustbin(currentTeacher_id,course_id);
                    if (deleteTeacher_course!=1){
                        throw new RuntimeException("取消教师课程关系失败");
                    }
                }

                //判断该教师是否还属于该课程所在的课程组
                //即判断该教师是否负责属于该课程组的其他课程
                //以此来评定是否将该教师从该课程组剔除
                //判断该教师是否负责其它属于该课程组的课程
                for (String currentTeacher_id:currentTeacher_ids){
                    //判断该教师是否负责其它属于该课程组的课程
                    int count=0;
                    List<String> course_ids;
                    course_ids=teacher_courseDao.getAllCoursesByTeacher(currentTeacher_id);
                    for (String courseId:course_ids){
                        String courseGroupId=courseGroup_courseDao.queryCourseGroupByCourse(courseId);
                        if (courseGroupId.equals(courseGroup_id)){
                            count++;
                        }
                    }

                    //该教师不再负责属于该课程组的任何课程
                    if (count==0){
                        //将该教师与当前课程组取消关联
                        int deleteCourseGroup_teacherSuccess=courseGroup_teacherDao
                                .deleteRelation(courseGroup_id,currentTeacher_id);

                        if (deleteCourseGroup_teacherSuccess!=1){
                            throw new RuntimeException("教师与课程组取关失败");
                        }
                    }
                    //该教师仍然负责该课程组的某课程
                    else{
                        //仍保留该教师与该课程组的联系
                    }
                }
                isSuccess=1;
            }
        }
        //存在负责该课程的教师
        else{
            //之前该课程不存在负责教师
            if (currentTeacher_ids==null){

                for (String teacher_id:teacher_ids){

                    Integer isDelete=teacher_courseDao.ifRelationExists(teacher_id,course_id);
                    //如果该老师以前未负责过该课程
                    if (isDelete==null){
                        int addTeacher_courseSuccess = teacher_courseDao.addRelation(teacher_id, course_id);
                        if (addTeacher_courseSuccess!=1){
                            throw new RuntimeException("关联教师过程中出现错误");
                        }

                    }
                    //如果该老师以前负责过该课程，但现在不负责，重新建立关系
                    else if (isDelete==1){
                        int recoverTeacher_courseSuccess=teacher_courseDao.recoverRelation(teacher_id,course_id);
                        if (recoverTeacher_courseSuccess!=1){
                            throw new RuntimeException("恢复教师课程关系时出现异常");
                        }
                    }
                    //gg情况
                    else if (isDelete==0){
                        throw new RuntimeException("教师课程关系处理时出现未知错误，gg！！！");
                    }

                    Integer courseGroup_teacherExists;
                    courseGroup_teacherExists=courseGroup_teacherDao.ifRelationExists(courseGroup_id,teacher_id);
                    //该教师从未属于过该课程组
                    if (courseGroup_teacherExists==null){
                        int addCourseGroup_TeacherSuccess;
                        addCourseGroup_TeacherSuccess=courseGroup_teacherDao.addCourseGroup_Teacher(courseGroup_id,teacher_id);
                        if (addCourseGroup_TeacherSuccess!=1){
                            throw  new RuntimeException("关联教师课程组过程中出现异常");
                        }
                    }else if (courseGroup_teacherExists==1){
                        int recoverCourseGroup_TeacherSuccess;
                        recoverCourseGroup_TeacherSuccess=courseGroup_teacherDao.recoverRelation(courseGroup_id,teacher_id);
                        if (recoverCourseGroup_TeacherSuccess!=1){
                            throw new RuntimeException("恢复课程组教师之间的关系");
                        }
                    }else{

                    }

                }
                isSuccess=1;
            }
            //如果之前课程也存在负责教师
            else{

                Arrays.sort(currentTeacher_ids);
                Arrays.sort(teacher_ids);

                //未更新教师
                if(Arrays.equals(currentTeacher_ids,teacher_ids)){
                    isSuccess=1;
                }

                //已更新教师
                else {

                    for (String teacher_id : teacher_ids) {
                        //判断是否为新教师
                        int isExist = 0;
                        for (String currentTeacher_id : currentTeacher_ids) {
                            if (currentTeacher_id.equals(teacher_id)) {
                                isExist = 1;
                            }
                        }

                        //如果是新老师
                        if (isExist == 0) {

                            Integer isDelete = teacher_courseDao.ifRelationExists(teacher_id, course_id);
                            //如果该老师以前未负责过该课程
                            if (isDelete == null) {
                                int addTeacher_courseSuccess = teacher_courseDao.addRelation(teacher_id, course_id);
                                if (addTeacher_courseSuccess != 1) {
                                    throw new RuntimeException("关联教师过程中出现错误");
                                }
                            }
                            //如果该老师以前负责过该课程，但现在不负责，重新建立关系
                            else if (isDelete == 1) {
                                int recoverTeacher_courseSuccess = teacher_courseDao.recoverRelation(teacher_id, course_id);
                                if (recoverTeacher_courseSuccess != 1) {
                                    throw new RuntimeException("恢复教师课程关系时出现异常");
                                }

                                Integer ifRelationNormal=courseGroup_teacherDao.ifRelationExists(courseGroup_id,teacher_id);
                                if (ifRelationNormal==null){
                                    throw new RuntimeException("未知错误");
                                }
                                //如果该教师仍然在该课程组执教
                                else if (ifRelationNormal==0){
                                    //不执行操作
                                }
                                //如果该教师已不再在该课程组执教
                                else if (ifRelationNormal==1){
                                    int recoverCourseGroup_teacherSuccess=courseGroup_teacherDao.recoverRelation(courseGroup_id,teacher_id);
                                    if (recoverCourseGroup_teacherSuccess!=1){
                                        throw new RuntimeException("恢复课程组教师关联失败");
                                    }
                                }

                            }
                            //gg情况
                            else if (isDelete == 0) {
                                throw new RuntimeException("教师课程关系处理时出现未知错误，gg！！！");
                            }

                        }
                        Integer courseGroup_teacherExists;
                        courseGroup_teacherExists=courseGroup_teacherDao.ifRelationExists(courseGroup_id,teacher_id);
                        //该教师从未属于过该课程组
                        if (courseGroup_teacherExists==null){
                            int addCourseGroup_TeacherSuccess;
                            addCourseGroup_TeacherSuccess=courseGroup_teacherDao.addCourseGroup_Teacher(courseGroup_id,teacher_id);
                            if (addCourseGroup_TeacherSuccess!=1){
                                throw  new RuntimeException("关联教师课程组过程中出现异常");
                            }
                        }else if (courseGroup_teacherExists==1){
                            int recoverCourseGroup_TeacherSuccess;
                            recoverCourseGroup_TeacherSuccess=courseGroup_teacherDao.recoverRelation(courseGroup_id,teacher_id);
                            if (recoverCourseGroup_TeacherSuccess!=1){
                                throw new RuntimeException("恢复课程组教师之间的关系");
                            }
                        }else{

                        }
                    }


                    for (String currentTeacher_id : currentTeacher_ids) {
                        //判断该教师是否仍然负责该课程
                        int isExist = 0;
                        for (String teacher_id : teacher_ids) {
                            if (currentTeacher_id.equals(teacher_id)) {
                                isExist = 1;
                            }
                        }
                        //如果该教师不再负责本课程
                        if (isExist == 0) {
                            int deleteTeacher_courseSuccess = teacher_courseDao.putToDustbin(currentTeacher_id, course_id);
                            if (deleteTeacher_courseSuccess != 1) {
                                throw new RuntimeException("更新教师过程出现错误");
                            }

                            //判断该教师是否依然属于该课程组
                            int count=0;
                            List<String> course_ids;
                            course_ids=teacher_courseDao.getAllCoursesByTeacher(currentTeacher_id);
                            for (String courseId:course_ids){
                                String courseGroupId=courseGroup_courseDao.queryCourseGroupByCourse(courseId);
                                if (courseGroupId.equals(courseGroup_id)){
                                    count++;
                                }
                            }

                            //该教师不再负责属于该课程组的任何课程
                            if (count==0){
                                //将该教师与当前课程组取消关联
                                int deleteCourseGroup_teacherSuccess=courseGroup_teacherDao
                                        .deleteRelation(courseGroup_id,currentTeacher_id);

                                if (deleteCourseGroup_teacherSuccess!=1){
                                    throw new RuntimeException("教师与课程组取关失败");
                                }
                            }
                            //该教师仍然负责该课程组的某课程
                            else{
                                //仍保留该教师与该课程组的联系
                            }

                        }
                    }
                    isSuccess=1;
                }

            }

        }
        return isSuccess;
    }


    /**
         *
         * @Date 2018/1/13 20:45
         * @author students_ManagementSchool
         * @param unit_id
         * @param office_ids
         * @return
         * @since JDK 1.8
         * @condition 绑定教学资料和教学单元
    */
    @Override
    @Transactional
    public List<Office> bindUnit_offices(String unit_id,List<String> office_ids) throws Exception{

        List<Office> offices=new ArrayList<>();

        for (String office_id:office_ids){

            Office office=officeDao.queryOfficeById(office_id);
            if (office==null){
                throw new RuntimeException("教学资料不存在");
            }


            int addUnit_OfficeSuccess;
            addUnit_OfficeSuccess=unit_officeDao.addRelation(unit_id,office_id);
            if (addUnit_OfficeSuccess!=1){
                throw new RuntimeException("添加教学单元教学资料关联失败");
            }

            offices.add(office);
        }

        return offices;

    }

}

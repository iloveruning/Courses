package com.hfut.glxy.service;

import com.hfut.glxy.entity.Course;
import com.hfut.glxy.entity.CourseGroup;


import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/6 15:25 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
public interface InforService {

    /**
         *
         * @Date 2017/12/6 15:26
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取所有的课程组以及教师
    */
    Map getCourseGroup_Teacher() throws Exception;

    /**
         *
         * @Date 2017/12/6 19:33
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取所有的课程组
    */
    List<CourseGroup> getCourseGroups() throws Exception;

    /**
         *
         * @Date 2017/12/7 11:59
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition   获取所有的课程
    */
    List<Course> getCourses() throws Exception;

    /**
         *
         * @Date 2017/12/7 16:09
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition   获取某课程组所有的信息（包括课程和课程组）
    */
    Map getCourse_Teacher(String courseGroup_id) throws Exception;

    /**
         *
         * @Date 2017/12/23 15:56
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition  获取某一教师的基本信息以及其所教授的课程
    */
    Map getTeacher_Courses(String teacher_id) throws Exception;

    /**
         *
         * @Date 2018/1/6 17:49
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition  前台页面获取课程详细信息
    */
    Map getCourseDetail(String course_id) throws Exception;

    /**
         *
         * @Date 2018/1/11 13:58
         * @author students_ManagementSchool
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition   获取某一教学单元的详细信息
    */
    Map getUnitDetail(String unit_id) throws Exception;

    /**
         *
         * @Date 2018/1/19 16:45
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取首页的信息：课程组名，课程名，课程类型（核心、辅助）、课程id、课程图片
    */
    List<Map> getHomepageInfo() throws Exception;

    /**
         *
         * @Date 2018/1/19 17:36
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取教师团队：课程组名，课程组id（方便点击获取教师）；教师：头像、姓名、职称、个人简介（pdf链接）、性别

     */
    List<Map> getTeacherTeam() throws Exception;

}

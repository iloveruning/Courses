package com.hfut.glxy.service;

import com.hfut.glxy.dto.PageResult;
import com.hfut.glxy.entity.Course;
import com.hfut.glxy.entity.Manager;

import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/22 19:52 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
public interface CourseService {

    /**
         *
         * @Date 2017/11/22 19:55
         * @author students_ManagementSchool
         * @param course
         * @param manager
         * @return
         * @since JDK 1.8
         * @condition 添加课程
    */
    int addCourse(Course course,Manager manager) throws Exception;

    /**
         *
         * @Date 2017/11/30 14:36
         * @author students_ManagementSchool
         * @param course
         * @param manager
         * @return
         * @since JDK 1.8
         * @condition  更新课程
    */
    int updateCourse(Course course,Manager manager) throws Exception;

    /**
         *
         * @Date 2017/12/2 9:53
         * @author students_ManagementSchool
         * @param  course_id
         * @return
         * @since JDK 1.8
         * @condition  将课程放入回收站
    */
    /*int putToDustbin(Course course) throws Exception;*/
    int putToDustbin(String course_id) throws Exception;

    /**
         *
         * @Date 2017/12/2 10:57
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition 根据id获取课程
    */
    Map queryCourseById(String course_id) throws Exception;

    /**
         *
         * @Date 2017/11/25 16:27
         * @author students_ManagementSchool
         * @param
         * @return
         * @since JDK 1.8
         * @condition 获取全部课程
    */
    List<Course> getAllCourses() throws Exception;

    /**
         *
         * @Date 2018/1/15 22:07
         * @author students_ManagementSchool
         * @param startPage
         * @param pageSize
         * @return
         * @since JDK 1.8
         * @condition   分页查询课程
    */
    PageResult<Map> getCoursesByPage(int startPage, int pageSize) throws Exception;

}

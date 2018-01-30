package com.hfut.glxy.service;

import com.hfut.glxy.entity.CourseGroup;
import com.hfut.glxy.entity.Picture;

import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/26 9:56 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
public interface CourseGroupService {

    /**
         *
         * @Date 2017/11/26 9:57
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition  增加课程组
    */
    int addCourseGroup(CourseGroup courseGroup) throws Exception;
    /*int addCourseGroup(CourseGroup courseGroup,Picture picture) throws Exception;*/

    /**
         *
         * @Date 2017/11/26 11:05
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition 更新课程组
    */
    /*int updateCourseGroup(CourseGroup courseGroup) throws Exception;*/
    int updateCourseGroup(CourseGroup courseGroup) throws Exception;

    /**
         *
         * @Date 2017/11/26 11:17
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition  根据id将课程组放入回收站
    */
    int deleteCourseGroup(String courseGroup_id) throws Exception;

    /**
         *
         * @Date 2017/11/26 11:37
         * @author students_ManagementSchool
         * @param null
         * @return
         * @since JDK 1.8
         * @condition  获取全部课程组
    */
    /*List<CourseGroup> getAllCourseGroups() throws Exception;*/
    List<Map> getAllCourseGroups() throws Exception;

    /**
         *
         * @Date 2017/11/26 11:50
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition  根据id查找课程组
    */
    /*CourseGroup queryCourseGroupById(String courseGroup_id) throws Exception;*/

    Map queryCourseGroupById(String courseGroup_id) throws  Exception;

}

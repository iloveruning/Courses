package com.hfut.glxy.service;

import com.hfut.glxy.entity.Course;
import com.hfut.glxy.entity.Office;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/28 11:07 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
public interface RelationService {

    /**   
         * 
         * @Date 2017/12/28 11:08
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition  更新课程所处课程组
    */
    Integer updateRelation_courseGroup_course(Course course) throws Exception;

    /**   
         * 
         * @Date 2017/12/28 11:46
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition  更新课程的负责教师
    */
    Integer updateRelation_teacher_course(Course course) throws Exception;

    /**
         *
         * @Date 2018/1/13 20:44
         * @author students_ManagementSchool
         * @param unit_id
         * @param office_ids
         * @return
         * @since JDK 1.8
         * @condition 绑定教学资料和教学单元
    */
    List<Office> bindUnit_offices(String unit_id,List<String> office_ids) throws Exception;

    /**   
         * 
         * @Date 2018/1/13 21:26
         * @author students_ManagementSchool
         * @param course_id
         * @param office_ids
         * @return
         * @since JDK 1.8
         * @condition  绑定课程的课程介绍：课程大纲、教学日历、考核方式与标准、学习指南
    */
    /*List<Office> bindCourse_offices(String course_id,List<String> office_ids) throws Exception;*/

}

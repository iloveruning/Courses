package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018/1/7 10:47 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "course_officeDao")
public interface Course_OfficeDao {

    /**
         *
         * @Date 2018/1/7 11:00
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition 获取某一课程的所有课程介绍：课程大纲、教学日历、考核方式与标准、学习指南
    */
    @Select("select office_id from course_office where course_id=#{course_id} and isDelete=0")
    String [] getCourseInfosByCourse(@Param("course_id") String course_id);

}

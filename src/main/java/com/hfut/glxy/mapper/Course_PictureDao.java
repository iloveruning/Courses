package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/30 10:10 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "course_pictureDao")
public interface Course_PictureDao {

    /**
         *
         * @Date 2017/11/30 11:27
         * @author students_ManagementSchool
         * @param course_id
         * @param picture_id
         * @return
         * @since JDK 1.8
         * @condition   增加一条记录
    */
    @Insert("insert into course_picture (course_id,picture_id,createtime,updatetime,isDelete)" +
            "values (#{course_id},#{picture_id},NOW(),NOW(),0)")
    Integer addCourse_picture(@Param("course_id") String course_id, @Param("picture_id") String picture_id);

    /**
         *
         * @Date 2017/11/30 16:43
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition   获取课程当前的图片
    */
    @Select("select picture_id from course_picture where course_id=#{course_id} and isDelete=0")
    String getCurrentPictureIdByCourse(@Param("course_id") String course_id);

    /**
     *
     * @Date 2017/11/28 14:52
     * @author students_ManagementSchool
     * @param course_id
     * @return
     * @since JDK 1.8
     * @condition   将教师的当前图片放入回收站
     */
    @Update("update course_picture set isDelete=1 where course_id=#{course_id} and isDelete=0")
    Integer deleteCourse_picture(@Param("course_id") String course_id);

}

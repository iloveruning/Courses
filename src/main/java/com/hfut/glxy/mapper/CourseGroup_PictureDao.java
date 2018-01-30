package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/27 14:45 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "courseGroup_pictureDao")
public interface CourseGroup_PictureDao {

    /**
         *
         * @Date 2017/11/27 14:49
         * @author students_ManagementSchool
         * @param  courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition  将与课程组相关的图片放入回收站（一般来说一个课程组对应一张图片，即只有一个关系发生改变）
    */
    @Update("update coursegroup_picture set isDelete=1 where coursegroup_id=#{courseGroup_id} and picture_id=#{picture}")
    Integer deleteRelationByCourseGroup_Teacher(@Param("courseGroup_id") String courseGroup_id, @Param("picture_id") String picture_id);


    /**
         *
         * @Date 2017/11/27 19:14
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition   将与课程组相关的图片放入回收站（一般来说一个课程组对应一张图片，即只有一个关系发生改变）
    */
    @Update("update coursegroup_picture set isDelete=1 where coursegroup_id=#{courseGroup_id}")
    Integer deleteRelationByCourseGroup(@Param("courseGroup_id") String courseGroup_id);

    /**
         *
         * @Date 2017/11/27 16:36
         * @author students_ManagementSchool
         * @param  courseGroup_id
         * @param picture
         * @return
         * @since JDK 1.8
         * @condition 插入一条记录
    */
    @Insert("insert into coursegroup_picture (coursegroup_id,picture_id,createtime,updatetime,isDelete)" +
            "values (#{courseGroup_id},#{picture_id},NOW(),NOW(),0)")
    Integer addCourseGroup_picture(@Param("courseGroup_id") String courseGroup_id, @Param("picture_id") String picture);

    /**
         *
         * @Date 2017/11/27 16:36
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition   查询课程组当前图片的id
    */
    @Select("select picture_id from coursegroup_picture where coursegroup_id=#{courseGroup_id} and isDelete=0")
    String getCurrentPictureIdByCourseGroup(@Param("courseGroup_id") String courseGroup_id);

}

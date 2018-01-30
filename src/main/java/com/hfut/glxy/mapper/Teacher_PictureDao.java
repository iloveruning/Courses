package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/28 14:31 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "teacher_pictureDao")
public interface Teacher_PictureDao {

    /**
         *
         * @Date 2017/11/28 14:34
         * @author students_ManagementSchool
         * @param teacher_id
         * @param picture_id
         * @return
         * @since JDK 1.8
         * @condition   向教师-图片表中添加一条记录
    */
    @Insert("insert into teacher_picture (teacher_id,picture_id,createtime,updatetime,isDelete)" +
            " values (#{teacher_id},#{picture_id},NOW(),NOW(),0)")
    Integer addTeacher_picture(@Param("teacher_id") String teacher_id, @Param("picture_id") String picture_id);

    /**
         *
         * @Date 2017/11/28 14:52
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition   将教师的当前图片放入回收站
    */
    @Update("update teacher_picture set isDelete=1 where teacher_id=#{teacher_id} and isDelete=0")
    Integer deleteTeacher_picture(@Param("teacher_id") String teacher_id);

    /**
         *
         * @Date 2017/11/28 14:55
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition 获取教师的当前图片的id
    */
    @Select("select picture_id from teacher_picture where teacher_id=#{teacher_id} and isDelete=0")
    String getCurrentPictureIdByTeacher(@Param("teacher_id") String teacher_id);

}

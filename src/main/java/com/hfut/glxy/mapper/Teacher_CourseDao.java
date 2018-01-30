package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/22 19:31 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value="teacher_courseDao")
public interface Teacher_CourseDao {


    /**
         *
         * @Date 2017/11/22 19:34
         * @author students_ManagementSchool
         * @param course_id
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition 给课程指定负责人（从教师中选）
    */
    @Insert("insert into teacher_course (teacher_id,course_id,createtime,updatetime,isDelete)" +
            " values (#{teacher_id},#{course_id},NOW(),NOW(),0)")
    Integer addRelation(@Param("teacher_id") String teacher_id, @Param("course_id") String course_id);


    /**
         *
         * @Date 2017/11/22 19:37
         * @author students_ManagementSchool
         * @param course_id
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition  将该关系彻底删除
    */
    @Delete("delete from teacher_course where teacher_id=#{teacher_id} and course_id=#{course_id}")
    Integer deleteRelation(@Param("teacher_id") String teacher_id, @Param("course_id") String course_id);


    /**
         *
         * @Date 2017/11/22 19:40
         * @author students_ManagementSchool
         * @param course_id
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition 将课程和该负责人解除关系
    */
    @Update("update teacher_course set isDelete=1 where teacher_id=#{teacher_id} and course_id=#{course_id} and isDelete=0")
    Integer putToDustbin(@Param("teacher_id") String teacher_id, @Param("course_id") String course_id);


    /**
         *
         * @Date 2017/11/22 19:43
         * @author students_ManagementSchool
         * @param teacher_id
         * @param startPage
         * @param pageSize
         * @return
         * @since JDK 1.8
         * @condition 分页查询某老师负责的课程
    */
    @Select("select course_id from teacher_course where teacher_id=#{teacher_id}" +
            " and order by createtime desc limit #{startPage},#{pageSize} and isDelete=0")
    String[] queryCourseByPage(@Param("teacher_id") String teacher_id,
                               @Param("startPage") Integer startPage, @Param("pageSize") Integer pageSize);

    /**
         *
         * @Date 2017/11/26 17:09
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition    在教师-课程关联表中，查询某教师是否还有课程（用于删除教师的校验）
    */
    @Select("select count(*) from teacher_course where teacher_id=#{teacher_id} and isDelete=0")
    Integer ifTeacherExists(@Param("teacher_id") String teacher_id);


    /**
         *
         * @Date 2017/11/27 15:21
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition   将与某教师有关的记录全部放入回收站
    */
    @Update("update teacher_course set isDelete=1 where teacher_id=#{teacher_id} and isDelete=0")
    Integer deleteRelationByTeacher(@Param("teacher_id") String teacher_id);

    /**
         *
         * @Date 2017/11/27 15:22
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition  将与某课程有关的记录全部放入回收站
    */
    @Update("update teacher_course set isDelete=1 where course_id=#{course_id} and isDelete=0")
    Integer deleteRelationByCourse(@Param("course_id") String course_id);

    /**
         *
         * @Date 2017/11/30 16:32
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition   获取课程当前的负责教师
    */
    @Select("select teacher_id from teacher_course where course_id=#{course_id} and isDelete=0")
    String[] getCurrentTeacherIdByCourse(@Param("course_id") String course_id);

    /**
         *
         * @Date 2017/12/23 16:06
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition  获取某一教师教授的所有课程
    */
    @Select("select course_id from teacher_course where teacher_id=#{teacher_id} and isDelete=0")
    List<String> getAllCoursesByTeacher(@Param("teacher_id") String teacher_id);

    /**
         *
         * @Date 2017/12/27 11:14
         * @author students_ManagementSchool
         * @param course_id
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition 判断教师与课程间是否有过关系
    */
    @Select("select isDelete from teacher_course where course_id=#{course_id} and teacher_id=#{teacher_id}")
    Integer ifRelationExists(@Param("teacher_id") String teacher_id, @Param("course_id") String course_id);

    /**
         *
         * @Date 2017/12/27 11:19
         * @author students_ManagementSchool
         * @param course_id
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition   恢复教师与课程间的关系
    */
    @Update("update teacher_course set isDelete=0 where course_id=#{course_id} and teacher_id=#{teacher_id} and isDelete=1")
    Integer recoverRelation(@Param("teacher_id") String teacher_id, @Param("course_id") String course_id);

    /**
         *
         * @Date 2017/12/27 11:31
         * @author students_ManagementSchool
         * @param  teacher_id
         * @return
         * @since JDK 1.8
         * @condition   获取该教师正在负责的全部课程
    */
    /*@Select("select course_id from teacher_course where teacher_id=#{teacher_id} and isDelete=0")
    String [] getCurrentCoursesByTeacher(@Param("teacher_id") String teacher_id);*/

    /**
         *
         * @Date 2017/12/27 11:40
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition   获取负责该课程的所有教师
    */
    @Select("select teacher_id from teacher_course where course_id=#{course_id} and isDelete=0")
    String [] getAllTeachersByCourse(@Param("course_id") String course_id);
}

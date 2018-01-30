package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/26 15:14 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "courseGroup_TeacherDao")
public interface CourseGroup_TeacherDao {

    /**
         *
         * @Date 2017/11/26 15:22
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition 增加一条管理记录
    */
    @Insert("insert into  coursegroup_teacher (coursegroup_id,teacher_id,createtime,updatetime,isDelete) " +
            "values(#{courseGroup_id},#{teacher_id},NOW(),NOW(),0)")
    Integer addCourseGroup_Teacher(@Param("courseGroup_id") String courseGroup_id,
                                   @Param("teacher_id") String teacher_id);

    /**
         *
         * @Date 2017/11/26 16:10
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition  在课程组-教师关联表中，查询某课程组是否还有教师（用于删除课程组的校验）
    */
    @Select("select count(*) from coursegroup_teacher where coursegroup_id=#{courseGroup_id} and isDelete=0")
    Integer ifCourseGroupExists(@Param("courseGroup_id") String courseGroup_id);

    /**
         *
         * @Date 2017/11/26 17:18
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition  将教师当前所在的课程组删除
    */
    @Update("update coursegroup_teacher set isDelete=1 where teacher_id=#{teacher_id} and isDelete=0")
    Integer deleteRelationByTeacher(@Param("teacher_id") String teacher_id);

    /**
         *
         * @Date 2017/11/27 14:38
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition  将与某一课程组有关的记录全部放入回收站
    */
    @Update("update coursegroup_teacher set isDelete=1 where coursegroup_id=#{courseGroup_id} and isDelete=0")
    Integer deleteRelationByCourseGroup(@Param("courseGroup_id") String courseGroup_id);

    /**
         *
         * @Date 2017/11/28 15:05
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition   获取教师当前所在的课程组
    */
    @Select("select coursegroup_id from coursegroup_teacher where teacher_id=#{teacher_id} and isDelete=0")
    String getCurrentCourseGroupIdByTeacher(@Param("teacher_id") String teacher_id);

    /**
         *
         * @Date 2017/12/7 16:15
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition   查出属于某课程组的全部教师
    */
    @Select("select teacher_id from coursegroup_teacher where coursegroup_id=#{courseGroup_id} and isDelete=0")
    List<String> getAllTeachersByCourseGroup(@Param("courseGroup_id") String courseGroup_id);

    /**
         *
         * @Date 2017/12/27 10:38
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition 根据课程组与教师共同删除一条记录
    */
    @Update("update coursegroup_teacher set isDelete=1 where coursegroup_id=#{courseGroup_id} and " +
            "teacher_id=#{teacher_id} and isDelete=0")
    Integer deleteRelation(@Param("courseGroup_id") String courseGroup_id,
                                                @Param("teacher_id") String teacher_id);

    /**
         *
         * @Date 2017/12/27 10:42
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition  判断某教师与某课程组是否存在关联以及当前是否在该课程组任教
    */
    @Select("select isDelete from coursegroup_teacher where coursegroup_id=#{courseGroup_id} and " +
            "teacher_id=#{teacher_id}")
    Integer ifRelationExists(@Param("courseGroup_id") String courseGroup_id,
                             @Param("teacher_id") String teacher_id);

    /**
         *
         * @Date 2017/12/27 11:01
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition  恢复该教师和课程组的关系
    */
    @Update("update coursegroup_teacher set isDelete=0 where coursegroup_id=#{courseGroup_id} and " +
            "teacher_id=#{teacher_id} and isDelete=1")
    Integer recoverRelation(@Param("courseGroup_id") String courseGroup_id,
                            @Param("teacher_id") String teacher_id);
}

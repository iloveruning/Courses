package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/22 15:37 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */

@Mapper
@Component(value="courseGroup_courseDao")
public interface CourseGroup_CourseDao {

    /**   
         * 
         * @Date 2017/11/22 15:44 
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition 将课程添加到课程组
    */
    @Insert("insert into coursegroup_course (coursegroup_id,course_id,createtime,updatetime,isDelete)" +
            " values (#{courseGroup_id},#{course_id},NOW(),NOW(),0)")
    Integer addRelation(@Param("courseGroup_id") String courseGroup_id, @Param("course_id") String course_id);

    /**
         *
         * @Date 2017/11/22 15:48
         * @author students_ManagementSchool
         * @param course_id
         * @param coursegroup_id
         * @return
         * @since JDK 1.8
         * @condition  将课程彻底删除
    */
    @Delete("delete from coursegroup_course where course_id=#{course_id} and coursegroup_id=#{coursegroup_id}")
    Integer deleteRelation(@Param("course_id") String course_id, @Param("coursegroup_id") String coursegroup_id);

   /* @UpdateProvider(type="DynamicSQLProvider_Relation.class",name="");*/

   /**
        *
        * @Date 2017/11/22 16:16
        * @author students_ManagementSchool
        * @param courseGroup_id
        * @param course_id
        * @return
        * @since JDK 1.8
        * @condition  将某一课程从某一课程组中删除
   */
   @Update("update coursegroup_course set isDelete=1 where " +
           "coursegroup_id=#{courseGroup_id} and course_id=#{course_id}")
    Integer putToDustbin(@Param("courseGroup_id") String courseGroup_id, @Param("course_id") String course_id);

   /*
        *
        * @Date 2017/11/22 16:25
        * @author students_ManagementSchool
        * @param coursegroup_id
        * @param startPage
        * @param pageSize
        * @return
        * @since JDK 1.8
        * @condition  分页查询属于某一课程组的课程
   */
  /* @Select("select course_id from coursegroup_course where coursegroup_id=#{coursegroup_id}" +
           " and order by createtime desc limit #{startPage},#{pageSize}")
    String[] queryCourseByPage(@Param("coursegroup_id") String coursegroup_id,
           @Param("startPage") Integer startPage,@Param("pageSize") Integer pageSize);*/


    /**
         *
         * @Date 2017/11/26 16:18
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition  在课程组-课程关联表中，查询某课程组是否还有课程（用于删除课程组的校验）
    */
   @Select("select count(*) from coursegroup_course where coursegroup_id=#{courseGroup_id} and isDelete=0")
    Integer ifCourseGroupExists(@Param("courseGroup_id") String courseGroup_id);

   /**
        *
        * @Date 2017/11/27 14:41
        * @author students_ManagementSchool
        * @param courseGroup_id
        * @return
        * @since JDK 1.8
        * @condition    将与某一课程组相关的记录全部放入回收站
   */
   @Update("update coursegroup_course set isDelete=1 where coursegroup_id=#{courseGroup_id}")
    Integer deleteRelationByCourseGroup(@Param("courseGroup_id") String courseGroup_id);

   /**
        *
        * @Date 2017/11/27 14:43
        * @author students_ManagementSchool
        * @param course_id
        * @return
        * @since JDK 1.8
        * @condition    将与某一课程相关的记录全部放入回收站
   */
   @Update("update coursegroup_course set isDelete=1 where course_id=#{course_id}")
    Integer deleteRelationByCourse(@Param("course_id") String course_id);

   /**
        *
        * @Date 2017/11/30 14:54
        * @author students_ManagementSchool
        * @param course_id
        * @return
        * @since JDK 1.8
        * @condition  查询课程当前所在的课程组
   */
   @Select("select coursegroup_id from coursegroup_course where course_id=#{course_id} and isDelete=0")
    String queryCourseGroupByCourse(@Param("course_id") String course_id);


   /**
        *
        * @Date 2017/12/7 16:24
        * @author students_ManagementSchool
        * @param courseGroup_id
        * @return
        * @since JDK 1.8
        * @condition 查询某课程组所有的课程
   */
   @Select("select course_id from coursegroup_course where coursegroup_id=#{courseGroup_id} and isDelete=0")
    List<String> getAllCoursesByCourseGroup(@Param("courseGroup_id") String courseGroup_id);

   /**
        *
        * @Date 2017/12/27 16:57
        * @author students_ManagementSchool
        * @param course_id
        * @param courseGroup_id
        * @return
        * @since JDK 1.8
        * @condition  判断某课程组与某课程间是否存在过关联
   */
   @Select("select count(*) from coursegroup_course where coursegroup_id=#{courseGroup_id} " +
           "and course_id=#{course_id}")
    Integer ifRelationExists(@Param("courseGroup_id") String courseGroup_id,
                             @Param("course_id") String course_id);

   /**
        *
        * @Date 2017/12/27 17:04
        * @author students_ManagementSchool
        * @param course_id
        * @param courseGroup_id
        * @return
        * @since JDK 1.8
        * @condition  恢复课程组与课程间的关系
   */
   @Update("update coursegroup_course set isDelete=0 where coursegroup_id=#{courseGroup_id} " +
           "and course_id=#{course_id} and isDelete=1")
    Integer recoverRelation(@Param("courseGroup_id") String courseGroup_id,
                            @Param("course_id") String course_id);

}

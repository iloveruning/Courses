package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/22 17:18 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value="manager_courseDao")
public interface Manager_CourseDao {

    /**
     *
     * @Date 2017/11/22 15:44
     * @author students_ManagementSchool
     * @param manager_id
     * @param course_id
     * @return
     * @since JDK 1.8
     * @condition 给课程指定管理员
     */
    @Insert("insert into manager_course (manager_id,course_id,createtime,updatetime,isDelete)" +
            " values (#{manager_id},#{course_id},NOW(),NOW(),0)")
    Integer addRelation(@Param("manager_id") String manager_id, @Param("course_id") String course_id);

    /**
     *
     * @Date 2017/11/22 15:48
     * @author students_ManagementSchool
     * @param manager_id
     * @param course_id
     * @return
     * @since JDK 1.8
     * @condition  将课程彻底删除
     */
    @Delete("delete from manager_course where manager_id=#{manager_id} and course_id=#{course.id}")
    Integer deleteRelation(@Param("manager_id") String manager_id, @Param("course_id") String course_id);

   /* @UpdateProvider(type="DynamicSQLProvider_Relation.class",name="");*/

   /**
        *
        * @Date 2017/12/2 11:43
        * @author students_ManagementSchool
        * @param course_id
        * @return
        * @since JDK 1.8
        * @condition  删除与某课程相关的全部管理（一般只有一个）
   */
   @Update("update manager_course set isDelete=1 where course_id=#{course_id} and isDelete=0")
   Integer deleteRelationByCourse(@Param("course_id") String course_id);

    /**
     *
     * @Date 2017/11/22 16:16
     * @author students_ManagementSchool
     * @param manager_id
     * @param course_id
     * @return
     * @since JDK 1.8
     * @condition  课程与管理员解除指定关系
     */
    @Update("update manager_course set isDelete=1 where " +
            "manager_id=#{manager_id} and course_id=#{course_id}")
    Integer putToDustbin(@Param("manager_id") String manager_id, @Param("course_id") String course_id);

    /**
     *
     * @Date 2017/11/22 16:25
     * @author students_ManagementSchool
     * @param course_id
     * @return
     * @since JDK 1.8
     * @condition  根据课程查询管理员
     */
    @Select("select manager_id from manager_course where course_id=#{course_id}")
    String queryManager(@Param("course_id") String course_id);

}

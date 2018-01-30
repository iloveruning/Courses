package com.hfut.glxy.mapper;

import com.hfut.glxy.entity.CourseGroup;
import com.hfut.glxy.mapper.dynamicSQLProvider.DynamicSQLProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/22 14:23 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value="courseGroupDao")
public interface CourseGroupDao {

    /**
     *
     * @Date 2017/11/22 11:22
     * @author students_ManagementSchool
     * @param courseGroup
     * @return
     * @since JDK 1.8
     * @condition  增加课程组
     */
    @Insert("insert ignore into coursegroup (id,number,name,introduction," +
            "createtime,updatetime,isDelete) values (#{courseGroup.id}," +
            "#{courseGroup.number},#{courseGroup.name},#{courseGroup.introduction}," +
            "NOW(),NOW(),#{courseGroup.isDelete})")
    Integer addCourseGroup(@Param("courseGroup") CourseGroup courseGroup);

    /**
     *
     * @author students_ManagementSchool
     * @param courseGroup
     * @return
     * @since JDK 1.8
     * @condition 更新课程组（包括将课程组放入回收站，也要调用该方法，其中isDelete设为1）
     */
    @UpdateProvider(type= DynamicSQLProvider.class,method = "updateCourseGroup")
    Integer updateCourseGroup(@Param("courseGroup") CourseGroup courseGroup);

    /**
         *
         * @Date 2017/11/27 14:54
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition  将课程组放入回收站
    */
    @Update("update coursegroup set isDelete=1 where id=#{courseGroup_id}")
    Integer putToDustbin(@Param("courseGroup_id") String courseGroup_id);

    /**
     *
     * @Date 2017/11/22 11:23
     * @author students_ManagementSchool
     * @param id
     * @return
     * @since JDK 1.8
     * @condition  按id删除课程组
     */
    @Delete("delete from coursegroup where id=#{id}")
    Integer deleteCourseGroupById(@Param("id") String id);


    /**
     *
     * @Date 2017/11/22 14:11
     * @author students_ManagementSchool
     * @param id
     * @return
     * @since JDK 1.8
     * @condition  按id查找课程组
     */
    @Select("select * from coursegroup where id=#{id}")
    CourseGroup queryCourseGroupById(@Param("id") String id);


    /**
     *
     * @Date 2017/11/22 14:15
     * @author students_ManagementSchool
     * @param startPage
     * @param pageSize
     * @return
     * @since JDK 1.8
     * @condition 分页查询课程组
     */
    @Select("select * from coursegroup  order by createtime desc limit #{startPage},#{pageSize}")
    List<CourseGroup> queryCourseGroupByPage(@Param("startPage") Integer startPage, @Param("pageSize") Integer pageSize);


    /**
     *
     * @Date 2017/11/22 14:18
     * @author students_ManagementSchool
     * @param null
     * @return
     * @since JDK 1.8
     * @condition  查询课程组总数
     */
    @Select("select count(*) from coursegroup where isDelete=0")
    Integer getCourseGroupTotalCount();

    /**
         *
         * @Date 2017/11/26 11:35
         * @author students_ManagementSchool
         * @param null
         * @return
         * @since JDK 1.8
         * @condition 获取全部课程组
    */
    @Select("select * from coursegroup where isDelete=0")
    List<CourseGroup> getAllCourseGroups();

}

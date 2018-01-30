package com.hfut.glxy.mapper;

import com.hfut.glxy.entity.Teacher;
import com.hfut.glxy.mapper.dynamicSQLProvider.DynamicSQLProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/22 14:38 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "teacherDao")
public interface TeacherDao {

    /**
     *
     * @Date 2017/11/22 11:22
     * @author students_ManagementSchool
     * @param teacher
     * @return
     * @since JDK 1.8
     * @condition  增加教师
     */
    @Insert("insert into teacher (id,number,name,sex,position," +
            "personIntroduction,createtime,updatetime,isDelete) values (#{teacher.id}," +
            "#{teacher.number},#{teacher.name},#{teacher.sex},#{teacher.position}," +
            "#{teacher.personIntroduction},NOW(),NOW(),#{teacher.isDelete})")
    Integer addTeacher(@Param("teacher") Teacher teacher);

    /**
     *
     * @author students_ManagementSchool
     * @param teacher
     * @return
     * @since JDK 1.8
     * @condition 更新教师（包括将教师放入回收站，也要调用该方法，其中isDelete设为1）
     */
    @UpdateProvider(type= DynamicSQLProvider.class,method = "updateTeacher")
    Integer updateTeacher(@Param("teacher") Teacher teacher);

    /**
         *
         * @Date 2017/11/27 15:06
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition   根据id将教师放入回收站
    */
    @Update("update teacher set isDelete=1 where id=#{id} and isDelete=0")
    Integer putToDustbin(@Param("id") String id);

    /**
     *
     * @Date 2017/11/22 11:23
     * @author students_ManagementSchool
     * @param id
     * @return
     * @since JDK 1.8
     * @condition  按id删除教师
     */
    @Delete("delete from teacher where id=#{id}")
    Integer deleteTeacherById(@Param("id") String id);


    /**
     *
     * @Date 2017/11/22 14:11
     * @author students_ManagementSchool
     * @param id
     * @return
     * @since JDK 1.8
     * @condition  按id查找教师
     */
    @Select("select * from teacher where id=#{id}")
    Teacher queryTeacherById(@Param("id") String id);


    /**
     *
     * @Date 2017/11/22 14:15
     * @author students_ManagementSchool
     * @param startPage
     * @param pageSize
     * @return
     * @since JDK 1.8
     * @condition 分页查询教师
     */
    @Select("select * from teacher order by createtime desc limit #{startPage},#{pageSize}")
    List<Teacher> queryTeacherByPage(@Param("startPage") Integer startPage, @Param("pageSize") Integer pageSize);


    /**
     *
     * @Date 2017/11/22 14:18
     * @author students_ManagementSchool
     * @param null
     * @return
     * @since JDK 1.8
     * @condition  查询教师总数
     */
    @Select("select count(*) from teacher where isDelete=0")
    Integer getTeacherTotalCount();

    /**
         *
         * @Date 2017/11/27 15:32
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition   获取所有教师
    */
    @Select("select * from teacher where isDelete=0")
    List<Teacher> getAllTeachers();


}

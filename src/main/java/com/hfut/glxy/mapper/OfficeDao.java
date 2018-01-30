package com.hfut.glxy.mapper;

import com.hfut.glxy.entity.Course;
import com.hfut.glxy.entity.CourseInfo;
import com.hfut.glxy.entity.Office;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018/1/7 10:46 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "officeDao")
public interface OfficeDao {

    /**   
         * 
         * @Date 2018/1/7 10:52
         * @author students_ManagementSchool
         * @param id
         * @return
         * @since JDK 1.8
         * @condition  根据id获取课程介绍：课程大纲、教学日历、考核方式与标准、学习指南
    */
    @Select("select * from office where id=#{id}")
    Office queryOfficeById(@Param("id") String id);

    /**
         *
         * @Date 2018/1/19 7:09
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取刚插入数据的id
    */
    @Select("select last_insert_id() from office")
    Integer getLatestId();


    /**
         *
         * @Date 2018/1/19 12:20
         * @author students_ManagementSchool
         * @param office
         * @return
         * @since JDK 1.8
         * @condition  获取插入数据自增的id值
    */
    @Insert("insert into office (name,description,view_url,file_url,type,create_time) values" +
            "(#{office.name},#{office.description},#{office.viewUrl},#{office.fileUrl},#{office.type}," +
            "NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "office.id",keyColumn = "id")
    Integer addOffice(@Param("office") Office office);

    /**
         *
         * @Date 2018/1/19 12:34
         * @author students_ManagementSchool
         * @param id
         * @return
         * @since JDK 1.8
         * @condition  根据id删除office文件
    */
    @Delete("delete from office where id=#{id}")
    Integer deleteOfficeById(@Param("id") Integer id);

    /**
         *
         * @Date 2018/1/19 15:42
         * @author students_ManagementSchool
         * @param startPage
         * @param pageSize
         * @return
         * @since JDK 1.8
         * @condition  分页获取某一教学单元的教学资料
    */
    @Select("select * from office limit #{startPage},#{pageSize}")
    List<Course> getOfficesByUnit(@Param("startPage") Integer startPage, @Param("pageSize") Integer pageSize);

}

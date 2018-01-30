package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/13 22:25 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "chapter_unitDao")
public interface Chapter_UnitDao {

    /**
         *
         * @Date 2017/12/13 22:29
         * @author students_ManagementSchool
         * @param chapter_id
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition 增加一条记录
    */
    @Insert("insert into chapter_unit(chapter_id,unit_id,createtime,updatetime,isDelete)" +
            "values(#{chapter_id},#{unit_id},NOW(),NOW(),0)")
    Integer addRelation(@Param("chapter_id") String chapter_id,@Param("unit_id") String unit_id);

    /**
         *
         * @Date 2017/12/17 18:43
         * @author students_ManagementSchool
         * @param chapter_id
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition 将某一节从某一章中删除
    */
    @Update("update chapter_unit set isDelete=1 where chapter_id=#{chapter_id} and unit_id=#{unit_id} and isDelete=0")
    Integer deleteRelation(@Param("chapter_id") String chapter_id,@Param("unit_id") String unit_id);

    /**
         *
         * @Date 2017/12/17 19:01
         * @author students_ManagementSchool
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition   获取某一章的所有小节
    */
    @Select("select unit_id from chapter_unit where chapter_id=#{chapter_id} and isDelete=0")
    String [] getUnitsByChapter(@Param("chapter_id") String chapter_id);

    /**   
         * 
         * @Date 2018/1/11 14:20
         * @author students_ManagementSchool
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition  获取教学单元所在章
    */
    @Select("select chapter_id from chapter_unit where unit_id=#{unit_id} and isDelete=0")
    String getChapterByUnit(@Param("unit_id") String unit_id);


    /**
         *
         * @Date 2018/1/19 0:34
         * @author students_ManagementSchool
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition  获取某一章教学单元的数量
    */
    @Select("select count(*) from chapter_unit where chapter_id=#{chapter_id} and isDelete=0")
    Integer getUnitCountByChapter(@Param("chapter_id") String chapter_id);

    /**   
         * 
         * @Date 2018/1/19 0:41
         * @author students_ManagementSchool
         * @param chapter_id
         * @param pageSize
         * @param startPage
         * @return
         * @since JDK 1.8
         * @condition  分页查询某章的教学单元
    */
    @Select("select unit_id from (select * from chapter_unit where chapter_id=#{chapter_id} and isDelete=0) " +
            "as test limit #{startPage},#{pageSize}")
    String [] getUnitsByPage_chapter(@Param("chapter_id") String chapter_id,
                                     @Param("startPage") int startPage,@Param("pageSize") int pageSize);
}

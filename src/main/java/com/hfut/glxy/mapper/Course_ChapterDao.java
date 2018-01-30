package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/5 17:04 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "course_chapterDao")
public interface Course_ChapterDao {

    /**
         *
         * @Date 2017/12/5 17:07
         * @author students_ManagementSchool
         * @param course_id
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition  增加一条记录
    */
    @Insert("insert into course_chapter (course_id,chapter_id,createtime,updatetime,isDelete) " +
            "values (#{course_id},#{chapter_id},NOW(),NOW(),0)")
    Integer addRelation(@Param("course_id") String course_id,@Param("chapter_id") String chapter_id);

    /**
         *
         * @Date 2017/12/6 10:13
         * @author students_ManagementSchool
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition 将记录放入回收站
    */
    @Update("update course_chapter set isDelete=1 where chapter_id=#{chapter_id} and isDelete=0")
    Integer deleteRelation(@Param("chapter_id") String chapter_id);

    /**
         *
         * @Date 2017/12/6 10:44
         * @author students_ManagementSchool
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition  获取章节对应的当前课程
    */
    @Select("select course_id from course_chapter where chapter_id=#{chapter_id} and isDelete=0")
    String getCurrentCourseByChapter(@Param("chapter_id") String chapter_id);

    /**   
         * 
         * @Date 2018/1/6 15:53
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition  获取某课程的全部章
    */
    @Select("select chapter_id from course_chapter where course_id=#{course_id} and isDelete=0")
    String [] getChaptersByCourse(@Param("course_id") String course_id);

    /**
         *
         * @Date 2018/1/20 20:49
         * @author students_ManagementSchool
         * @param course_id
         * @param startPage
         * @param pageSize
         * @return
         * @since JDK 1.8
         * @condition   分页获取章
    */
    @Select("select chapter_id from (select chapter_id from course_chapter where course_id=#{course_id} and " +
            "isDelete=0) as test limit #{startPage},#{pageSize}")
    String [] getChaptersByPage(@Param("course_id") String course_id,@Param("startPage") Integer startPage,
                                @Param("pageSize") Integer pageSize);

    /**   
         * 
         * @Date 2018/1/20 20:54
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition  获取课程总章数
    */
    @Select("select count(*) from course_chapter where course_id=#{course_id} and isDelete=0")
    Integer getCountByCourse(@Param("course_id") String course_id);

}

package com.hfut.glxy.service;

import com.hfut.glxy.dto.PageResult;
import com.hfut.glxy.entity.Chapter;

import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/5 15:54 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
public interface ChapterService {

    /**
         *
         * @Date 2017/12/5 15:56
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition 添加章
    */
    Chapter addChapter(Chapter chapter) throws Exception;

    /**
         *
         * @Date 2017/12/5 21:07
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition  更新章
    */
    int updateChapter(Chapter chapter) throws Exception;

    /**
         *
         * @Date 2017/12/6 8:51
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition  将本章放入回收站
    */
    int deleteChapter(Chapter chapter) throws Exception;

    /**
         *
         * @Date 2017/12/6 11:05
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取所有的章
    */
    List<Chapter> getAllChapters() throws Exception;

    /**
         *
         * @Date 2017/12/6 11:25
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition  根据id获取章
    */
    Chapter queryChapterById(Chapter chapter) throws Exception;

    /**
         *
         * @Date 2018/1/6 18:03
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition  获取某一课程的全部章节
    */
    List<Chapter> getChaptersByCourse(String course_id) throws Exception;

    /**
         *
         * @Date 2018/1/20 20:44
         * @author students_ManagementSchool
         * @param map
         * @return
         * @since JDK 1.8
         * @condition  分页获取章
    */
    PageResult<Chapter> getChaptersByPage(Map map) throws Exception;

}

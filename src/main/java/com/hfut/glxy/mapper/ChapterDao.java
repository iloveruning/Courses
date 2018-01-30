package com.hfut.glxy.mapper;

import com.hfut.glxy.entity.Chapter;
import com.hfut.glxy.mapper.dynamicSQLProvider.DynamicSQLProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/5 16:30 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "chapterDao")
public interface ChapterDao {

    /**
     *
     * @Date 2017/12/5 15:57
     * @author students_ManagementSchool
     * @param chapter
     * @return
     * @since JDK 1.8
     * @condition 添加章
     */
    @Insert("insert into chapter (id,number,name,createtime,updatetime,isDelete)" +
            " values (#{chapter.id},#{chapter.number},#{chapter.name},NOW(),NOW(),0)")
    Integer addChapter(@Param("chapter") Chapter chapter);

    /**
         *
         * @Date 2017/12/5 21:06
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition  更新章
    */
    @UpdateProvider(type= DynamicSQLProvider.class,method = "updateChapter")
    Integer updateChapter(@Param("chapter") Chapter chapter);

    /**
         *
         * @Date 2017/12/6 8:50
         * @author students_ManagementSchool
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition  将本章放入回收站
    */
    @Update("update chapter set isDelete=1 where id=#{chapter_id} and isDelete=0")
    Integer putToDustbin(@Param("chapter_id") String chapter_id);

    /**
         *
         * @Date 2017/12/6 11:04
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取所有的章
    */
    @Select("select * from chapter where isDelete=0")
    List<Chapter> getAllChapters();

    /**
         *
         * @Date 2017/12/6 11:24
         * @author students_ManagementSchool
         * @param id
         * @return
         * @since JDK 1.8
         * @condition  根据id获取章
    */
    @Select("select * from chapter where id=#{id}")
    Chapter queryChapterById(@Param("id") String id);


}

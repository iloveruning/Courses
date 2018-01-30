package com.hfut.glxy.service;

import com.hfut.glxy.dto.PageResult;
import com.hfut.glxy.entity.Chapter;
import com.hfut.glxy.entity.Unit;

import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/13 22:18 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
public interface UnitService {

    /**
         *
         * @Date 2017/12/13 22:18
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition  添加小节
    */
    int addUnit(Unit unit) throws Exception;

    /**
         *
         * @Date 2017/12/17 18:27
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition  更新小节
    */
    int updateUnit(Unit unit) throws Exception;

    /**
         *
         * @Date 2017/12/17 18:38
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition   删除小节
    */
    int deleteUnit(Unit unit) throws Exception;

    /**
         *
         * @Date 2017/12/17 18:59
         * @author students_ManagementSchool
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition 查找某一章的全部小节
    */
    List<Unit> getUnitsByChapter(String chapter_id) throws Exception;

    /**
         *
         * @Date 2018/1/7 12:06
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition 获取某一教学单元的所有信息
    */
    Map queryUnitById(Unit unit) throws Exception;

    /**
         *
         * @Date 2018/1/19 0:29
         * @author students_ManagementSchool
         * @param map
         * @return
         * @since JDK 1.8
         * @condition 分页查询某页的教学单元
    */
    PageResult<Unit> getUnitsByPage_chapter(Map map) throws Exception;

}

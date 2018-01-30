package com.hfut.glxy.service;

import com.hfut.glxy.entity.KnowledgePoint;
import com.hfut.glxy.entity.Unit;

import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/28 21:00 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
public interface KnowledgePointService {

    /**   
         * 
         * @Date 2018/1/5 20:43
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  增加知识点
    */
    KnowledgePoint addKnowledgePoint(KnowledgePoint knowledgePoint) throws Exception;

    /**   
         * 
         * @Date 2017/12/28 21:01
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  更新知识点
    */
    Integer updateKnowledgePoint(KnowledgePoint knowledgePoint) throws Exception;

    /**   
         * 
         * @Date 2018/1/5 21:41
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  删除知识点
    */
    Integer deleteKnowledgePoint(KnowledgePoint knowledgePoint) throws Exception;

    /**   
         * 
         * @Date 2018/1/6 15:22
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  增加知识点与教学单元的关联
    */
    Integer addRelation(KnowledgePoint knowledgePoint) throws Exception;

    /**
         *
         * @Date 2018/1/5 22:19
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition 解除知识点与教学单元的关联
    */
    Integer releaseRelation(KnowledgePoint knowledgePoint) throws Exception;

    /**   
         * 
         * @Date 2018/1/6 10:22
         * @author students_ManagementSchool
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition  获取与某教学单元相关的所有知识点
    */
    List<KnowledgePoint> getKnowledgePointsByUnit(String unit_id) throws Exception;

    /**   
         * 
         * @Date 2018/1/6 10:27
         * @author students_ManagementSchool
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition  获取与某章相关的所有知识点
    */
    List<KnowledgePoint> getKnowledgePointsByChapter(String chapter_id) throws Exception;

    /**
         *
         * @Date 2018/1/6 15:48
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
     * @condition  获取与某课程相关的所有知识点
    */
    List<KnowledgePoint> getKnowledgePointsByCourse(String course_id) throws Exception;

    /**   
         * 
         * @Date 2018/1/6 10:38
         * @author students_ManagementSchool
         * @param knowledgePoint_id
         * @return
         * @since JDK 1.8
         * @condition  获取与某一知识点相关的所有教学单元
    */
    List<Map> getUnitsByKnowledgePoint(String knowledgePoint_id) throws Exception;



    String [] test(String test_id) throws Exception;

}

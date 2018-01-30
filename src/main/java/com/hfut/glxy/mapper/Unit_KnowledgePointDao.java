package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;


/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018/1/5 20:45 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "unit_knowledgePointDao")
public interface Unit_KnowledgePointDao {

    /**
         *
         * @Date 2018/1/5 20:50
         * @author students_ManagementSchool
         * @param unit_id
         * @param knowledgePoint_id
         * @return
         * @since JDK 1.8
         * @condition   增加课时与知识点关联
    */
    @Insert("insert into unit_knowledgepoint (unit_id,knowledgepoint_id,createtime,updatetime,isDelete) values" +
            "(#{unit_id},#{knowledgePoint_id},now(),now(),0)")
    Integer addRelation(@Param("unit_id") String unit_id,@Param("knowledgePoint_id") String knowledgePoint_id);

    /**   
         * 
         * @Date 2018/1/5 21:36
         * @author students_ManagementSchool
         * @param knowledgePoint_id
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition  删除该关系(临时)
    */
    @Update("update unit_knowledgepoint set isDelete=1 where unit_id=#{unit_id} and " +
            "knowledgepoint_id=#{knowledgePoint_id} and isDelete=0")
    Integer deleteRelation(@Param("unit_id") String unit_id,@Param("knowledgePoint_id") String knowledgePoint_id);

    /**   
         * 
         * @Date 2018/1/5 21:48
         * @author students_ManagementSchool
         * @param knowledgePoint_id
         * @return
         * @since JDK 1.8
         * @condition  取消与该知识点有关的全部关联
    */
    @Update("update unit_knowledgepoint set isDelete=1 where knowledgepoint_id=#{knowledgePoint_id} and isDelete=0")
    Integer deleteRelationByKnowledgePoint(@Param("knowledgePoint_id") String knowledgePoint_id);

    /**   
         * 
         * @Date 2018/1/6 10:17
         * @author students_ManagementSchool
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition  获取与某教学单元相关的所有知识点
    */
    @Select("select knowledgepoint_id from unit_knowledgepoint where unit_id=#{unit_id} and isDelete=0")
    String [] getKnowledgePointsByUnit(@Param("unit_id") String unit_id);

    /**
         *
         * @Date 2018/1/6 11:57
         * @author students_ManagementSchool
         * @param knowledgePoint_id
         * @return
         * @since JDK 1.8
         * @condition  获取与某一知识点相关的所有教学单元
    */
    @Select("select unit_id from unit_knowledgepoint where knowledgepoint_id=#{knowledgePoint_id} and isDelete=0")
    String [] getUnitsByKnowledgePoint(@Param("knowledgePoint_id") String knowledgePoint_id);

    /**
         *
         * @Date 2018/1/6 15:26
         * @author students_ManagementSchool
         * @param knowledgePoint_id
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition 判断关系是否存在
    */
    @Select("select isDelete from unit_knowledgepoint where knowledgepoint_id=#{knowledgePoint_id} and unit_id=#{unit_id}")
    Integer ifRelationExists(@Param("unit_id") String unit_id,@Param("knowledgePoint_id") String knowledgePoint_id);

    /**   
         * 
         * @Date 2018/1/6 15:31
         * @author students_ManagementSchool
         * @param knowledgePoint_id
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition  恢复知识点教学单元联系
    */
    @Update("update unit_knowledgepoint set isDelete=0 where knowledgepoint_id=#{knowledgePoint_id} and unit_id=#{unit_id} and isDelete=1")
    Integer recoverRelation(@Param("unit_id") String unit_id,@Param("knowledgePoint_id") String knowledgePoint_id);
}

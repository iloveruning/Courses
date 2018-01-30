package com.hfut.glxy.mapper;

import com.hfut.glxy.entity.KnowledgePoint;
import com.hfut.glxy.mapper.dynamicSQLProvider.DynamicSQLProvider;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/28 20:41 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "knowledgePointDao")
public interface KnowledgePointDao {

    /**   
         * 
         * @Date 2017/12/28 20:45
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  增加知识点
    */
    @Insert("insert into knowledgepoint (id,content,createtime,updatetime,isDelete) " +
            "values (#{knowledgePoint.id},#{knowledgePoint.content},NOW(),NOW(),0)")
    Integer addKnowledgePoint(@Param("knowledgePoint") KnowledgePoint knowledgePoint);

    /**   
         * 
         * @Date 2017/12/28 20:51
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  更新知识点
    */
    @UpdateProvider(type = DynamicSQLProvider.class,method = "updateKnowledgePoint")
    Integer updateKnowledgePoint(@Param("knowledgePoint") KnowledgePoint knowledgePoint);

    /**   
         * 
         * @Date 2017/12/28 20:53
         * @author students_ManagementSchool
         * @param id
         * @return
         * @since JDK 1.8
         * @condition  将知识点放入回收站
    */
    @Update("update knowledgePoint set isDelete=1 where id=#{id} and isDelete=0")
    Integer putToDustbin(@Param("id") String id);

    /**   
         * 
         * @Date 2017/12/28 20:55
         * @author students_ManagementSchool
         * @param id
         * @return
         * @since JDK 1.8
         * @condition 根据id获取知识点
    */
    @Select("select * from knowledgepoint where id=#{id}")
    KnowledgePoint queryKnowledgePointById(@Param("id") String id);

    /**
         *
         * @Date 2018/1/6 10:46
         * @author students_ManagementSchool
         * @param contents
         * @return
         * @since JDK 1.8
         * @condition  对知识点进行模糊查询
    */
    @SelectProvider(type = DynamicSQLProvider.class,method = "getSimilarKnowledgePoints")
    String[] getSimilarKnowledgePoints(@Param("contents") List<String> contents);



    @Select("select id from knowledgepoint where content like '%ds%'")
    String [] test();

}

package com.hfut.glxy.mapper;

import com.hfut.glxy.entity.Unit;
import com.hfut.glxy.mapper.dynamicSQLProvider.DynamicSQLProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/13 22:02 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "unitDao")
public interface UnitDao {

    /**
         *
         * @Date 2017/12/13 22:06
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition  添加小节
    */
    @Insert("insert into unit(id,number,name,content,createtime,updatetime,isDelete)" +
            "values(#{unit.id},#{unit.number},#{unit.name},#{unit.content},NOW()," +
            "NOW(),0)")
    Integer addUnit(@Param("unit") Unit unit);


    /**
         *
         * @Date 2017/12/13 22:08
         * @author students_ManagementSchool
         * @param id
         * @return
         * @since JDK 1.8
         * @condition   将小节放入回收站
    */
    @Update("update unit set isDelete=1 where id=#{id}")
    Integer putToDustbin(@Param("id") String id);

    /**
         *
         * @Date 2017/12/13 22:14
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition  对小节进行更新
    */
    @UpdateProvider(type = DynamicSQLProvider.class,method = "updateUnit")
    Integer updateUnit(@Param("unit") Unit unit);

    /**
         *
         * @Date 2017/12/13 22:15
         * @author students_ManagementSchool
         * @param id
         * @return
         * @since JDK 1.8
         * @condition  根据id获取小节
    */
    @Select("select * from unit where id=#{id}")
    Unit queryUnitById(@Param("id") String id);

    /**
         *
         * @Date 2017/12/13 22:15
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取所有的小节
    */
    @Select("select * from unit where isDelete=0")
    List<Unit> getAllUnits();


}

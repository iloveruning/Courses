package com.hfut.glxy.mapper;

import com.hfut.glxy.entity.Manager;
import com.hfut.glxy.mapper.dynamicSQLProvider.DynamicSQLProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/22 14:57 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value="managerDao")
public interface ManagerDao {

    /**
     *
     * @Date 2017/11/22 11:22
     * @author students_ManagementSchool
     * @param manager
     * @return
     * @since JDK 1.8
     * @condition  增加管理员
     */
    @Insert("insert ignore into manager (id,account,password," +
            "issuper,createtime,updatetime,isDelete) values (#{manager.id}," +
            "#{manager.account},#{manager.password},#{manager.isSuper}," +
            "NOW(),NOW(),#{manager.isDelete})")
    Integer addManager(@Param("manager") Manager manager);

    /**
     *
     * @author students_ManagementSchool
     * @param manager
     * @return
     * @since JDK 1.8
     * @condition 更新管理员（包括将管理员放入回收站，也要调用该方法，其中isDelete设为1）
     */
    @UpdateProvider(type= DynamicSQLProvider.class,method = "updateManager")
    Integer updateManager(@Param("manager") Manager manager);

    /**
     *
     * @Date 2017/11/22 11:23
     * @author students_ManagementSchool
     * @param id
     * @return
     * @since JDK 1.8
     * @condition  按id删除管理员
     */
    @Delete("delete from manager where id=#{id}")
    Integer deleteManagerById(@Param("id") String id);


    /**
     *
     * @Date 2017/11/22 14:11
     * @author students_ManagementSchool
     * @param id
     * @return
     * @since JDK 1.8
     * @condition  按id查找管理员
     */
    @Select("select * from manager where id=#{id}")
    Manager queryManagerById(@Param("id") String id);


    /**
     *
     * @Date 2017/11/22 14:15
     * @author students_ManagementSchool
     * @param startPage
     * @param pageSize
     * @return
     * @since JDK 1.8
     * @condition 分页查询管理员
     */
    @Select("select * from manager  order by createtime desc limit #{startPage},#{pageSize}")
    List<Manager> queryManagerByPage(@Param("startPage") Integer startPage, @Param("pageSize") Integer pageSize);


    /**
     *
     * @Date 2017/11/22 14:18
     * @author students_ManagementSchool
     * @param null
     * @return
     * @since JDK 1.8
     * @condition  查询管理员总数
     */
    @Select("select count(*) from manager")
    Integer getManagerTotalCount();

    /**
         *
         * @Date 2017/11/25 15:27
         * @author students_ManagementSchool
         * @param null
         * @return
         * @since JDK 1.8
         * @condition 通过超级管理员权限查询超级管理员信息
    */
    @Select("select * from manager where issuper=1")
    Manager querySuperManager();

    /**
         *
         * @Date 2017/11/25 22:45
         * @author students_ManagementSchool
         * @param null
         * @return
         * @since JDK 1.8
         * @condition  获取全部管理员，用于超级管理员的后台管理
    */
    @Select("select * from manager  where isDelete=0")
    List<Manager> getAllManagers();


    /**
         *
         * @Date 2017/12/2 10:09
         * @author students_ManagementSchool
         * @param id
         * @return
         * @since JDK 1.8
         * @condition   将管理员放入回收站
    */
    @Update("update manager set isDelete=1 where id=#{id} and isDelete=0")
    Integer putToDustbin(@Param("id") String id);
}

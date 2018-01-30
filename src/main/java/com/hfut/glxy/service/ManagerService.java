package com.hfut.glxy.service;

import com.hfut.glxy.entity.Manager;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/25 15:29 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
public interface ManagerService {

    /**
         *
         * @Date 2017/11/25 15:30
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  查询超级管理员
    */
    Manager querySuperManager() throws Exception;

    /**
         *
         * @Date 2017/11/25 15:49
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition 通过选择登录的管理课程，在管理员课程表中查询管理员Id，根据此Id在管理员表查找此管理员
    */
    Manager queryManagerByCourse(String course_id) throws Exception;

    /**
         *
         * @Date 2017/11/25 20:51
         * @author students_ManagementSchool
         * @param manager_Id
         * @return
         * @since JDK 1.8
         * @condition 通过管理员Id查找管理员
    */
    Manager queryManagerById(String manager_Id) throws Exception;

    /**
         *
         * @Date 2017/11/25 22:48
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取全部管理员，用于超级管理员后台管理
    */
    List<Manager> getAllManagers() throws Exception;


}

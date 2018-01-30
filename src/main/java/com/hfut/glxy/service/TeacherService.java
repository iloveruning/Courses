package com.hfut.glxy.service;


import com.hfut.glxy.entity.Teacher;

import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/26 15:23 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
public interface TeacherService {

    /**
         *
         * @Date 2017/11/26 15:24
         * @author students_ManagementSchool
         * @param teacher
         * @return
         * @since JDK 1.8
         * @condition  增加教师以及向教师课程组关联表中增加数据
    */
    /*int addTeacher(String courseGroup_id,Teacher teacher) throws Exception;*/
    int addTeacher(Teacher teacher) throws Exception;

    /**
         *
         * @Date 2017/11/26 16:38
         * @author students_ManagementSchool
         * @param teacher
         * @return
         * @since JDK 1.8
         * @condition  更新教师信息
    */
    /*int updateTeacher(Teacher teacher) throws Exception;*/
    int updateTeacher(Teacher teacher) throws Exception;

    /**
         *
         * @Date 2017/11/26 16:55
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition  将教师放入回收站
    */
    int deleteTeacher(String teacher_id) throws Exception;

    /**
         *
         * @Date 2017/11/26 17:32
         * @author students_ManagementSchool
         * @param teacher_id
         * @return
         * @since JDK 1.8
         * @condition  通过id查找教师
    */
    /*Teacher queryTeacherById(String teacher_id) throws Exception;*/
    Map queryTeacherById(String teacher_id) throws Exception;

    /**   
         * 
         * @Date 2017/11/28 16:56
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取全部教师
    */
    List<Map> getAllTeachers() throws Exception;

}

package com.hfut.glxy.service;

import com.github.pagehelper.PageInfo;
import com.hfut.glxy.entity.StudentCourse;

import java.util.Map;

/**
 * @author chenliangliang
 * @date: 2017/12/6
 */
public interface StudentCourseService {


    boolean save(String sid, String cid);


    boolean cancel(String sid, String cid);


    PageInfo<StudentCourse> getCourseStudents(String cid, int pageNum, int pageSize);


    PageInfo<Map<String,Object>> getStudentCourses(String sid, int pageNum, int pageSize);

    boolean isChosed(String sid, String cid);


    boolean updateTime(String sid, String cid);

    boolean isDelete(String sid, String cid);



}

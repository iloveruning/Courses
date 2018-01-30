package com.hfut.glxy.mapper;

import com.hfut.glxy.entity.StudentCourse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenliangliang
 * @date: 2017/12/6
 */
@Component
public interface StudentCourseMapper {


    int save(@Param("sid") String sid, @Param("cid") String cid);


    int delete(@Param("sid") String sid, @Param("cid") String cid);


    List<StudentCourse> findCourseBySid(@Param("sid") String sid);


    List<StudentCourse> findStudentsByCid(@Param("cid") String cid);


    int cancel(@Param("sid") String sid, @Param("cid") String cid);


    int isChosed(@Param("sid") String sid, @Param("cid") String cid);

    int updateTime(@Param("sid") String sid, @Param("cid") String cid);

    int isDelete(@Param("sid") String sid, @Param("cid") String cid);

}

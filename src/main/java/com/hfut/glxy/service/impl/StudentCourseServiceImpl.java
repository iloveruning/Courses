package com.hfut.glxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfut.glxy.entity.StudentCourse;
import com.hfut.glxy.mapper.CourseMapper;
import com.hfut.glxy.mapper.StudentCourseMapper;
import com.hfut.glxy.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2017/12/6
 */
@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    private StudentCourseMapper studentCourseMapper;

    private CourseMapper courseMapper;


    @Autowired
    protected StudentCourseServiceImpl(StudentCourseMapper studentCourseMapper, CourseMapper courseMapper) {
        this.studentCourseMapper = studentCourseMapper;
        this.courseMapper = courseMapper;
    }


    @Override
    public boolean save(String sid, String cid) {
        return studentCourseMapper.save(sid, cid) == 1;
    }

    @Override
    public boolean cancel(String sid, String cid) {
        return studentCourseMapper.cancel(sid, cid) == 1;
    }

    @Override
    public PageInfo<StudentCourse> getCourseStudents(String cid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentCourse> list = studentCourseMapper.findStudentsByCid(cid);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Map<String, Object>> getStudentCourses(String sid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentCourse> list = studentCourseMapper.findCourseBySid(sid);
        //根据课程id查询课程信息......(待完成...)
        List<Map<String, Object>> res = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        list.forEach(it -> {
            String cid = it.getCourseId();
            Map<String, Object> map = courseMapper.findCourseAndGroupInfoById(cid);
            map.put("cid",cid);
            map.put("img", courseMapper.findCourseImgById(cid));
            map.put("time",sdf.format(it.getUpdateTime()));
            res.add(map);
        });

        return new PageInfo<>(res);
    }

    @Override
    public boolean isChosed(String sid, String cid) {
        return studentCourseMapper.isChosed(sid, cid) == 1;
    }

    @Override
    public boolean updateTime(String sid, String cid) {
        return studentCourseMapper.updateTime(sid, cid) == 1;
    }

    @Override
    public boolean isDelete(String sid, String cid) {
        return studentCourseMapper.isDelete(sid, cid) == 1;
    }
}

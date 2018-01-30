package com.hfut.glxy.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfut.glxy.entity.Student;
import com.hfut.glxy.mapper.StudentMapper;
import com.hfut.glxy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenliangliang
 * @date 2017/12/19
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {

    private StudentMapper studentMapper;

    @Autowired
    protected StudentServiceImpl(StudentMapper studentMapper){
        this.studentMapper=studentMapper;
    }

    @Override
    public Student getNameAndImg(String sid) {
        return studentMapper.findNameAndImgById(sid);
    }

    @Override
    public Student getLoginInfo(String email) {
        return studentMapper.findByEmail(email);
    }

    @Override
    public boolean isExistByEmail(String email) {
        return studentMapper.countByEmail(email)==1;
    }

    @Override
    public String getPassword(String sid) {
        return studentMapper.findPwById(sid);
    }
}

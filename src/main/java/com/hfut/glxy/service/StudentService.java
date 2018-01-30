package com.hfut.glxy.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfut.glxy.entity.Student;

/**
 * @author chenliangliang
 * @date 2017/12/19
 */
public interface StudentService extends IService<Student> {

    Student getNameAndImg(String sid);

    Student getLoginInfo(String email);

    boolean isExistByEmail(String email);

    String getPassword(String sid);
}

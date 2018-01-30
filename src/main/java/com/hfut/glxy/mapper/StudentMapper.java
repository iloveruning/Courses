package com.hfut.glxy.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hfut.glxy.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date: 2017/12/19
 */
@Component
public interface StudentMapper extends BaseMapper<Student> {

    Student findNameAndImgById(@Param("sid") String sid);

    Student findByEmail(@Param("email") String email);

    int countByEmail(@Param("email") String email);

    String findPwById(@Param("sid") String sid);
}

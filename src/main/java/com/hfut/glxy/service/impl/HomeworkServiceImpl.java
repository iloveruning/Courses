package com.hfut.glxy.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfut.glxy.entity.Homework;
import com.hfut.glxy.mapper.HomeworkMapper;
import com.hfut.glxy.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2017/11/29
 */
@Service
public class HomeworkServiceImpl extends ServiceImpl<HomeworkMapper,Homework> implements HomeworkService {

    private HomeworkMapper homeworkMapper;

    @Autowired
    protected HomeworkServiceImpl(HomeworkMapper homeworkMapper){
        this.homeworkMapper=homeworkMapper;
    }

    @Override
    public PageInfo<Map<String, Object>> listHomeworkInfo(int pageNum,String cid) {
        PageHelper.startPage(pageNum,10);
        List<Map<String,Object>> list=homeworkMapper.findHomeworkInfo(cid);
        return new PageInfo<>(list);
    }
}

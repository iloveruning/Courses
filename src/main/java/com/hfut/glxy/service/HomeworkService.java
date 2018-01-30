package com.hfut.glxy.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import com.hfut.glxy.entity.Homework;

import java.util.Map;

/**
 * @author chenliangliang
 * @date 2017/11/29
 */
public interface HomeworkService extends IService<Homework> {

    PageInfo<Map<String,Object>> listHomeworkInfo(int pageNum, String cid);
}

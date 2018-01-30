package com.hfut.glxy.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfut.glxy.entity.Video;
import com.hfut.glxy.mapper.VideoMapper;
import com.hfut.glxy.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * @author chenliangliang
 * @date: 2017/12/5
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper,Video> implements VideoService {
}

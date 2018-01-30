package com.hfut.glxy.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfut.glxy.entity.Picture;
import com.hfut.glxy.mapper.PictureMapper;
import com.hfut.glxy.service.PictureService;
import org.springframework.stereotype.Service;

/**
 * @author chenliangliang
 * @date: 2017/12/6
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper,Picture> implements PictureService {
}

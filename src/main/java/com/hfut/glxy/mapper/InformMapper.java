package com.hfut.glxy.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hfut.glxy.entity.Inform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenliangliang
 * @date: 2017/11/27
 */
@Component
public interface InformMapper extends BaseMapper<Inform>{

    int deleteAll();

    int save(Inform inform);

    List<Inform> findTitleByCourseId(String cid);

    Inform findTitleAndContentById(@Param("id") long id);
}

package com.hfut.glxy.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hfut.glxy.entity.Reply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 * @date: 2017/12/18
 */
@Component
public interface ReplyMapper extends BaseMapper<Reply> {

    List<Map<String,Object>> findCommentReplies(@Param("cid") int cid);

    Map<String,Object> findById(@Param("id") int id);

    int deleteByCommentId(@Param("cid") int cid);

    //int deleteAgreementByC

}

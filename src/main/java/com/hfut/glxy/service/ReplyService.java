package com.hfut.glxy.service;

import com.baomidou.mybatisplus.service.IService;
import com.hfut.glxy.entity.Reply;

import java.util.List;

/**
 * @author chenliangliang
 * @date: 2017/12/19
 */
public interface ReplyService extends IService<Reply> {

    List<Reply> getCommentReplies(int cid);

    Reply getReplyById(int id);
}

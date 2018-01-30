package com.hfut.glxy.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import com.hfut.glxy.entity.Comment;

import java.util.Map;

/**
 * @author chenliangliang
 * @date: 2017/11/29
 */
public interface CommentService extends IService<Comment> {


    /**
     * 给评论点赞
     * @param studentId
     * @param commentId
     * @throws RuntimeException
     */
    void agree(String studentId, int commentId) throws RuntimeException;


    /**
     * 取消点赞
     * @param studentId
     * @param commentId
     * @throws RuntimeException
     */
    void cancelAgreement(String studentId, int commentId) throws RuntimeException;


    /**
     * 是否点过赞
     * @param studentId
     * @param commentId
     * @return
     */
    boolean isAgreed(String studentId, int commentId);

    /**
     * 获取章节对应的评论
     * @param cid
     * @return
     */
    PageInfo<Comment> getChapterComments(int pageNum, String sid, String cid);


    Comment getCommentById(int id);


    PageInfo<Map<String,Object>> getMyComments(int pageNum, String sid);


    boolean deleteMyComment(int cid, String sid) throws RuntimeException;

}

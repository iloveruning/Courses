package com.hfut.glxy.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hfut.glxy.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenliangliang
 * @date 2017/11/29
 */
@Component
public interface CommentMapper extends BaseMapper<Comment> {


    int saveAgreement(@Param("sid") String sid, @Param("cid") int cid);

    int increaseAgreeCount(@Param("id") int id);

    int decreaseAgreeCount(@Param("id") int id);

    int deleteAgreement(@Param("sid") String sid, @Param("cid") int cid);

    int isAgreed(@Param("sid") String sid, @Param("cid") int cid);

    int increaseReply(@Param("cid") int cid);

    int decreaseReply(@Param("cid") int cid);

    List<Comment> findByChapterId(@Param("cid") String cid);

    Comment findById(@Param("id") int id);

    List<Comment> findByStudentId(@Param("sid") String sid);

    int deleteById(@Param("id") int id);
    

}

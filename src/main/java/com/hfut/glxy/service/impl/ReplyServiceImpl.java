package com.hfut.glxy.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hfut.glxy.entity.Reply;
import com.hfut.glxy.entity.Student;
import com.hfut.glxy.mapper.ReplyMapper;
import com.hfut.glxy.mapper.StudentMapper;
import com.hfut.glxy.service.ReplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2017/12/19
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {

    private ReplyMapper replyMapper;

    private StudentMapper studentMapper;

    @Autowired
    protected ReplyServiceImpl(ReplyMapper replyMapper, StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
        this.replyMapper = replyMapper;
    }

    @Override
    public List<Reply> getCommentReplies(int cid) {
        List<Map<String, Object>> list = replyMapper.findCommentReplies(cid);
        List<Reply> res = new ArrayList<>(list.size() * 2);
        Reply reply;
        Student student;
        String sid;
        for (Map<String, Object> map : list) {
            reply = new Reply();
            reply.setId((Integer) map.get("id"));
            reply.setContent((String) map.get("content"));
            reply.setUpdateTime((Date) map.get("time"));
            sid = (String) map.get("s1");
            student = studentMapper.findNameAndImgById(sid);
            reply.setFrom(student.getName());
            reply.setHeadimg(student.getImg());
            sid = (String) map.get("s2");
            if (StringUtils.isNotEmpty(sid)) {
                student = studentMapper.findNameAndImgById(sid);
                reply.setTo(student.getName());
            }else {
                reply.setTo("");
            }
            res.add(reply);
        }
        return res;
    }

    @Override
    public Reply getReplyById(int id) {
        Reply reply = new Reply();
        Map<String, Object> map = replyMapper.findById(id);
        reply.setId((Integer) map.get("id"));
        reply.setContent((String) map.get("content"));
        reply.setUpdateTime((Date) map.get("time"));
        String sid = (String) map.get("s1");
        Student student = studentMapper.findNameAndImgById(sid);
        reply.setFrom(student.getName());
        reply.setHeadimg(student.getImg());
        sid = (String) map.get("s2");
        if (StringUtils.isNotEmpty(sid)) {
            student = studentMapper.findNameAndImgById(sid);
            reply.setTo(student.getName());
        }else {
            reply.setTo("");
        }


        return reply;
    }
}

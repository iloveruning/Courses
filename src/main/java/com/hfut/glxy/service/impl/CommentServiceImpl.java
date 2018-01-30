package com.hfut.glxy.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfut.glxy.entity.Comment;
import com.hfut.glxy.entity.Student;
import com.hfut.glxy.exception.TransactionalException;
import com.hfut.glxy.mapper.CommentMapper;
import com.hfut.glxy.mapper.CourseMapper;
import com.hfut.glxy.mapper.StudentMapper;
import com.hfut.glxy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 * @date: 2017/11/29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    private CommentMapper commentMapper;

    private StudentMapper studentMapper;

    private CourseMapper courseMapper;

    @Autowired
    protected CommentServiceImpl(CommentMapper commentMapper, StudentMapper studentMapper,
                                 CourseMapper courseMapper) {
        this.commentMapper = commentMapper;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
    }


    @Override
    @Transactional(rollbackFor = TransactionalException.class)
    public void agree(String studentId, int commentId) throws RuntimeException {

        //外键约束检查-----该评论是否存在

        //保存点赞数据
        try {
            commentMapper.saveAgreement(studentId, commentId);
        } catch (Exception e) {
            throw new TransactionalException("保存点赞数据失败");
        }

        //更新评论的点赞数
        int res = commentMapper.increaseAgreeCount(commentId);
        if (res != 1) {
            throw new TransactionalException("更新评论的点赞数失败");
        }

    }

    @Override
    @Transactional(rollbackFor = TransactionalException.class)
    public void cancelAgreement(String studentId, int commentId) throws RuntimeException {

        //删除点赞数据
        int res1 = commentMapper.deleteAgreement(studentId, commentId);
        if (res1 != 1) {
            throw new TransactionalException("删除点赞数据失败-该评论可能不存在");
        }

        //更新评论的点赞数
        int res2 = commentMapper.decreaseAgreeCount(commentId);
        if (res2 != 1) {
            throw new TransactionalException("更新评论的点赞数失败");
        }
    }

    @Override
    public boolean isAgreed(String studentId, int commentId) {

        return commentMapper.isAgreed(studentId, commentId) == 1;
    }

    @Override
    public PageInfo<Comment> getChapterComments(int pageNum, String sid, String cid) {
        PageHelper.startPage(pageNum, 5);
        List<Comment> comments = commentMapper.findByChapterId(cid);
        if (sid != null) {
            //个性化
            comments.forEach(it -> {
                Student stu = studentMapper.findNameAndImgById(it.getStudentId());
                it.setAuthor(stu.getName());
                it.setImg(stu.getImg());
                it.setIsAgreed(commentMapper.isAgreed(sid, it.getId()) == 1);
            });
        }

        return new PageInfo<>(comments);
    }


    @Override
    public Comment getCommentById(int id) {
        Comment comment = commentMapper.findById(id);
        if (comment == null) {
            return null;
        }
        String sid = comment.getStudentId();
        Student stu = studentMapper.findNameAndImgById(sid);
        comment.setAuthor(stu.getName());
        comment.setImg(stu.getImg());
        comment.setIsAgreed(false);
        return comment;
    }

    @Override
    public PageInfo<Map<String, Object>> getMyComments(int pageNum, String sid) {
        PageHelper.startPage(pageNum, 6);
        List<Comment> list = commentMapper.findByStudentId(sid);
        List<Map<String, Object>> res = new ArrayList<>(12);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        list.forEach(it -> {
            String chapterId = it.getChapterId();
            System.out.println("chapterId: " + chapterId);
            Map<String, Object> map = courseMapper.findCourseAndChapterInfoByChapterId(chapterId);
            map.put("time", sdf.format(it.getUpdateTime()));
            map.put("agreeCount", it.getAgreeCount());
            map.put("chapterId", chapterId);
            map.put("content", it.getContent());
            map.put("isAgree", commentMapper.isAgreed(sid, it.getId()));
            map.put("courseId", it.getId());
            map.put("reply", it.getReply());
            map.put("id", it.getId());
            res.add(map);
        });

        return new PageInfo<>(res);
    }

    @Override
    public boolean deleteMyComment(int cid, String sid) throws RuntimeException {
        return false;
    }
}

package com.hfut.glxy.controller;

import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Reply;
import com.hfut.glxy.mapper.CommentMapper;
import com.hfut.glxy.service.ReplyService;
import com.hfut.glxy.service.StudentService;
import com.hfut.glxy.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author chenliangliang
 * @date: 2017/12/19
 */
@RestController
@RequestMapping(value = "/reply")
//@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE})
public class ReplyController {


    private ReplyService replyService;

    private StudentService studentService;

    private CommentMapper commentMapper;

    @Autowired
    protected ReplyController(ReplyService replyService, StudentService studentService, CommentMapper commentMapper) {
        this.replyService = replyService;
        this.studentService = studentService;
        this.commentMapper=commentMapper;
    }


    /**
     * 发表回复
     *
     * @param reply
     * @param session
     * @param result
     * @return
     */
    @PostMapping
    public Result publish(@RequestBody Reply reply, HttpSession session,
                          BindingResult result) {
        String sid=(String)session.getAttribute("sid");
        if (sid==null){
            return ResultUtil.fail("登录后才能回复哦");
        }

        if (result.hasErrors() || reply.getCommentId() == null || reply.getCommentId() <= 0) {
            return ResultUtil.fail(result.getFieldErrors().toString());
        }


        try {
            //String sid = "2015213749";
            reply.setStudentId(sid);
            if (replyService.insert(reply)) {
                Reply rep = replyService.getReplyById(reply.getId());
                //更新评论回复数
                commentMapper.increaseReply(reply.getCommentId());
                return ResultUtil.OK(rep);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.insertError();
    }

    /**
     * 获取评论对应的所有回复
     *
     * @param cid
     * @return
     */
    @GetMapping("/{cid}")
    public Result getCommentReplies(@PathVariable(value = "cid") Integer cid) {

        if (cid == null || cid <= 0) {
            return ResultUtil.dataError();
        }

        try {
            List<Reply> replies = replyService.getCommentReplies(cid);
            return ResultUtil.OK(replies);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.selectError();
        }
    }
}

package com.hfut.glxy.controller;

import com.github.pagehelper.PageInfo;
import com.hfut.glxy.config.CommentConfig;
import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Comment;
import com.hfut.glxy.mapper.Course_ChapterDao;
import com.hfut.glxy.service.CommentService;
import com.hfut.glxy.service.StudentService;
import com.hfut.glxy.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2017/11/29
 */
@RestController
@RequestMapping(value = "/comment")
//@CrossOrigin(origins = "*",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.OPTIONS,RequestMethod.DELETE})
public class CommentController {

    private CommentService commentService;

    private StudentService studentService;



    @Autowired
    protected CommentController(CommentService commentService, StudentService studentService) {
        this.commentService = commentService;
        this.studentService = studentService;
    }

    @Resource
    private Course_ChapterDao course_chapterDao;


    /**
     * 发表评论
     *
     * @param comment
     * @param result
     * @param session
     * @return
     */
    @PostMapping("/student")
    public Result saveComment(@RequestBody @Valid Comment comment,
                              BindingResult result, HttpSession session) {
        String studentId = (String) session.getAttribute("sid");
        if (studentId==null){
            return ResultUtil.fail("登录后才能评论呦");
        }
        if (result.hasErrors()) {
            return ResultUtil.fail(result.getAllErrors().toString());
        }

        //String studentId = "2015213749";
        String chapterId = comment.getChapterId();

        //通过chapterId找到对应的课程号courseId
        String courseId = course_chapterDao.getCurrentCourseByChapter(chapterId);
        //String courseId = "561bt";
        //通过courseId在配置文件看评论是否需要审核isCheck

        Integer isCheck = CommentConfig.getIsCheck(courseId);
        //如果isCheck=0----不需要审核，将isPass=1
        if (isCheck == 0) {
            comment.setIsPass(1);
        }
        //如果isCheck=1----需要审核，将isPass=0
        if (isCheck == 1) {
            comment.setIsPass(0);
        }

        comment.setStudentId(studentId);

        try {
            if (commentService.insert(comment)) {
                comment = commentService.getCommentById(comment.getId());
                return ResultUtil.OK(comment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.insertError();
    }


    /**
     * 分页显示评论，根据学生id查询学生有无点赞
     *
     * @param chapterId
     * @param pageNum
     * @param session
     * @return
     */
    @GetMapping("/student/{chapterId}/{pageNum}")
    //@Cacheable(value = "readComments",key = "#chapterId+':'+#pageNum",cacheManager = "cacheManager")
    public Result listComments(@PathVariable(value = "chapterId") String chapterId,
                               @PathVariable(value = "pageNum", required = false) int pageNum,
                               HttpSession session) {

        if (pageNum <= 0) {
            pageNum = 1;
        }

        try {
            String studentId = (String) session.getAttribute("sid");
            //String studentId = "2015213749";
            PageInfo<Comment> comments = commentService.getChapterComments(pageNum, studentId, chapterId);

            return ResultUtil.OK(comments);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.selectError();
        }

    }

    /**
     * 老师设置是否审核评论
     *
     * @param isCheck
     * @param session
     * @return
     */
    @PostMapping("/teacher/setting/{isCheck}")
    public Result setting(@PathVariable("isCheck") Integer isCheck,
                          HttpSession session) {
        if (isCheck == null) {
            return ResultUtil.dataError();
        }

        String courseId = (String) session.getAttribute("courseId");
        //String courseId="7240f23v";
        Integer old = CommentConfig.getIsCheck(courseId);
        if (isCheck.intValue() != old.intValue()) {
            if (CommentConfig.setIsCheck(courseId, isCheck)) {
                return ResultUtil.OK();
            } else {
                return ResultUtil.fail("修改设置失败");
            }
        }
        return ResultUtil.OK();
    }





   /* @GetMapping("/teacher/check/{pageNum}")
    public Result checkComments(@PathVariable("pageNum") int pageNum,
                                HttpSession session){

        if (pageNum<=0){
            pageNum=1;
        }
        try {
            String courseId=(String) session.getAttribute("courseId");
            //通过courseId找到它的章号列表chapters
            commentService.selectPage(new Page<>(pageNum,12),
                    new EntityWrapper<Comment>()
            .)
        }catch (Exception e){
            e.printStackTrace();
        }

    }*/


    @GetMapping("/test/{key}/{value}")
    public Result test(@PathVariable("value") Integer value,
                       @PathVariable("key") String Key) {

        if (CommentConfig.setIsCheck(Key, value)) {
            return ResultUtil.success("写入配置文件成功", CommentConfig.getIsCheck("teacherId"));
        }
        return ResultUtil.fail("写入配置文件失败");
    }

    /**
     * 评论点赞
     *
     * @param commentId
     * @param session
     * @return
     */
    @PostMapping("/student/agree/{commentId}")
    public Result agree(@PathVariable("commentId") int commentId,
                        HttpSession session) {
        String studentId = (String) session.getAttribute("sid");
        if (studentId==null){
            return ResultUtil.fail("登录后才能点赞呦");
        }
        if (commentId <= 0) {
            return ResultUtil.dataError();
        }

        try {

            //String studentId ="2015213749";
            commentService.agree(studentId, commentId);
            return ResultUtil.OK();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.fail("点赞失败");
        }

    }

    /**
     * 取消点赞
     *
     * @param commentId
     * @param session
     * @return
     */
    @DeleteMapping("/student/agree/{commentId}")
    public Result cancelAgreement(@PathVariable("commentId") int commentId,
                                  HttpSession session) {
        String studentId = (String) session.getAttribute("sid");
        if (studentId==null){
            return ResultUtil.fail("登录后才能取消点赞呦");
        }
        if (commentId <= 0) {
            return ResultUtil.dataError();
        }

        try {
            //String studentId = "u84982";
            //String studentId ="2015213749";
            commentService.cancelAgreement(studentId, commentId);
            return ResultUtil.OK();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.fail("取消点赞失败--您可能还没有给该评论点赞");
        }

    }


    @GetMapping("/stu/my/{pageNum}")
    public Result getMyComment(@PathVariable("pageNum") int pageNum, HttpSession session){
        String sid = (String) session.getAttribute("sid");
        if (sid==null){
            return ResultUtil.fail("登录后才能显示自己的评论");
        }

        if (pageNum<=0){
            pageNum=1;
        }

        //String sid="2015213749";
        PageInfo<Map<String,Object>> pageInfo=commentService.getMyComments(pageNum,sid);
        return ResultUtil.OK(pageInfo);


    }


    /**
     * 删除我的评论
     * @param cid
     * @return
     */
   /* @DeleteMapping("/stu/{cid}")
    public Result deleteMyComment(@PathVariable("cid") int cid,HttpSession session){

        String sid=(String)session.getAttribute("id");


    }*/


}

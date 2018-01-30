package com.hfut.glxy.controller;

import com.github.pagehelper.PageInfo;
import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.StudentCourse;
import com.hfut.glxy.service.StudentCourseService;
import com.hfut.glxy.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author chenliangliang
 * @date: 2017/12/6
 */
@RestController
@RequestMapping(value = "/sc")
//@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE}, allowedHeaders = "*")
public class StudentCourseController {

    private StudentCourseService studentCourseService;



    @Autowired
    protected StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;

    }


    /**
     * 学生选课
     *
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/student")
    public Result choseCourse(@RequestBody Map<String, Object> map,
                              HttpSession session) {

        String cid = (String) map.get("cid");
        if (cid == null) {
            return ResultUtil.dataError();
        }

        String sid = (String) session.getAttribute("sid");
        //String sid="123456";
        if (studentCourseService.isChosed(sid, cid)) {
            //更新课程学习的最近时间
            if (studentCourseService.updateTime(sid, cid)) {
                return ResultUtil.OK();
            }

        } else {
            //添加学习记录
            if (studentCourseService.save(sid, cid)) {
                return ResultUtil.OK();
            }
        }
        return ResultUtil.unknownError();

    }

    /**
     * 学生取消选课
     * @param cid
     * @param session
     * @return
     */
    @DeleteMapping("/student/{cid}")
    public Result cancel(@PathVariable("cid") String cid,
                         HttpSession session){
        String sid=(String) session.getAttribute("sid");
        //String sid="123456";
        if (studentCourseService.isChosed(sid,cid)){
            if (studentCourseService.cancel(sid,cid)){
                return ResultUtil.OK();
            }else {
                return ResultUtil.deleteError();
            }

        }else {
            return ResultUtil.fail("您还没有选择这门课");
        }
    }


    /**
     * 老师获取选择这门课的学生
     * @param pageNum
     * @param session
     * @return
     */
    @GetMapping("/teacher/{pageNum}")
    public Result getChosenStudents(@PathVariable("pageNum") int pageNum,
                                    HttpSession session) {

        if (pageNum <= 0) {
            pageNum = 1;
        }
        String cid = (String) session.getAttribute("courseId");
        //String cid ="aaaaaa";
        try {
            PageInfo<StudentCourse> pageInfo = studentCourseService.getCourseStudents(cid, pageNum, 12);


            //根据学生id查询学生信息......(待完成...)

            return ResultUtil.OK(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.selectError();
        }
    }


    /**
     * 学生获取选择的课程信息
     * @param pageNum
     * @param session
     * @return
     */
    @GetMapping("/stu/{pageNum}")
    public Result getChosenCourses(@PathVariable("pageNum") int pageNum,
                                   HttpSession session){

        if (pageNum<=0){
            pageNum=1;
        }

        String sid=(String)session.getAttribute("sid");
        //String sid="11";
        try {
            PageInfo<Map<String,Object>> pageInfo = studentCourseService.getStudentCourses(sid, pageNum, 12);

            return ResultUtil.OK(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.selectError();
        }

    }


}

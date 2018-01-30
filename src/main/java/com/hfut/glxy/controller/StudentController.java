package com.hfut.glxy.controller;

import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Student;
import com.hfut.glxy.service.StudentService;
import com.hfut.glxy.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author chenliangliang
 * @date 2017/12/30
 */
@RestController
@RequestMapping(value = "/stu")
//@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE}, allowedHeaders = "*")
public class StudentController {

    private StudentService studentService;


    @Autowired
    protected StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    /**
     * 获取学生个人信息
     * @param session
     * @return
     */
    @GetMapping("/info")
    public Result getStudentInfo(HttpSession session) {

        String sid = (String) session.getAttribute("sid");
        //String sid ="2015213749";
        if (sid == null) {
            return ResultUtil.fail("用户未登录或登录过期");
        }

        Student stu = studentService.selectById(sid);
        return ResultUtil.OK(stu);

    }


    /**
     * 更新学生个人信息
     * @param student
     * @param session
     * @return
     */
    @PostMapping("/info/update")
    public Result updateInfo(@RequestBody Student student, HttpSession session){

        String sid = (String) session.getAttribute("sid");
        //String sid ="2015213749";
        student.setId(sid);
        if (studentService.updateById(student)){
            return ResultUtil.OK();
        }else {
            return ResultUtil.updateError();
        }

    }


    /*@PostMapping("/changePw")
    public Result changePassword(@RequestParam("oldPw") String oldPw,@RequestParam("newPw") String newPw,
                                 HttpSession session){

        String sid=(String)session.getAttribute("id");




    }*/


}

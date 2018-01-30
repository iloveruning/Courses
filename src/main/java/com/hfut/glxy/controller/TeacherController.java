package com.hfut.glxy.controller;

import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Picture;
import com.hfut.glxy.entity.Teacher;
import com.hfut.glxy.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/26 15:10 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Controller
public class TeacherController {

    private static final Logger logger= LoggerFactory.getLogger(ManagerController.class);

    @Resource
    private TeacherService teacherService;

    /*
         *
         * @Date 2017/11/26 15:54
         * @author students_ManagementSchool
         * @param param
         * @return
         * @since JDK 1.8
         * @condition  添加教师
    */
    @ResponseBody
    @RequestMapping(value = "/addTeacher",method = RequestMethod.POST)
    /*public Result<String> addTeacher(@RequestBody Param param){

        int isSuccess;

        Teacher teacher=param.teacher;
        String courseGroup_id=param.courseGroup_id;

        if (courseGroup_id==null){
            return new Result<>(false,"没有选择课程组",null);
        }
        if (teacher.getNumber()==null){
            return new Result<>(false,"教师编号不能为空",null);
        }
        if (teacher.getName()==null){
            return new Result<>(false,"教师姓名不能为空",null);
        }
        if (teacher.getSex()==null){
            return new Result<>(false,"教师性别不能为空",null);
        }

        String teacher_id= UUID.randomUUID().toString().replaceAll("-","");
        teacher.setId(teacher_id);

        try{
            isSuccess=teacherService.addTeacher(courseGroup_id,teacher);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"未知错误",null);
        }

        if (isSuccess==0){
            return new Result<>(false,"添加失败",null);
        }

        return new Result<>(true,"添加成功",null);
    }*/
    /**   
         * 
         * @Date 2017/11/28 14:50
         * @author students_ManagementSchool
         * @param teacher
         * @return
         * @since JDK 1.8
         * @condition
    */
    public Result<String> addTeacher(@RequestBody Teacher teacher){

        /*String courseGroup_id=teacher.getCourseGroup_id();

        if (courseGroup_id==null){
            return new Result<>(false,"没有选择课程组",null);
        }*/
        if (teacher.getNumber()==null){
            return new Result<>(false,"教师编号不能为空",null);
        }
        if (teacher.getName()==null){
            return new Result<>(false,"教师姓名不能为空",null);
        }
        if (teacher.getSex()==null){
            return new Result<>(false,"教师性别不能为空",null);
        }

        String teacher_id=UUID.randomUUID().toString().replaceAll("-","");
        teacher.setId(teacher_id);

        int isSuccess;

        try{
            isSuccess=teacherService.addTeacher(teacher);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"添加失败",null);
        }

        if (isSuccess==0){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"添加成功",null);
    }


    @ResponseBody
    @RequestMapping(value = "/updateTeacher",method = RequestMethod.POST)
    /**   
         * 
         * @Date 2017/11/28 15:37 
         * @author students_ManagementSchool
         * @param teacher
         * @return
         * @since JDK 1.8
         * @condition  更新教师
    */
    public Result<String> updateTeacher(@RequestBody Teacher teacher){

        if(teacher.getId()==null||teacher.getId().trim().isEmpty()){
            return new Result<>(false,"教师id不能为空",null);
        }
        /*String courseGroup_id=teacher.getCourseGroup_id();

        if (courseGroup_id==null){
            return new Result<>(false,"没有选择课程组",null);
        }*/
        if (teacher.getNumber()==null){
            return new Result<>(false,"教师编号不能为空",null);
        }
        if (teacher.getName()==null){
            return new Result<>(false,"教师姓名不能为空",null);
        }
        if (teacher.getSex()==null){
            return new Result<>(false,"教师性别不能为空",null);
        }

        int updateTeacherSuccess;

        try{
            updateTeacherSuccess=teacherService.updateTeacher(teacher);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"更新失败",null);
        }

        if (updateTeacherSuccess==0){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"更新成功",null);
    }

    /**
         *
         * @Date 2017/11/26 17:26
         * @author students_ManagementSchool
         * @param teacher
         * @return
         * @since JDK 1.8
         * @condition  将教师放入回收站
    */
    @ResponseBody
    @RequestMapping(value = "/deleteTeacher",method =RequestMethod.POST)
    public Result<String> deleteTeacher(@RequestBody Teacher teacher){

        int isSuccess;

        try{
            isSuccess=teacherService.deleteTeacher(teacher.getId());
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"删除失败",null);
        }

        if (isSuccess==0){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"删除成功",null);
    }



    @ResponseBody
    @RequestMapping(value = "/queryTeacherById",method = RequestMethod.POST)
    /**   
         * 
         * @Date 2017/11/28 16:56
         * @author students_ManagementSchool
         * @param teacher
         * @return
         * @since JDK 1.8
         * @condition  
    */
    public Result<Map> queryTeacherById(@RequestBody Teacher teacher){

        Map map;

        try{
            map=teacherService.queryTeacherById(teacher.getId());
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"未知错误",null);
        }

        /*if (map==null){
            return new Result<>(false,"查询失败",null);
        }*/

        return new Result<>(true,"查询成功",map);
    }


    /**
         *
         * @Date 2017/11/26 17:46
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  分页查询教师
    */

    @ResponseBody
    @RequestMapping(value = "/getAllTeachers",method = RequestMethod.GET)
    public Result<List<Map>> getAllTeachers(){

        List<Map> maps;

        try{
            maps=teacherService.getAllTeachers();
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"未知错误",null);
        }

        /*if (maps==null){
            return new Result<>(false,"查询失败",null);
        }*/

        return new Result<>(true,"查询成功",maps);
    }
}

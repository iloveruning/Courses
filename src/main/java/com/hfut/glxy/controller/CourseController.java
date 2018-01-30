package com.hfut.glxy.controller;

import com.hfut.glxy.dto.PageResult;
import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Course;
import com.hfut.glxy.entity.Manager;
import com.hfut.glxy.service.CourseService;
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
 * date: 2017/11/23 15:40 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Controller
//@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE}, allowedHeaders = "*")
public class CourseController {

    @Resource
    private CourseService courseService;


   /* @ResponseBody
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public Result<String> test(@RequestBody List<Map<String,Object>> maps){

        try {
            Chapter chapter=(Chapter) MapToObjectUtils.mapToBean(maps.get(0), Chapter.class);
            Course course = (Course) MapToObjectUtils.mapToBean(maps.get(1), Course.class);
            System.out.println("chaptername："+chapter.getName());
            System.out.println("chapteNumber："+chapter.getNumber());
            System.out.println("courseid："+course.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result<>(true,"gg",null);
    }*/



    /**
         *
         * @Date 2017/11/23 16:58
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition 增加课程的校验过程以及数据传输过程（包括向数据库层传以及返回前台响应信息）
    */
    @ResponseBody
    @RequestMapping(value="/addCourse",method= RequestMethod.POST)
    public Result<String> addCourse(@RequestBody Course course){

        /*String courseGroup_id=course.getCourseGroup_id();*/
        Manager manager=new Manager();
        manager.setAccount(course.getManager_account());
        manager.setPassword(course.getManager_password());

        if(manager.getAccount()==null||manager.getAccount().trim().isEmpty()){
            return new Result<>(false,"没有指定管理员账户",null);
        }
        if (manager.getPassword()==null||manager.getPassword().trim().isEmpty()){
            return new Result<>(false,"没有指定管理员密码",null);
        }
        if (course.getNumber()==null||course.getNumber().trim().isEmpty()){
            return new Result<String>(false,"没有指定课程编号",null);
        }
        if (course.getName()==null||course.getName().trim().isEmpty()){
            return new Result<String>(false,"没有指定课程名称",null);
        }
        /*if (course.getType()==null){
            return new Result<String>(false,"没有指定课程类型",null);
        }
        if (course.getCredit()==null){
            return new Result<String>(false,"没有指定课程学分",null);
        }
        if (course.getHours()==null){
            return new Result<String>(false,"没有指定课程学时",null);
        }*/

        /*if (result.hasErrors()){
            return new Result<String>(false,result.toString(),null);
        }*/

        course.setId(UUID.randomUUID().toString().replaceAll("-",""));
        manager.setId(UUID.randomUUID().toString().replaceAll("-",""));

        int success;

        try {
            success = courseService.addCourse(course, manager);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"增加失败",null);
        }
        if(success==0){
            return new Result<>(false,"未知错误",null);
        }else{
            return new Result<>(true,"添加成功",null);
        }
    }

    /**
         *
         * @Date 2017/11/30 17:03
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition 更新课程
    */
    @ResponseBody
    @RequestMapping(value="/updateCourse",method= RequestMethod.POST)
    public Result<String> updateCourse(@RequestBody Course course){

        Manager manager=new Manager();
        manager.setAccount(course.getManager_account());
        manager.setPassword(course.getManager_password());

        /*if (result.hasErrors()){
            String message=result.getFieldError().getDefaultMessage();
            return new Result<>(false,message,null);
        }*/

        /*if (course.getCourseGroup_id()==null||course.getCourseGroup_id().trim().isEmpty()){
            return new Result<>(false,"没有指定课程组",null);
        }
        *//*if (course.getTeacher_id()==null||course.getTeacher_id().trim().isEmpty()){
            return new Result<>(false,"没有指定负责教师",null);
        }*//*
        if (course.getTeacher_ids()==null||course.getTeacher_ids().length==0){
            return new Result<>(false,"没有指定负责教师",null);
        }*/
        if(manager.getAccount()==null||manager.getAccount().trim().isEmpty()){
            return new Result<>(false,"没有指定管理员账户",null);
        }
        if (manager.getPassword()==null||manager.getPassword().trim().isEmpty()){
            return new Result<>(false,"没有指定管理员密码",null);
        }
        if (course.getNumber()==null||course.getNumber().trim().isEmpty()){
            return new Result<String>(false,"没有指定课程编号",null);
        }
        if (course.getName()==null||course.getName().trim().isEmpty()){
            return new Result<String>(false,"没有指定课程名称",null);
        }

        int isSuccess;

        try{
            isSuccess=courseService.updateCourse(course,manager);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"更新失败",null);
        }

        if (isSuccess==0){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"更新成功",null);
    }

    /**
         *
         * @Date 2017/12/2 11:30
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition  删除课程
    */
    @ResponseBody
    @RequestMapping(value = "/deleteCourseById",method = RequestMethod.POST)
    public Result<String> deleteCourseById(@RequestBody Course course){

        String course_id=course.getId();
        if (course_id==null||course_id.trim().isEmpty()){
            return new Result<>(false,"参数不能为空",null);
        }

        int isSuccess;
        try {
            isSuccess = courseService.putToDustbin(course_id);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"删除失败",null);
        }

        if (isSuccess==0){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"删除成功",null);
    }

    /**
         *
         * @Date 2017/12/2 11:06
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition  根据id查询课程
    */
    @ResponseBody
    @RequestMapping(value = "/queryCourseById",method = RequestMethod.POST)
    public Result<Map> queryCourseById(@RequestBody Course course){

        String course_id=course.getId();

        Map map;
        try {
            map=courseService.queryCourseById(course_id);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"查询失败",null);
        }

        return new Result<>(true,"查询成功",map);
    }

    /**
         *
         * @Date 2017/12/2 11:08
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition 获取全部课程
    */
    @ResponseBody
    @RequestMapping(value = "/getAllCourses",method = RequestMethod.GET)
    public Result<List<Course>> getAllCourses(){

        List<Course> courses;
        try {
            courses=courseService.getAllCourses();
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"查询失败",null);
        }

        return new Result<>(true,"查询成功",courses);
    }

    /**
         *
         * @Date 2018/1/15 22:34
         * @author students_ManagementSchool
         * @param map
         * @return
         * @since JDK 1.8
         * @condition  分页查询课程
    */
    @ResponseBody
    @RequestMapping(value = "/getCoursesByPage",method = RequestMethod.POST)
    public Result<PageResult<Map>> getCoursesByPage(@RequestBody Map map){

        /*System.out.println(map.toString());
        System.out.println(map.get("aoData"));
        System.out.println(map.get("iDisplayLength"));
        System.out.println((int)map.get("iDisplayStart"));*/

        int startPage=(int)map.get("iDisplayStart");
        int pageSize=(int)map.get("iDisplayLength");

        PageResult<Map> courses;

        try{
            courses=courseService.getCoursesByPage(startPage,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"查询失败",null);
        }

        return new Result<>(true,"查询成功",courses);

    }
}

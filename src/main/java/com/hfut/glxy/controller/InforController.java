package com.hfut.glxy.controller;

import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.*;
import com.hfut.glxy.mapper.PictureDao;
import com.hfut.glxy.service.InforService;
import javafx.scene.chart.ValueAxis;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Adler32;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/6 15:18 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Controller
//@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE}, allowedHeaders = "*")
public class InforController {

    @Resource
    private InforService inforService;
    @Resource
    private PictureDao pictureDao;

    /**
     *
     * @Date 2017/12/6 15:26
     * @author students_ManagementSchool
     * @return
     * @since JDK 1.8
     * @condition  获取所有的课程以及教师
     */
    @ResponseBody
    @RequestMapping(value="/getCourseGroup_Teacher",method = RequestMethod.GET)
    public Result<Map> getCourseGroup_Teacher(){

        Map map;

        try{
            map=inforService.getCourseGroup_Teacher();
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"请求失败",null);
        }

        return new Result<>(true,"请求成功",map);
    }

    /**
         *
         * @Date 2017/12/6 19:40
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取全部课程组
    */
    @ResponseBody
    @RequestMapping(value = "/getCourseGroups",method = RequestMethod.GET)
    public Result<List<CourseGroup>> getCourseGroups(){

        List<CourseGroup> courseGroups;

        try{
            courseGroups=inforService.getCourseGroups();
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"请求失败",null);
        }

        return new Result<>(true,"请求成功",courseGroups);
    }

    /**
         *
         * @Date 2017/12/7 12:04
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition 获取所有的课程
    */
    @ResponseBody
    @RequestMapping(value = "/getCourses",method = RequestMethod.GET)
    public Result<List<Course>> getCourses(){

        List<Course> courses;

        try{
            courses=inforService.getCourses();
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"获取失败",null);
        }

        return new Result<>(true,"获取成功",courses);
    }

    /**   
         * 
         * @Date 2017/12/7 16:31
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition  获取课程组的详细信息
    */
    @ResponseBody
    @RequestMapping(value = "/getCourse_Teacher",method = RequestMethod.POST)
    public Result<Map> getCourse_Teacher(@RequestBody CourseGroup courseGroup){

        Map map;

        try{
            map=inforService.getCourse_Teacher(courseGroup.getId());
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"获取失败",null);
        }

        return new Result<>(true,"获取成功",map);
    }

    /**   
         * 
         * @Date 2017/12/23 16:15
         * @author students_ManagementSchool
         * @param teacher
         * @return
         * @since JDK 1.8
         * @condition  获取某一教师的基本信息以及其所教授的课程
    */
    @ResponseBody
    @RequestMapping(value = "/getTeacher_Courses",method = RequestMethod.POST)
    public Result<Map> getTeacher_Courses(@RequestBody Teacher teacher){

        String teacher_id=teacher.getId();
        if (teacher_id==null||teacher_id.trim().isEmpty()){
            return new Result<>(false,"参数错误",null);
        }

        Map map;

        try{
            map=inforService.getTeacher_Courses(teacher_id);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"索取失败",null);
        }

        return new Result<>(true,"获取成功",map);
    }

    /*@ResponseBody
    @RequestMapping(value = "/queryPictureById",method = RequestMethod.POST)
    public Result<Picture> queryPictureById(@RequestBody Picture picture){

        Picture pictureG=pictureDao.queryPictureById(picture.getId().toString());
        return new Result<>(true,"获取成功",pictureG);
    }*/

    /**
         *
         * @Date 2018/1/6 18:51
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition  前台页面获取课程详细信息
    */
    @ResponseBody
    @RequestMapping(value = "/getCourseDetail",method =RequestMethod.POST )

    public Result<Map> getCourseDetail(@RequestBody Course course){

        String course_id=course.getId();

        if (course_id==null||course_id.trim().isEmpty()){
            return new Result<>(false,"参数错误",null);
        }

        Map map;

        try{
            map=inforService.getCourseDetail(course_id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"数据获取异常",null);
        }

        return new Result<>(true,"获取成功",map);
    }

    /**   
         * 
         * @Date 2018/1/11 14:35
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition  获取某一教学单元的详细信息
    */
    @ResponseBody
    @RequestMapping(value = "/getUnitDetail",method = RequestMethod.POST)
    public Result<Map> getUnitDetail(@RequestBody Unit unit){

        String unit_id=unit.getId();

        if (unit_id==null||unit_id.trim().isEmpty()){
            return new Result<>(false,"参数错误",null);
        }

        Map map;

        try{
            map=inforService.getUnitDetail(unit_id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"数据获取异常",null);
        }

        return new Result<>(true,"获取成功",map);

    }

    /**   
         * 
         * @Date 2018/1/19 16:43
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取首页的信息：课程组名，课程名，课程类型（核心、辅助）、课程id、课程图片
     */
    @ResponseBody
    @RequestMapping(value = "/getHomepageInfo",method = RequestMethod.GET)
    public Result<List<Map>> getHomepageInfo(){

        List<Map> maps=null;

        try{
            maps=inforService.getHomepageInfo();
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(true,"获取失败",maps);
        }

        return new Result<>(true,"获取成功",maps);
    }

    /**
         *
         * @Date 2018/1/19 17:53
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取教学团队
    */
    @ResponseBody
    @RequestMapping(value = "/getTeacherTeam",method = RequestMethod.GET)
    public Result<List<Map>> getTeacherTeam() {

        List<Map> maps;

        try{
            maps=inforService.getTeacherTeam();
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"获取失败",null);
        }

        return new Result<>(true,"获取成功",maps);
    }
}

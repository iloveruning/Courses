package com.hfut.glxy.controller;

import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.CourseGroup;
import com.hfut.glxy.service.CourseGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/26 9:53 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Controller
public class CourseGroupController {

    private static final Logger logger= LoggerFactory.getLogger(ManagerController.class);

    @Resource
    private CourseGroupService courseGroupService;

    /*
     * @param courseGroup
     * @param result
     * @return
     * @Date 2017/11/26 10:56
     * @author students_ManagementSchool
     * @condition 增加课程组
     * @since JDK 1.8
*/
    @ResponseBody
    @RequestMapping(value = "/addCourseGroup", method = RequestMethod.POST)
    /*public Result<String> addCourseGroup(@RequestBody @Valid CourseGroup courseGroup, BindingResult result) {
        int isSuccess;

        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return new Result<>(false, "" + message, null);
        }

        String courseGroup_id= UUID.randomUUID().toString().replaceAll("-","");
        courseGroup.setId(courseGroup_id);

        try {
            isSuccess = courseGroupService.addCourseGroup(courseGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, "未知错误", null);
        }

        if (isSuccess == 0) {
            return new Result<>(false, "添加失败", null);
        }

        return new Result<>(true, "添加成功", null);
    }*/

    /**
         *
         * @Date 2017/11/27 16:10
         * @author students_ManagementSchool
         * @param param
         * @return
         * @since JDK 1.8
         * @condition  增加课程组
    */
    public Result<String> addCourseGroup(@RequestBody CourseGroup courseGroup ){

        /*CourseGroup courseGroup=param.courseGroup;
        Picture picture=param.picture;*/

        System.out.println("the method addCourseGroup is being used");

        /*courseGroup=courseGroup_picture.getCourseGroup();
        picture=courseGroup_picture.getPicture();*/

        /*courseGroup=(CourseGroup) JSONObject.parse("courseGroup");
        picture=(Picture)map.get("picture");*/

        /*try {
            courseGroup = JsonXMLUtils.map2obj((Map<String, Object>) models.get("courseGroup"), CourseGroup.class);
            picture=JsonXMLUtils.map2obj((Map<String, Object>) models.get("picture"), Picture.class);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"数据解析失败",null);
        }*/

        if (courseGroup.getNumber()==null){
            return new Result<>(false,"课程组编号不能为空",null);
        }
        if (courseGroup.getName()==null){
            return new Result<>(false,"课程组名称不能为空",null);
        }

        String courseGroup_id= UUID.randomUUID().toString().replaceAll("-","");
        courseGroup.setId(courseGroup_id);

        int isSuccess;
        try {
            isSuccess = courseGroupService.addCourseGroup(courseGroup);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"添加失败",null);
        }

        if (isSuccess==0){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"添加成功",null);
    }

    /*class CourseGroupParam implements Serializable{
        public CourseGroupParam(){}
        public CourseGroup courseGroup;
        public Picture picture;
    }*/

    /*
         *
         * @Date 2017/11/26 11:13
         * @author students_ManagementSchool
         * @param courseGroup
         * @param result
         * @return
         * @since JDK 1.8
         * @condition  更新课程组
    */
    @ResponseBody
    @RequestMapping(value = "/updateCourseGroup", method = RequestMethod.POST)
    /*public Result<String> updateCourseGroup(@RequestBody @Valid CourseGroup courseGroup, BindingResult result) {
        int isSuccess;

        if(courseGroup.getId()==null){
            return new Result<>(false,"传参错误，Id为空",null);
        }

        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return new Result<>(false, "" + message, null);
        }

        try{
            isSuccess=courseGroupService.updateCourseGroup(courseGroup);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"未知错误",null);
        }

        if (isSuccess==0) {
            return new Result<>(false, "更新失败", null);
        }

        return new Result<>(true,"更新成功",null);
    }*/
    /**   
         * 
         * @Date 2017/11/29 10:21 
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition  
    */
    public Result<String> updateCourseGroup(@RequestBody CourseGroup courseGroup){

        /*CourseGroup courseGroup=param.courseGroup;
        Picture picture=param.picture;*/

        if (courseGroup.getNumber()==null){
            return new Result<>(false,"课程组编号不能为空",null);
        }
        if (courseGroup.getName()==null){
            return new Result<>(false,"课程组名称不能为空",null);
        }

        int isSuccess;

        try{
            isSuccess=courseGroupService.updateCourseGroup(courseGroup);
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
         * @Date 2017/11/26 11:31
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition 将课程组放入回收站
    */
    @ResponseBody
    @RequestMapping(value="/deleteCourseGroup",method = RequestMethod.POST)
    /*public Result<String> deleteCourseGroup(@RequestBody CourseGroup courseGroup){*/
    public Result<String> deleteCourseGroup(@RequestBody CourseGroup courseGroup){
        int isSuccess;

        String courseGroup_id=courseGroup.getId();

        /*if(courseGroup.getId()==null){
            return new Result<>(false,"传参错误，Id为空",null);
        }*/
        if(courseGroup_id==null){
            return new Result<>(false,"传参错误，Id为空",null);
        }

        try{
            isSuccess=courseGroupService.deleteCourseGroup(courseGroup_id);
            /*isSuccess=courseGroupService.deleteCourseGroup(courseGroup.getId());*/
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"删除失败",null);
        }

        if (isSuccess==0){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"删除成功",null);
    }

    /*
         *
         * @Date 2017/11/26 11:47
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition 获取全部课程组
    */
    @ResponseBody
    @RequestMapping(value = "/getAllCourseGroups",method = RequestMethod.GET)
    /*public Result<List<CourseGroup>> getAllCourseGroups(){

        List<CourseGroup> courseGroups;

        try{
            courseGroups=courseGroupService.getAllCourseGroups();
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"获取失败",null);
        }

        return new Result<>(true,"获取成功",courseGroups);
    }*/
    /**   
         * 
         * @Date 2017/11/27 20:38
         * @author students_ManagementSchool
         * @param 
         * @return
         * @since JDK 1.8
         * @condition  获取全部课程组及图片
    */
    public Result<List<Map>> getAllCourseGroups(){

        List<Map> maps;

        try{
            maps=courseGroupService.getAllCourseGroups();
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"获取成功",maps);
    }


    /*
         *
         * @Date 2017/11/26 11:57
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition  根据id获取课程组
    */
    @ResponseBody
    @RequestMapping(value = "/queryCourseGroupById",method = RequestMethod.POST)
    /*public Result<CourseGroup> queryCourseGroupById(@RequestBody CourseGroup courseGroup){

        CourseGroup courseGroupG;

        try{
            courseGroupG=courseGroupService.queryCourseGroupById(courseGroup.getId());
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"未知错误",null);
        }

        if (courseGroupG==null){
            return new Result<>(false,"获取失败",null);
        }

        return new Result<>(true,"获取成功",courseGroupG);
    }*/

    /**   
         * 
         * @Date 2017/11/27 20:26
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition  根据课程组id获取课程组及相应图片
    */
    public Result<Map> queryCourseGroupById(@RequestBody CourseGroup courseGroup){

        Map map;

        try{
            map=courseGroupService.queryCourseGroupById(courseGroup.getId());
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"未知错误",null);
        }

        /*if (map==null){
            return new Result<>(false,"获取失败",null);
        }*/

        return new Result<>(true,"获取成功",map);
    }

}

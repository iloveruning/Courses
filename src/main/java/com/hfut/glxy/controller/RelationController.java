package com.hfut.glxy.controller;

import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Course;
import com.hfut.glxy.entity.Office;
import com.hfut.glxy.service.RelationService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.boot.autoconfigure.social.FacebookAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/28 11:01 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Controller
public class RelationController {

    @Resource
    private RelationService relationService;

    /**   
         * 
         * @Date 2017/12/28 11:05 
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition  更新课程所处的课程组
    */
    @ResponseBody
    @RequestMapping(value = "/updateRelation_courseGroup_course",method = RequestMethod.POST)
    public Result<String> updateRelation_courseGroup_course(@RequestBody Course course){

        String courseGroup_id=course.getCourseGroup_id();
        String course_id=course.getId();
        /*String[] teacher_ids=course.getTeacher_ids();*/

        if (courseGroup_id==null||courseGroup_id.trim().isEmpty()){
            return new Result<>(false,"课程组不能为空",null);
        }
        if (course_id==null||course_id.trim().isEmpty()){
            return new Result<>(false,"传参失败，未选择相应课程",null);
        }
        /*if (teacher_ids==null){
            return new Result<>(false,"负责教师不能为空",null);
        }*/

        int updateRelationSuccess;

        try{
            updateRelationSuccess=relationService.updateRelation_courseGroup_course(course);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"更新失败",null);
        }

        if (updateRelationSuccess!=1){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"更新成功",null);
    }

    /**
         *
         * @Date 2018/1/13 20:54
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition 更新课程的负责教师
    */
    @ResponseBody
    @RequestMapping(value = "/updateRelation_teacher_course",method = RequestMethod.POST)
    public Result<String> updateRelation_teacher_course(@RequestBody Course course){

        String courseGroup_id=course.getCourseGroup_id();
        String course_id=course.getId();
       /* String[] teacher_ids=course.getTeacher_ids();*/

        if (courseGroup_id==null||courseGroup_id.trim().isEmpty()){
            return new Result<>(false,"课程组不能为空",null);
        }
        if (course_id==null||course_id.trim().isEmpty()){
            return new Result<>(false,"传参失败，未选择相应课程",null);
        }
        /*if (teacher_ids==null){
            return new Result<>(false,"负责教师不能为空",null);
        }*/
        int updateRelationSuccess;

        try{
            updateRelationSuccess=relationService.updateRelation_teacher_course(course);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"更新失败",null);
        }

        if (updateRelationSuccess!=1){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"更新成功",null);
    }

    /**
         *
         * @Date 2018/1/13 21:02
         * @author students_ManagementSchool
         * @param map
         * @return
         * @since JDK 1.8
         * @condition 绑定教学资料和教学单元
    */
    @ResponseBody
    @RequestMapping(value = "/bindUnit_offices",method = RequestMethod.POST)
    public Result<List<Office>> bindUnit_offices(@RequestBody Map map){

        String unit_id= (String) map.get("unit_id");
        if (unit_id==null||unit_id.trim().isEmpty()){
            return new Result<>(false,"未选择教学单元",null);
        }

        List<String> office_ids= (List<String>) map.get("office_ids");
        if (office_ids==null||office_ids.size()==0){
            return new Result<>(false,"未上传任何资料",null);
        }

        /*for (String office_id:
             office_ids) {
            System.out.println("office_id: "+office_id);
        }*/

        List<Office> offices;
        try{
            offices=relationService.bindUnit_offices(unit_id,office_ids);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"添加失败",null);
        }

        return new Result<>(true,"添加成功",offices);

    }

}

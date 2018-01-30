package com.hfut.glxy.controller;

import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.KnowledgePoint;
import com.hfut.glxy.mapper.KnowledgePointDao;
import com.hfut.glxy.service.KnowledgePointService;
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
 * date: 2018/1/5 20:37 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Controller
//@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE}, allowedHeaders = "*")
public class KnowledgePointController {

    @Resource
    private KnowledgePointService knowledgePointService;
    @Resource
    private KnowledgePointDao knowledgePointDao;

    /**   
         * 
         * @Date 2018/1/5 21:14
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  增加知识点
    */
    @ResponseBody
    @RequestMapping(value = "/addKnowledgePoint",method = RequestMethod.POST)
    public Result<KnowledgePoint> addKnowledgePoint(@RequestBody KnowledgePoint knowledgePoint){

        /*if (knowledgePoint.getUnit_id()==null||knowledgePoint.getUnit_id().trim().isEmpty()){
            return new Result<>(false,"课时id未选择",null);
        }*/
        if (knowledgePoint.getContent()==null||knowledgePoint.getContent().trim().isEmpty()){
            return new Result<>(false,"知识点内容不能为空",null);
        }

        String id= UUID.randomUUID().toString().replaceAll("-","");
        knowledgePoint.setId(id);

        KnowledgePoint knowledgePointG;//刚添加的知识点

        try{
            knowledgePointG=knowledgePointService.addKnowledgePoint(knowledgePoint);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"插入过程出现异常",null);
        }

        if (knowledgePointG==null){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"添加成功",knowledgePointG);

    }

    /**   
         * 
         * @Date 2018/1/5 21:30
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  更新知识点
    */
    @ResponseBody
    @RequestMapping(value = "/updateKnowledgePoint",method = RequestMethod.POST)
    public Result<String> updateKnowledgePoint(@RequestBody KnowledgePoint knowledgePoint){

        if (knowledgePoint.getId()==null||knowledgePoint.getId().trim().isEmpty()){
            return new Result<>(false,"id未选择",null);
        }
        if (knowledgePoint.getContent()==null||knowledgePoint.getContent().trim().isEmpty()){
            return new Result<>(false,"知识点内容不能为空",null);
        }

        int updateSuccess;
        try {
            updateSuccess = knowledgePointService.updateKnowledgePoint(knowledgePoint);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"更新异常",null);
        }
        if (updateSuccess!=1){
            return new Result<>(false,"更新失败",null);
        }

        return new Result<>(true,"更新成功",null);
    }

    /**   
         * 
         * @Date 2018/1/5 21:56
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  删除知识点及其与相关教学单元的关系
    */
    @ResponseBody
    @RequestMapping(value = "/deleteKnowledgePoint",method = RequestMethod.POST)
    public Result<String> deleteKnowledgePoint(@RequestBody KnowledgePoint knowledgePoint){

        if (knowledgePoint.getId()==null||knowledgePoint.getId().trim().isEmpty()){
            return new Result<>(false,"id未选择",null);
        }

        int deleteSuccess;
        try{
            deleteSuccess=knowledgePointService.deleteKnowledgePoint(knowledgePoint);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"删除失败",null);
        }

        if (deleteSuccess!=1){
            return new Result<>(false,"删除异常",null);
        }

        return new Result<>(true,"删除成功",null);
    }

    /**   
         * 
         * @Date 2018/1/5 22:34
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  删除知识点与教学单元间的关系
    */
    @ResponseBody
    @RequestMapping(value = "/releaseUnit_knowledgePointRelation",method = RequestMethod.POST)
    public Result<String> releaseRelation(@RequestBody KnowledgePoint knowledgePoint){

        if (knowledgePoint.getUnit_id()==null||knowledgePoint.getUnit_id().trim().isEmpty()){
            return new Result<>(false,"课时id未选择",null);
        }
        if (knowledgePoint.getId()==null||knowledgePoint.getId().trim().isEmpty()){
            return new Result<>(false,"id未选择",null);
        }

        int releaseRelation;
        try{
            releaseRelation=knowledgePointService.releaseRelation(knowledgePoint);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"删除失败",null);
        }

        if (releaseRelation!=1){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"删除成功",null);
    }

    /**   
         * 
         * @Date 2018/1/6 13:27
         * @author students_ManagementSchool
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition 获取与某教学单元相关的所有知识点
    */
    @ResponseBody
    @RequestMapping(value = "/getKnowledgePointsByUnit",method = RequestMethod.POST)
    public Result<List<KnowledgePoint>> getKnowledgePointsByUnit(@RequestParam String unit_id){

        if (unit_id==null||unit_id.trim().isEmpty()){
            return new Result<>(false,"教学单元id不能为空",null);
        }

        List<KnowledgePoint> knowledgePoints;

        try{
            knowledgePoints=knowledgePointService.getKnowledgePointsByUnit(unit_id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"数据获取异常",null);
        }
        

        return new Result<>(true,"获取成功",knowledgePoints);
    }

    /**
         *
         * @Date 2018/1/6 13:32
         * @author students_ManagementSchool
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition
    */
    @ResponseBody
    @RequestMapping(value = "/getKnowledgePointsByChapter",method = RequestMethod.POST)
    public Result<List<KnowledgePoint>> getKnowledgePointsByChapter(@RequestParam String chapter_id){

        if (chapter_id==null||chapter_id.trim().isEmpty()){
            return new Result<>(false,"章id不能为空",null);
        }

        List<KnowledgePoint> knowledgePoints;

        try{
            knowledgePoints=knowledgePointService.getKnowledgePointsByChapter(chapter_id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"数据获取异常",null);
        }


        return new Result<>(true,"获取成功",knowledgePoints);

    }

    /**
         *
         * @Date 2018/1/6 16:14
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition 获取与某课程相关的所有知识点
     */
    @ResponseBody
    @RequestMapping(value = "/getKnowledgePointsByCourse",method = RequestMethod.POST)
    public Result<List<KnowledgePoint>> getKnowledgePointsByCourse(@RequestParam String course_id){

        if (course_id==null||course_id.trim().isEmpty()){
            return new Result<>(false,"课程id不能为空",null);
        }

        List<KnowledgePoint> knowledgePoints;

        try{
            knowledgePoints=knowledgePointService.getKnowledgePointsByCourse(course_id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"数据获取异常",null);
        }


        return new Result<>(true,"获取成功",knowledgePoints);

    }

    /**
         *
         * @Date 2018/1/6 13:39
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition   获取与某一知识点相关的所有教学单元(模糊查询)
    */
    @ResponseBody
    @RequestMapping(value = "/getUnitsByKnowledgePoint",method = RequestMethod.POST)
    public Result<List<Map>> getUnitsByKnowledgePoint(@RequestBody KnowledgePoint knowledgePoint){

        if (knowledgePoint.getId()==null||knowledgePoint.getId().trim().isEmpty()){
            return new Result<>(false,"id未选择",null);
        }

        List<Map> maps;

        try{
            maps=knowledgePointService.getUnitsByKnowledgePoint(knowledgePoint.getId());
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"数据获取异常",null);
        }

        return new Result<>(true,"获取成功",maps);
    }

    /**
         *
         * @Date 2018/1/6 14:44
         * @author students_ManagementSchool
         * @param test_id
         * @return
         * @since JDK 1.8
         * @condition  测试接口
    */
    @ResponseBody
    @RequestMapping(value = "/testsql",method = RequestMethod.POST)
    public Result<String[]> testsql(@RequestParam String test_id){

        String [] ids=null;

        try{
            ids=knowledgePointService.test(test_id);
        }catch(Exception e){
            e.printStackTrace();
        }

        return new Result<>(false,"test",ids);
    }

    /**   
         * 
         * @Date 2018/1/6 15:38
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  增加知识点与教学单元的关联
    */
    @ResponseBody
    @RequestMapping(value = "/addUnit_knowledgePointRelation",method = RequestMethod.POST)
    public Result<KnowledgePoint> addRelation(@RequestBody KnowledgePoint knowledgePoint){

        if (knowledgePoint.getUnit_id()==null||knowledgePoint.getUnit_id().trim().isEmpty()){
            return new Result<>(false,"课时id未选择",null);
        }
        if (knowledgePoint.getId()==null||knowledgePoint.getId().trim().isEmpty()){
            return new Result<>(false,"id未选择",null);
        }

        int deleteRelationSuccess;
        try{
            deleteRelationSuccess=knowledgePointService.addRelation(knowledgePoint);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"添加失败",null);
        }

        if (deleteRelationSuccess!=1){
            return new Result<>(false,"未知错误",null);
        }

        knowledgePoint= knowledgePointDao.queryKnowledgePointById(knowledgePoint.getId());

        return new Result<>(true,"添加成功",knowledgePoint);

    }

}

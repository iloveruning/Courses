package com.hfut.glxy.controller;

import com.hfut.glxy.dto.PageResult;
import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Unit;
import com.hfut.glxy.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/13 22:32 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE}, allowedHeaders = "*")
public class UnitController {

    @Resource
    private UnitService unitService;


    /**
         *
         * @Date 2017/12/13 22:39
         * @author students_ManagementSchool
         * @param unit
         * @param result
         * @return
         * @since JDK 1.8
         * @condition   添加小节
    */
    @ResponseBody
    @RequestMapping(value = "/addUnit",method = RequestMethod.POST)
    public Result<Unit> addUnit(@RequestBody @Valid Unit unit, BindingResult result){

        if (result.hasErrors()){
            String message=result.getFieldError().getDefaultMessage();
            return new Result<>(false,""+message,null);
        }

        String unit_id= UUID.randomUUID().toString().replaceAll("-","");
        unit.setId(unit_id);

        int isSuccess;

        try{
            isSuccess=unitService.addUnit(unit);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"添加失败",null);
        }

        if (isSuccess==0){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"添加成功",unit);
    }

    /**
         *
         * @Date 2017/12/17 18:37
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition
    */
    @ResponseBody
    @RequestMapping(value = "/updateUnit",method = RequestMethod.POST)
    public Result<String> updateUnit(@RequestBody Unit unit){

        /*if (result.hasErrors()){
            String message=result.getFieldError().getDefaultMessage();
            return new Result<>(false,""+message,null);
        }*/

        if (unit.getId()==null||unit.getId().trim().isEmpty()){
            return new Result<>(false,"教学单元id不能为空",null);
        }
        /*if (unit.getNumber()==null||unit.getNumber().trim().isEmpty()){
            return new Result<>(false,"教学单元编号不能为空",null);
        }
        if (unit.getName()==null||unit.getName().trim().isEmpty()){
            return new Result<>(false,"教学单元名称不能为空",null);
        }*/

        int isSuccess;

        try{
            isSuccess=unitService.updateUnit(unit);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"更新失败",null);
        }

        if (isSuccess!=1){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"更新成功",null);
    }

    /**
         *
         * @Date 2017/12/17 18:55
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition 删除小节
    */
    @ResponseBody
    @RequestMapping(value = "/deleteUnit",method = RequestMethod.POST)
    public Result<String> deleteUnit(@RequestBody Unit unit){

        if (unit.getChapter_id()==null||unit.getChapter_id().trim().isEmpty()){
            return new Result<>(false,"未选择哪一章",null);
        }
        if (unit.getId()==null||unit.getId().trim().isEmpty()){
            return new Result<>(false,"未选择哪一节",null);
        }

        int isSuccess;

        try{
            isSuccess=unitService.deleteUnit(unit);
        }catch(Exception e){
            return new Result<>(false,"删除失败",null);
        }

        if (isSuccess!=1){
            return new Result<>(false,"未知错误",null);
        }

        return new Result<>(true,"删除成功",null);
    }

    /**
         *
         * @Date 2017/12/17 19:20
         * @author students_ManagementSchool
         * @param map
         * @return
         * @since JDK 1.8
         * @condition 获取某章的所有小节
    */
    @ResponseBody
    @RequestMapping(value = "/getUnitsByChapter",method = RequestMethod.POST)
    /*public Result<List<Unit>> getUnitsByChapter(@RequestBody Unit unit){

        String chapter_id=unit.getChapter_id();
        if (chapter_id==null||chapter_id.trim().isEmpty()){
            return new Result<>(false,"传参错误",null);
        }

        List<Unit> units;

        try{
            units=unitService.getUnitsByChapter(chapter_id);
        }catch(Exception e){
            return new Result<>(false,"获取失败",null);
        }

        return new Result<>(true,"获取成功",units);
    }*/

    public Result<PageResult<Unit>> getUnitsByChapter(@RequestBody Map map){

        /*int startPage=(int)map.get("iDisplayStart");
        int pageSize=(int)map.get("iDisplayLength");
        String chapter_id=(String)map.get("chapter_id");*/

        PageResult<Unit> units;


        /*if (chapter_id==null||chapter_id.trim().isEmpty()){
            return new Result<>(false,"传参错误",null);
        }*/

        try{
            units=unitService.getUnitsByPage_chapter(map);
        }catch(Exception e){
            return new Result<>(false,"获取失败",null);
        }

        return new Result<>(true,"获取成功",units);
    }


    /**   
         * 
         * @Date 2018/1/7 12:37
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition 获取某一教学单元的所有信息
    */
    @ResponseBody
    @RequestMapping(value ="/queryUnitById",method = RequestMethod.POST)
    public Result<Map> queryUnitById(@RequestBody Unit unit){

        if (unit.getId()==null||unit.getId().trim().isEmpty()){
            return new Result<>(false,"未选择哪一节",null);
        }

        Map map;

        try{
            map=unitService.queryUnitById(unit);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"数据获取异常",null);
        }

        return new Result<>(true,"获取成功",map);
    }

}

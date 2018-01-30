package com.hfut.glxy.controller;

import com.hfut.glxy.dto.PageResult;
import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Chapter;
import com.hfut.glxy.service.ChapterService;
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
 * date: 2017/12/5 15:46 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Controller
//@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE}, allowedHeaders = "*")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    /**
         *
         * @Date 2017/12/5 16:36
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition 添加章
    */
    @ResponseBody
    @RequestMapping(value = "/addChapter",method = RequestMethod.POST)
    public Result<Chapter> addChapter(@RequestBody Chapter chapter){

        if (chapter.getCourse_id()==null||chapter.getCourse_id().trim().isEmpty()){
            return new Result<>(false,"课程不能为空",null);
        }

        /*if (result.hasErrors()){
            String message=result.getFieldError().getDefaultMessage();
            return new Result<>(false,""+message,null);
        }*/

        /*if (chapter.getNumber()==null||chapter.getNumber().trim().isEmpty()){
            return new Result<>(false,"章编号不能为空",null);
        }
        if (chapter.getName()==null||chapter.getName().trim().isEmpty()){
            return new Result<>(false,"章名称不能为空",null);
        }*/

        String chapter_id= UUID.randomUUID().toString().replaceAll("-","");
        chapter.setId(chapter_id);

        Chapter chapterR;

        try{
            chapterR=chapterService.addChapter(chapter);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"插入失败",null);
        }

        return new Result<>(true,"插入成功",chapterR);
    }

    /**
         *
         * @Date 2017/12/5 21:33
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition 更新章
    */
    @ResponseBody
    @RequestMapping(value = "/updateChapter",method = RequestMethod.POST)
    public Result<String> updateChapter(@RequestBody Chapter chapter){

        /*if (result.hasErrors()){
            String message=result.getFieldError().getDefaultMessage();
            return new Result<>(false,""+message,null);
        }*/

        if (chapter.getNumber()==null||chapter.getNumber().trim().isEmpty()){
            return new Result<>(false,"章编号不能为空",null);
        }
        if (chapter.getName()==null||chapter.getName().trim().isEmpty()){
            return new Result<>(false,"章名称不能为空",null);
        }

        if (chapter.getId()==null||chapter.getId().trim().isEmpty()){
            return new Result<>(false,"id不能为空",null);
        }

        int isSuccess;
        try {
            isSuccess = chapterService.updateChapter(chapter);
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
         * @Date 2017/12/6 10:37
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition  删除章
    */
    @ResponseBody
    @RequestMapping(value = "/deleteChapter",method = RequestMethod.POST)
    public Result<String> deleteChapter(@RequestBody Chapter chapter){

        int isSuccess;

        /*if (chapter.getCourse_id()==null){
            return new Result<>(false,"课程不能为空",null);
        }*/

        if (chapter.getId()==null){
            return new Result<>(false,"传参错误",null);
        }

        try{
            isSuccess=chapterService.deleteChapter(chapter);
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
         * @Date 2017/12/6 11:21
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取所有的章
    */
    @ResponseBody
    @RequestMapping(value = "/getAllChapters",method = RequestMethod.GET)
    public Result<List<Chapter>> getAllChapters(){

        List<Chapter> chapters;

        try{
            chapters=chapterService.getAllChapters();
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"获取失败",null);
        }

        return new Result<>(true,"获取成功",chapters);
    }

    /**
         *
         * @Date 2017/12/13 21:40
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition   根据id获取某章信息
    */
    @ResponseBody
    @RequestMapping(value = "/queryChapterById",method = RequestMethod.POST)
    public Result<Chapter> queryChapterById(@RequestBody Chapter chapter){

        Chapter chapterGet=null;
        try{
            chapterGet=chapterService.queryChapterById(chapter);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<>(false,"查询失败",null);
        }

        return new Result<>(true,"查询成功",chapterGet);
    }

    /**   
         * 
         * @Date 2018/1/6 18:15
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition 获取某课程的全部章
    */
    @ResponseBody
    @RequestMapping(value = "/getChaptersByCourse",method = RequestMethod.POST)
    public Result<List<Chapter>> getChaptersByCourse(@RequestBody Chapter chapter){

        String course_id=chapter.getCourse_id();

        if (course_id==null||course_id.trim().isEmpty()){
            return new Result<>(false,"课程id不能为空",null);
        }

        List<Chapter> chapters;

        try{
            chapters=chapterService.getChaptersByCourse(course_id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"数据获取异常",null);
        }

        return new Result<>(true,"获取成功",chapters);
    }

    /**
         *
         * @Date 2018/1/20 21:01
         * @author students_ManagementSchool
         * @param map
         * @return
         * @since JDK 1.8
         * @condition  分页获取章
    */
    @ResponseBody
    @RequestMapping(value = "/getChaptersByPage",method = RequestMethod.POST)
    public Result<PageResult<Chapter>> getChaptersByPage(@RequestBody Map map){

        String course_id=(String)map.get("course_id");
        if (course_id==null||course_id.trim().isEmpty()){
            return new Result<>(false,"未选择课程",null);
        }

        PageResult<Chapter> chapters;

        try{
            chapters=chapterService.getChaptersByPage(map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"获取失败",null);
        }

        return new Result<>(true,"获取成功",chapters);
    }
}

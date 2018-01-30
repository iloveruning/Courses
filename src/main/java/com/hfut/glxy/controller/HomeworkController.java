package com.hfut.glxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Homework;
import com.hfut.glxy.service.HomeworkService;
import com.hfut.glxy.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2017/11/29
 */
@RestController
@RequestMapping(value = "/homework")
//@CrossOrigin(origins = "*",methods = {RequestMethod.PUT,RequestMethod.POST,RequestMethod.GET,RequestMethod.OPTIONS,RequestMethod.DELETE})
public class HomeworkController {

    private HomeworkService homeworkService;

    @Autowired
    protected HomeworkController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    /**
     * 分页显示作业列表
     *
     * @param pageNum
     * @return
     */
    @GetMapping("/{pageNum}")
    public Result listHomeworks(@PathVariable(value = "pageNum", required = false) int pageNum,
                                HttpSession session) {
        String courseId = (String) session.getAttribute("courseId");
        //String courseId="111";
        if(pageNum<=0){
            pageNum=1;
        }
        try {
            PageInfo<Map<String,Object>> page=homeworkService.listHomeworkInfo(pageNum,courseId);
            return ResultUtil.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.selectError();
        }
    }

    /**
     * 单个删除作业
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result removeHomework(@PathVariable("id") Integer id) {
        try {
            if (id != null && id != 0) {
                Homework homework = new Homework();
                homework.setId(id);
                homework.setIsDelete(1);
                if (homeworkService.updateById(homework)) {
                    return ResultUtil.success("删除作业成功", null);
                }
            } else {
                return ResultUtil.fail("id为空或格式不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.deleteError();
    }


    /**
     * 批量删除作业
     *
     * @param map
     * @return
     */
    @DeleteMapping("/batch")
    public Result removeHomeworkes(@RequestBody Map<String, Object> map) {

        List<Integer> list = JSONObject.parseArray(map.get("ids").toString(), Integer.class);

        try {
            if (list != null && list.size() > 0) {
                List<Homework> homeworkList=new ArrayList<>(list.size());

                list.forEach(it->{
                    Homework homework=new Homework();
                    homework.setId(it);
                    homework.setIsDelete(1);
                    homeworkList.add(homework);
                });
                if (homeworkService.updateBatchById(homeworkList)) {
                    return ResultUtil.success("批量删除作业成功", null);
                }
            } else {
                return ResultUtil.dataError();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.deleteError();
    }

    /**
     * 保存作业
     *
     * @param homework
     * @param result
     * @return
     */
    @PostMapping
    public Result saveHomework(@RequestBody @Valid Homework homework,
                               BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return ResultUtil.fail(result.getAllErrors().toString());
        }
        try {
            String courseId = (String) session.getAttribute("courseId");
            //String courseId="rnt6576";
            homework.setCourseId(courseId);
            if (homeworkService.insert(homework)) {
                return ResultUtil.OK();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.insertError();

    }

    /**
     * 更新作业信息
     *
     * @param homework
     * @return
     */
    @PutMapping
    public Result updateHomework(@RequestBody Homework homework) {
        try {
            if (homeworkService.updateById(homework)) {
                return ResultUtil.OK();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.updateError();
    }
}

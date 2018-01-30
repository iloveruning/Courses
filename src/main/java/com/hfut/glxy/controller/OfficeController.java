package com.hfut.glxy.controller;

import com.hfut.glxy.dto.PageResult;
import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Office;
import com.hfut.glxy.entity.Unit;
import com.hfut.glxy.mapper.OfficeDao;
import com.hfut.glxy.mapper.Unit_OfficeDao;
import com.hfut.glxy.service.OfficeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018/1/19 12:32 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Controller
public class OfficeController {

    @Resource
    private OfficeService officeService;
    @Resource
    private OfficeDao officeDao;
    @Resource
    private Unit_OfficeDao unit_officeDao;

    /**   
         * 
         * @Date 2018/1/19 13:07 
         * @author students_ManagementSchool
         * @param map
         * @return
         * @since JDK 1.8
         * @condition  删除office文件
    */
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/deleteOfficeById",method = RequestMethod.POST)
    public Result<String> deleteOffice(@RequestBody Map map){

        String office_id=(String) map.get("office_id");
        String unit_id=(String) map.get("unit_id");

        System.out.println(unit_id);

        if (office_id==null||office_id.trim().isEmpty()){
            return new Result<>(false,"教学资料id不存在",null);
        }

        try{
            if (officeService.deleteById(Integer.parseInt(office_id))){

                if (unit_id==null){

                }else{
                    int deleteSuccess=unit_officeDao.deleteRelation(unit_id,office_id);
                    if (deleteSuccess!=1){
                        throw new RuntimeException("删除异常");
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(false,"删除失败",null);
        }
        return new Result<>(false,"删除成功",null);
    }

    /**
         *
         * @Date 2018/1/19 16:00
         * @author students_ManagementSchool
         * @param map
         * @return
         * @since JDK 1.8
         * @condition
    */
    @ResponseBody
    @RequestMapping(value = "/getOfficesByUnit",method = RequestMethod.POST)
    public Result<PageResult<Office>> getOfficesByUnit(@RequestBody Map map){

        String unit_id=(String)map.get("unit_id");

        if (unit_id==null||unit_id.trim().isEmpty()){
            return new Result<>(false,"没有选中教学单元",null);
        }

        PageResult<Office> officePageResult=new PageResult<>();

        int startPage=(int)map.get("iDisplayStart");
        int pageSize=(int)map.get("iDisplayLength");

        //获取总数
        int totalCount=unit_officeDao.getOfficeCountByUnit(unit_id);
        if (totalCount==0){
            return null;
        }

        officePageResult.setiTotalRecords(totalCount);
        officePageResult.setiTotalDisplayRecords(totalCount);

        List<Office> offices=new ArrayList<>();
        String [] office_ids=unit_officeDao.getOfficesByUnit(unit_id,startPage,pageSize);
        for (String office_id:office_ids){
            Office office=officeService.selectById(office_id);
            if (office==null){
                return new Result<>(false," gg  gg",null);
            }
            offices.add(office);
        }

        officePageResult.setData(offices);
        return new Result<>(true,"获取成功",officePageResult);
    }

}

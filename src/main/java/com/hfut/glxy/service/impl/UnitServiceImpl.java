package com.hfut.glxy.service.impl;

import com.hfut.glxy.dto.PageResult;
import com.hfut.glxy.entity.*;
import com.hfut.glxy.mapper.*;
import com.hfut.glxy.service.KnowledgePointService;
import com.hfut.glxy.service.UnitService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/13 22:19 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Service
@Component(value = "unitService")
public class UnitServiceImpl implements UnitService {

    @Resource
    private UnitDao unitDao;
    @Resource
    private ChapterDao chapterDao;
    @Resource
    private Chapter_UnitDao chapter_unitDao;
    @Resource
    private Unit_KnowledgePointDao unit_knowledgePointDao;
    @Resource
    private Unit_OfficeDao unit_officeDao;
    @Resource
    private OfficeDao officeDao;
    @Resource
    private Unit_VideoDao unit_videoDao;
    @Resource
    private VideoDao videoDao;
    @Resource
    private KnowledgePointService knowledgePointService;

    /**
         *
         * @Date 2017/12/17 17:11
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition  添加小节
    */
    @Override
    @Transactional
    public int addUnit(Unit unit) throws Exception{

        String chapter_id=unit.getChapter_id();
        String unit_id=unit.getId();

        //检验所属章是否存在
        Chapter chapter=chapterDao.queryChapterById(chapter_id);
        if (chapter==null){
            return 0;
        }

        int addSuccess;
        int addChapter_unitSuccess;

        addSuccess=unitDao.addUnit(unit);
        addChapter_unitSuccess=chapter_unitDao.addRelation(chapter_id,unit_id);


        if (addSuccess!=1||addChapter_unitSuccess!=1){
            //添加失败，进行回滚
            throw new RuntimeException("添加失败");
        }

        return 1;
    }

    /**
         *
         * @Date 2017/12/17 18:31
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition 更新小节
    */
    @Override
    public int updateUnit(Unit unit) throws Exception{

        int updateSuccess;

        updateSuccess=unitDao.updateUnit(unit);

        if (updateSuccess!=1){
            //更新失败，进行回滚
            throw new RuntimeException("更新失败");
        }

        return updateSuccess;
    }

    /**
         *
         * @Date 2017/12/17 18:46
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition   删除小节
    */
    @Override
    public int deleteUnit(Unit unit) throws Exception{

        String chapter_id=unit.getChapter_id();
        String unit_id=unit.getId();

        int deleteUnitSuccess;
        int deleteChapter_unitSuccess;

        deleteUnitSuccess=unitDao.putToDustbin(unit_id);
        deleteChapter_unitSuccess=chapter_unitDao.deleteRelation(chapter_id,unit_id);

        if (deleteUnitSuccess!=1||deleteChapter_unitSuccess!=1){
            //删除失败,进行回滚
            throw new RuntimeException("删除失败");
        }

        return 1;
    }

    /**
         *
         * @Date 2017/12/17 19:10
         * @author students_ManagementSchool
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition  获取某章的全部小节
    */
    @Override
    public List<Unit> getUnitsByChapter(String chapter_id) throws Exception{

        List<Unit> units=new ArrayList<>();
        Unit unitGet;

        String [] unit_ids;
        unit_ids=chapter_unitDao.getUnitsByChapter(chapter_id);

        if (unit_ids==null){
            return null;
        }

        for (String unit_id:unit_ids){
            unitGet=unitDao.queryUnitById(unit_id);
            units.add(unitGet);
        }

        return units;
    }

    /**
         *
         * @Date 2018/1/19 0:23
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition 获取某教学单元的全部信息
    */
    @Override
    public Map queryUnitById(Unit unit) throws Exception{

        Map map=new HashMap();

        //获取教学单元
        String unit_id=unit.getId();
        Unit unitG=unitDao.queryUnitById(unit_id);
        map.put("unit",unitG);

        //获取知识点
        List<KnowledgePoint> knowledgePoints=knowledgePointService.getKnowledgePointsByUnit(unit_id);
        map.put("knowledgePoints",knowledgePoints);

        //获取office文件
        /*List<Office> offices=new ArrayList<>();
        String [] office_ids=unit_officeDao.getOfficesByUnit(unit_id);
        if (office_ids==null){

        }else{

            for (String office_id:office_ids){

                Office office=officeDao.queryOfficeById(office_id);
                offices.add(office);

            }
        }
        map.put("offices",offices);*/

        //获取视频文件
        List<Video> videos=new ArrayList<>();
        String [] video_ids=unit_videoDao.getVideosByUnit(unit_id);
        if (video_ids==null){

        }else{

            for (String video_id:video_ids){
                Video video=videoDao.queryVideoById(video_id);
                videos.add(video);
            }

        }
        map.put("videos",videos);

        return map;
    }


    /**   
         * 
         * @Date 2018/1/19 0:51
         * @author students_ManagementSchool
         * @param map
         * @return
         * @since JDK 1.8
         * @condition  分页查询某页的教学单元
    */
    @Override
    public PageResult<Unit> getUnitsByPage_chapter(Map map) throws Exception{

        int startPage=(int)map.get("iDisplayStart");
        int pageSize=(int)map.get("iDisplayLength");
        String chapter_id=(String)map.get("chapter_id");

        PageResult<Unit> unitPageResult=new PageResult<>();

        //获取总数
        int totalCount=chapter_unitDao.getUnitCountByChapter(chapter_id);
        if (totalCount==0){
            return null;
        }

        unitPageResult.setiTotalRecords(totalCount);
        unitPageResult.setiTotalDisplayRecords(totalCount);

        //获取教学单元
        List<Unit> units=new ArrayList<>();
        String [] unit_ids;
        unit_ids=chapter_unitDao.getUnitsByPage_chapter(chapter_id,startPage,pageSize);
        for (String unit_id:unit_ids){
            Unit unit=unitDao.queryUnitById(unit_id);
            if (unit==null){
                throw new RuntimeException("gg gg gg gg");
            }
            units.add(unit);
        }

        unitPageResult.setData(units);

        return unitPageResult;
    }

}

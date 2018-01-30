package com.hfut.glxy.service.impl;

import com.hfut.glxy.entity.Chapter;
import com.hfut.glxy.entity.Course;
import com.hfut.glxy.entity.KnowledgePoint;
import com.hfut.glxy.entity.Unit;
import com.hfut.glxy.mapper.*;
import com.hfut.glxy.service.KnowledgePointService;
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
 * date: 2017/12/28 21:01 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Service
@Component(value = "knowledgePointService")
public class KnowledgePointServiceImpl implements KnowledgePointService {

    @Resource
    private KnowledgePointDao knowledgePointDao;

    @Resource
    private Unit_KnowledgePointDao unit_knowledgePointDao;

    @Resource
    private Chapter_UnitDao chapter_unitDao;

    @Resource
    private UnitDao unitDao;

    @Resource
    private ChapterDao chapterDao;

    @Resource
    private CourseDao courseDao;

    @Resource
    private Course_ChapterDao course_chapterDao;

    /**   
         * 
         * @Date 2018/1/5 20:55
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  增加知识点以及与课时的关系
    */
    @Override
    @Transactional
    public KnowledgePoint addKnowledgePoint(KnowledgePoint knowledgePoint) throws Exception{

        /*String unit_id=knowledgePoint.getUnit_id();*/
        String knowledgePoint_id=knowledgePoint.getId();

        int addKnowledgePointSuccess;
        addKnowledgePointSuccess=knowledgePointDao.addKnowledgePoint(knowledgePoint);
        if (addKnowledgePointSuccess!=1){
            throw new RuntimeException("增加知识点异常");
        }

        /*int addUnit_KnowledgePointSuccess;
        addUnit_KnowledgePointSuccess=unit_knowledgePointDao.addRelation(unit_id,knowledgePoint_id);
        if (addUnit_KnowledgePointSuccess!=1){
            throw new RuntimeException("增加课时与知识点关联异常");
        }*/

        KnowledgePoint knowledgePointG;
        knowledgePointG=knowledgePointDao.queryKnowledgePointById(knowledgePoint_id);

        return knowledgePointG;
    }

    /**   
         * 
         * @Date 2018/1/5 21:20
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  更新知识点
    */
    @Override
    public Integer updateKnowledgePoint(KnowledgePoint knowledgePoint) throws Exception{

        int updateSuccess;

        updateSuccess=knowledgePointDao.updateKnowledgePoint(knowledgePoint);

        return updateSuccess;
    }

    /**   
         * 
         * @Date 2018/1/5 21:50
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  删除某一知识点及其相关教学单元的关联
    */
    @Override
    @Transactional
    public Integer deleteKnowledgePoint(KnowledgePoint knowledgePoint) throws Exception{

        String knowledgePoint_id=knowledgePoint.getId();

        int deleteKnowledgePointSuccess;
        deleteKnowledgePointSuccess=knowledgePointDao.putToDustbin(knowledgePoint_id);
        if (deleteKnowledgePointSuccess!=1){
            throw new RuntimeException("删除知识点异常");
        }

        int deleteUnit_KnowledgePointSuccess=-1;
        deleteUnit_KnowledgePointSuccess=unit_knowledgePointDao.deleteRelationByKnowledgePoint(knowledgePoint_id);
        if (deleteUnit_KnowledgePointSuccess==-1){
            throw new RuntimeException("删除知识点教学单元关联异常");
        }

        return 1;
    }

    /**
     *
     * @Date 2018/1/6 15:23
     * @author students_ManagementSchool
     * @param knowledgePoint
     * @return
     * @since JDK 1.8
     * @condition  增加知识点与教学单元的关联
     */
    @Override
    @Transactional
    public Integer addRelation(KnowledgePoint knowledgePoint) throws Exception{

        String unit_id=knowledgePoint.getUnit_id();
        String knowledgePoint_id=knowledgePoint.getId();

        Integer ifRelationExists=unit_knowledgePointDao.ifRelationExists(unit_id,knowledgePoint_id);
        if (ifRelationExists==null){

            int addRelationSuccess;
            addRelationSuccess=unit_knowledgePointDao.addRelation(unit_id,knowledgePoint_id);
            if (addRelationSuccess!=1){
                throw new RuntimeException("增加关系异常");
            }

        }else if(ifRelationExists==1){

            int recoverRelationSuccess;
            recoverRelationSuccess=unit_knowledgePointDao.recoverRelation(unit_id,knowledgePoint_id);
            if (recoverRelationSuccess!=1){
                throw new RuntimeException("恢复关系异常");
            }

        }else{
            return 0;
        }

        return 1;
    }

    /**
         *
         * @Date 2018/1/5 22:26
         * @author students_ManagementSchool
         * @param  knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  删除知识点教学单元关联异常
    */
    @Override
    public Integer releaseRelation(KnowledgePoint knowledgePoint) throws Exception{

        String unit_id=knowledgePoint.getUnit_id();
        String knowledgePoint_id=knowledgePoint.getId();

        int deleteRelationSuccess;
        deleteRelationSuccess=unit_knowledgePointDao.deleteRelation(unit_id,knowledgePoint_id);

        return deleteRelationSuccess;
    }


    /**
         *
         * @Date 2018/1/6 10:23
         * @author students_ManagementSchool
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition  获取与某教学单元相关的所有知识点
    */
    @Override
    public List<KnowledgePoint> getKnowledgePointsByUnit(String unit_id) throws Exception{

        List<KnowledgePoint> knowledgePoints=new ArrayList<>();
        KnowledgePoint knowledgePoint;

        String [] knowledgePoint_ids;
        knowledgePoint_ids=unit_knowledgePointDao.getKnowledgePointsByUnit(unit_id);

        if (knowledgePoint_ids==null){
            return null;
        }

        for (String knowledgePoint_id:knowledgePoint_ids){
            knowledgePoint=knowledgePointDao.queryKnowledgePointById(knowledgePoint_id);
            knowledgePoints.add(knowledgePoint);
        }

        return knowledgePoints;
    }

    /**   
         * 
         * @Date 2018/1/6 10:36
         * @author students_ManagementSchool
         * @param chapter_id
         * @return
         * @since JDK 1.8
         * @condition  获取与某章相关的所有知识点
    */
    @Override
    public List<KnowledgePoint> getKnowledgePointsByChapter(String chapter_id) throws Exception{

        List<KnowledgePoint> knowledgePoints=new ArrayList<>();
        KnowledgePoint knowledgePoint;

        String [] knowledgePoint_ids;
        String [] unit_ids;

        unit_ids=chapter_unitDao.getUnitsByChapter(chapter_id);
        if (unit_ids==null){
            return null;
        }

        for (String unit_id:unit_ids){

            knowledgePoint_ids=unit_knowledgePointDao.getKnowledgePointsByUnit(unit_id);

            if (knowledgePoint_ids==null){
                continue;
            }

            for (String knowledgePoint_id:knowledgePoint_ids){
                knowledgePoint=knowledgePointDao.queryKnowledgePointById(knowledgePoint_id);
                knowledgePoints.add(knowledgePoint);
            }

        }

        return knowledgePoints;
    }

    /**   
         * 
         * @Date 2018/1/6 15:51
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition  获取与某课程相关的所有知识点
    */
    @Override
    public List<KnowledgePoint> getKnowledgePointsByCourse(String course_id) throws Exception{

        List<KnowledgePoint> knowledgePoints=new ArrayList<>();

        String [] chapter_ids;

        chapter_ids=course_chapterDao.getChaptersByCourse(course_id);
        if (chapter_ids==null) {
            return null;
        }

        for (String chapter_id:chapter_ids) {
            try {

                List<KnowledgePoint> knowledgePoints_C = getKnowledgePointsByChapter(chapter_id);
                if (knowledgePoints_C.size()==0) {
                    continue;
                }

                for(KnowledgePoint knowledgePoint1:knowledgePoints_C) {

                    if (knowledgePoints.size()==0) {
                        knowledgePoints.add(knowledgePoint1);
                    }else{

                        boolean checkPass=false;
                        for (KnowledgePoint knowledgePoint2:knowledgePoints){

                            if(knowledgePoint1.getId().equals(knowledgePoint2.getId())){
                                break;
                            }

                            checkPass=true;
                        }

                        if (checkPass){
                            knowledgePoints.add(knowledgePoint1);
                        }

                    }

                }

            }catch(Exception e) {
                e.printStackTrace();
                throw new RuntimeException("数据获取异常");
            }

        }

        return knowledgePoints;
    }

    /**   
         * 
         * @Date 2018/1/6 11:56
         * @author students_ManagementSchool
         * @param knowledgePoint_id
         * @return
         * @since JDK 1.8
         * @condition  获取与某一知识点相关的所有教学单元(模糊查询)
    */
    @Override
    public List<Map> getUnitsByKnowledgePoint(String knowledgePoint_id) throws Exception{

        //获取该知识点的内容
        KnowledgePoint knowledgePoint=knowledgePointDao.queryKnowledgePointById(knowledgePoint_id);
        if (knowledgePoint==null){
            return null;
        }

       /* System.out.println("-----------------------------------------------------------------------");*/

        String knowledgePoint_content=knowledgePoint.getContent();

        ArrayList<String> contents=new ArrayList<>();

        //判断该知识点内容长度
        int contentLength=knowledgePoint_content.length();

        switch (contentLength){

            case 1:
                contents.add(knowledgePoint_content);
                break;
            case 2:
                contents.add(knowledgePoint_content);
                break;
            case 3:
                contents.add(knowledgePoint_content);
                break;
            case 4:
                String content41=knowledgePoint_content.substring(0,2);
                String content42=knowledgePoint_content.substring(2,4);
                contents.add(content41);
                contents.add(content42);
                break;
            case 5:
                String content51=knowledgePoint_content.substring(0,2);
                String content52=knowledgePoint_content.substring(2,5);
                String content53=knowledgePoint_content.substring(0,3);
                String content54=knowledgePoint_content.substring(3,5);
                contents.add(content51);
                contents.add(content52);
                contents.add(content53);
                contents.add(content54);
                break;
            case 6:
                String content61=knowledgePoint_content.substring(0,2);
                String content62=knowledgePoint_content.substring(2,4);
                String content63=knowledgePoint_content.substring(4,6);
                String content64=knowledgePoint_content.substring(0,3);
                String content65=knowledgePoint_content.substring(3,3);
                contents.add(content61);
                contents.add(content62);
                contents.add(content63);
                contents.add(content64);
                contents.add(content65);
                break;
        }

        /*System.out.println(contents.toString());*/

        List<Unit> units=new ArrayList<>();
        Unit unit;

        String [] knowledgePoint_ids;//名字相同或相近的知识点集合
        String [] unit_ids; //某一知识点对应的教学单元的集合

        knowledgePoint_ids=knowledgePointDao.getSimilarKnowledgePoints(contents);
        if (knowledgePoint_ids==null){
            throw new RuntimeException("模糊查询出现异常");
        }

        //对每一个知识点进行教学单元的获取
        for (String knowledgePointId:knowledgePoint_ids){

            unit_ids=unit_knowledgePointDao.getUnitsByKnowledgePoint(knowledgePointId);
            if (unit_ids==null){
                continue;
            }

            //根据教学单元id获取教学单元
            for (String unit_id:unit_ids){

                boolean checkPass=false;

                if (units.size()==0){
                    unit=unitDao.queryUnitById(unit_id);
                    units.add(unit);
                }else{

                    //检查该教学单元是否已经获取到
                    for (Unit checkUnit:units){
                        if (unit_id.equals(checkUnit.getId())){
                            break;
                        }
                        checkPass=true;
                    }

                    /*System.out.println("units.length: "+units.size());*/

                    if (checkPass){
                        unit=unitDao.queryUnitById(unit_id);
                        units.add(unit);
                    }

                }


            }
            
        }

        List<Map> maps=new ArrayList<>();
        for (Unit unitI:units){

            String chapter_id=chapter_unitDao.getChapterByUnit(unitI.getId());
            Chapter chapter=chapterDao.queryChapterById(chapter_id);
            if (chapter==null){
                throw new RuntimeException("章不存在");
            }

            String course_id=course_chapterDao.getCurrentCourseByChapter(chapter_id);
            Course course=courseDao.queryCourseById(course_id);
            if (course==null){
                throw new RuntimeException("课程不存在");
            }

            Map map=new HashMap();
            map.put("course",course);
            map.put("chapter",chapter);
            map.put("unit",unitI);

            maps.add(map);
        }

        return maps;
    }

    @Override
    public String [] test(String test_id) throws Exception{

        String [] ids=knowledgePointDao.test();

        return ids;

    }




 /*   @Override
    public List<KnowledgePoint> getKnowledgePointsByCourse(String course_id) throws Exception{*/



    /*}*/

}


package com.hfut.glxy.service.impl;

/*import com.hfut.glxy.dao.*;*/
import com.hfut.glxy.entity.CourseGroup;
import com.hfut.glxy.entity.Picture;
import com.hfut.glxy.mapper.*;
import com.hfut.glxy.service.CourseGroupService;
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
 * date: 2017/11/26 9:58 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Service
public class CourseGroupServiceImpl implements CourseGroupService {

    @Resource
    private CourseGroupDao courseGroupDao;
    @Resource
    private PictureDao pictureDao;

    @Resource
    private CourseGroup_TeacherDao courseGroup_teacherDao;
    @Resource
    private CourseGroup_CourseDao courseGroup_courseDao;
    @Resource
    private CourseGroup_PictureDao courseGroup_pictureDao;

    /*
         *
         * @Date 2017/11/26 10:03
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition  增加课程组
    */
    /*@Override
    public int addCourseGroup(CourseGroup courseGroup) throws Exception{
        int isSuccess=courseGroupDao.addCourseGroup(courseGroup);
        return isSuccess;
    }*/

    @Override
    @Transactional
    public int addCourseGroup(CourseGroup courseGroup) {

        int addCourseGroupSuccess;
        int addCourseGroup_pictureSuccess;

        String picture_id=courseGroup.getPicture_id();

        //向课程组表中添加
        addCourseGroupSuccess = courseGroupDao.addCourseGroup(courseGroup);

        if (addCourseGroupSuccess==0){
            //进行回滚
            throw new RuntimeException("添加失败");
        }

        if (picture_id==null) {
            return addCourseGroupSuccess;
        }

        //判断上传的图片是否真的存在，若不存在，说明数据伪造，拒绝请求
        Picture picture=pictureDao.queryPictureById(picture_id);
        if (picture==null){
            //进行回滚
            throw new RuntimeException("添加失败");
        }

        //向课程组表-图片中添加
        addCourseGroup_pictureSuccess = courseGroup_pictureDao.addCourseGroup_picture
                (courseGroup.getId(), picture_id);


        if (addCourseGroup_pictureSuccess == 0) {
            //进行回滚
            throw new RuntimeException("添加失败");
        }

        return 1;
    }

    /*
         *
         * @Date 2017/11/26 11:07
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition  更新课程组
    */
    /*@Override
    public int updateCourseGroup(CourseGroup courseGroup) throws Exception{
        int isSuccess=courseGroupDao.updateCourseGroup(courseGroup);
        return isSuccess;
    }*/
    /**
         *
         * @Date 2017/11/27 18:56
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition
    */
    @Override
    @Transactional
    public int updateCourseGroup(CourseGroup courseGroup) throws Exception {

        int updateCourseGroupSuccess;
        int deleteRelationSuccess;
        int updateRelationSuccess;
        int addRelationSuccess;

        String picture_id=courseGroup.getPicture_id();

        //更新课程组表
        updateCourseGroupSuccess = courseGroupDao.updateCourseGroup(courseGroup);

        if (updateCourseGroupSuccess==0){
            //进行回滚
            throw new RuntimeException("更新失败");
        }

        //如果没有上传图片
        if (picture_id == null) {
            return updateCourseGroupSuccess;
        }

        //判断上传的图片是否真的存在，若不存在，说明数据伪造，拒绝请求
        Picture picture=pictureDao.queryPictureById(picture_id);
        if (picture==null){
            //进行回滚
            throw new RuntimeException("更新失败");
        }

        String courseGroup_id=courseGroup.getId();
        String currentPictureId=courseGroup_pictureDao.getCurrentPictureIdByCourseGroup(courseGroup_id);

        //如果课程组没有初始图片
        if (currentPictureId==null){
            addRelationSuccess=courseGroup_pictureDao.addCourseGroup_picture(courseGroup_id,picture_id);
            if(addRelationSuccess==0){
                //进行回滚
                throw new RuntimeException("更新失败");
            }
            return 1;
        }

        //如果图片没有更新
        if (currentPictureId.equals(picture_id)){
            return updateCourseGroupSuccess;
        }

        //如果图片有更新
        deleteRelationSuccess=courseGroup_pictureDao.deleteRelationByCourseGroup_Teacher(courseGroup_id,currentPictureId);
        updateRelationSuccess=courseGroup_pictureDao.addCourseGroup_picture(courseGroup_id,picture_id);

        if (deleteRelationSuccess==0||updateRelationSuccess==0){
            //进行回滚
            throw new RuntimeException("更新失败");
        }

        return 1;
    }

    /*
     * @param courseGroup_id
     * @return
     * @Date 2017/11/26 11:22
     * @author students_ManagementSchool
     * @condition 将课程组放入回收站
     * @since JDK 1.8
     */
    @Override
    @Transactional
    public int deleteCourseGroup(String courseGroup_id) throws Exception {

        /*int deleteCourseGroupSuccess=0;
        int deleteCourseGroup_courseSuccess=0;
        int deleteCourseGroup_teacherSuccess=0;
        int deleteCourseGroup_pictureSuccess=0;*/

        //从课程组表中删除
        /*deleteCourseGroupSuccess = courseGroupDao.putToDustbin(courseGroup_id);*/

        /*if (deleteCourseGroupSuccess==0){
            //进行回滚
            throw new RuntimeException("删除失败");
        }*/

        //从课程组—课程表里删除
        /*deleteCourseGroup_courseSuccess = courseGroup_courseDao.deleteRelationByCourseGroup(courseGroup_id);

        //从课程组-教师表中删除
        deleteCourseGroup_teacherSuccess = courseGroup_teacherDao.deleteRelationByCourseGroup(courseGroup_id);

        //从课程组-图片表删除
        deleteCourseGroup_pictureSuccess = courseGroup_pictureDao.deleteRelationByCourseGroup(courseGroup_id);*/

        try{
            courseGroupDao.putToDustbin(courseGroup_id);
            courseGroup_courseDao.deleteRelationByCourseGroup(courseGroup_id);
            courseGroup_teacherDao.deleteRelationByCourseGroup(courseGroup_id);
            courseGroup_pictureDao.deleteRelationByCourseGroup(courseGroup_id);
        }catch(RuntimeException e){
            //进行回滚
            throw new RuntimeException("删除失败");
        }

        return 1;
    }

    /*
     * @return
     * @Date 2017/11/26 11:51
     * @author students_ManagementSchool
     * @condition 获取全部课程组
     * @since JDK 1.8
     */
    /*@Override
    public List<CourseGroup> getAllCourseGroups() throws Exception {
        List<CourseGroup> courseGroups;
        courseGroups = courseGroupDao.getAllCourseGroups();
        return courseGroups;
    }*/

    @Override
    @Transactional
    public List<Map> getAllCourseGroups() throws Exception{

        List<Map> maps=new ArrayList<>();
        Map map;
        /*CourseGroup courseGroup;*/
        Picture picture;
        List<CourseGroup> courseGroups;

        courseGroups=courseGroupDao.getAllCourseGroups();

        if (courseGroups.isEmpty()){
            return null;
        }

        for (CourseGroup courseGroup:courseGroups){
            String picture_id=courseGroup_pictureDao.getCurrentPictureIdByCourseGroup(courseGroup.getId());
            picture=pictureDao.queryPictureById(picture_id);
            map=new HashMap();
            map.put("courseGroup",courseGroup);
            map.put("picture",picture);
            maps.add(map);
        }

        return maps;
    }

    /*
     * @param courseGroup_id
     * @return
     * @Date 2017/11/26 11:52
     * @author students_ManagementSchool
     * @condition 根据id获取课程组
     * @since JDK 1.8
     */
    /*@Override
    public CourseGroup queryCourseGroupById(String courseGroup_id) throws Exception {
        CourseGroup courseGroup;
        courseGroup = courseGroupDao.queryCourseGroupById(courseGroup_id);
        return courseGroup;
    }*/

    @Override
    @Transactional
    /**   
         * 
         * @Date 2017/11/27 20:27
         * @author students_ManagementSchool
         * @param courseGroup_id
         * @return
         * @since JDK 1.8
         * @condition  
    */
    public Map queryCourseGroupById(String courseGroup_id) throws Exception{

        Map map=new HashMap();
        Picture picture;
        CourseGroup courseGroup;

        String picture_id=courseGroup_pictureDao.getCurrentPictureIdByCourseGroup(courseGroup_id);
        courseGroup=courseGroupDao.queryCourseGroupById(courseGroup_id);
        picture=pictureDao.queryPictureById(picture_id);

        map.put("courseGroup",courseGroup);
        map.put("picture",picture);

        return map;
    }




}

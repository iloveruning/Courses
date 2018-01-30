package com.hfut.glxy.service.impl;

import com.hfut.glxy.dto.PageResult;
import com.hfut.glxy.entity.Chapter;
import com.hfut.glxy.entity.Course;
import com.hfut.glxy.mapper.*;
import com.hfut.glxy.mapper.Course_ChapterDao;
import com.hfut.glxy.service.ChapterService;
import org.apache.tomcat.util.buf.CharChunk;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/12/5 15:56 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Service
public class ChapterServiceImpl implements ChapterService {

    @Resource
    private ChapterDao chapterDao;
    @Resource
    private CourseDao courseDao;
    @Resource
    private Course_ChapterDao course_chapterDao;

    /**
         *
         * @Date 2017/12/5 16:50
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition  添加章
    */
    @Override
    @Transactional
    public Chapter addChapter(Chapter chapter) throws Exception{

        //防止数据伪造以及检查课程号是否为空
        String course_id=chapter.getCourse_id();
        Course course= courseDao.queryCourseById(course_id);
        if (course==null){
            return null;
        }

        int addChapterSuccess;
        int addCourse_chapterSuccess;

        addChapterSuccess=chapterDao.addChapter(chapter);
        addCourse_chapterSuccess=course_chapterDao.addRelation(course_id,chapter.getId());

        if (addChapterSuccess!=1||addCourse_chapterSuccess!=1){
            throw new RuntimeException("添加失败");
        }

        String chapter_id=chapter.getId();
        Chapter chapterG=chapterDao.queryChapterById(chapter_id);

        return chapter;
    }

    /**
         *
         * @Date 2017/12/5 21:10
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition  更新章
    */
    @Override
    @Transactional
    public int updateChapter(Chapter chapter) throws Exception{

        //防止数据伪造以及检查课程号是否为空
        /*String course_id=chapter.getCourse_id();
        Course course= courseDao.queryCourseById(course_id);
        if (course==null){
            return 0;
        }*/

        int updateChapterSuccess;
        updateChapterSuccess=chapterDao.updateChapter(chapter);

        if (updateChapterSuccess!=1){
            throw new RuntimeException("更新失败");
        }

        return 1;
    }

    /**
         *
         * @Date 2017/12/6 10:19
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition  删除本章
    */
    @Override
    @Transactional
    public int deleteChapter(Chapter chapter) throws Exception{

        /*String course_id=chapter.getCourse_id();*/
        String chapter_id=chapter.getId();

        /*String currentCourseId=course_chapterDao.getCurrentCourseByChapter(chapter_id);
        if (!course_id.equals(currentCourseId)){
            return 0;
        }*/

        int deleteChapterSuccess;
        int deleteRelationSuccess;

        deleteChapterSuccess=chapterDao.putToDustbin(chapter_id);
        deleteRelationSuccess=course_chapterDao.deleteRelation(chapter_id);

        if (deleteChapterSuccess!=1||deleteRelationSuccess!=1){
            throw new RuntimeException("删除失败");
        }

        return 1;
    }

    /**
         *
         * @Date 2017/12/6 11:25
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取所有的章
    */
    @Override
    public List<Chapter> getAllChapters() throws Exception{

        List<Chapter> chapters;

        chapters=chapterDao.getAllChapters();

        return chapters;
    }

    /**
         *
         * @Date 2017/12/13 21:52
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition 根据id获取章
    */
    @Override
    public Chapter queryChapterById(Chapter chapter) throws Exception{

        /*String course_id=chapter.getCourse_id();*/
        String chapter_id=chapter.getId();

       /* String currentCourseId=course_chapterDao.getCurrentCourseByChapter(chapter_id);
        if (!course_id.equals(currentCourseId)){
            return null;
        }*/

        Chapter chapterGet=chapterDao.queryChapterById(chapter_id);
        if (chapter==null){
            throw new RuntimeException("未知错误！");
        }

        return chapterGet;
    }

    /**
         *
         * @Date 2018/1/6 18:05
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition 获取某一课程的全部章节
    */
    @Override
    public List<Chapter> getChaptersByCourse(String course_id) throws Exception{

        List<Chapter> chapters=new ArrayList<>();
        Chapter chapter;

        String[] chapter_ids;
        chapter_ids=course_chapterDao.getChaptersByCourse(course_id);

        if (chapter_ids==null){
            return null;
        }

        for (String chapter_id:chapter_ids){
            chapter=chapterDao.queryChapterById(chapter_id);
            chapters.add(chapter);
        }

        return chapters;
    }

    /**
         *
         * @Date 2018/1/20 20:58
         * @author students_ManagementSchool
         * @param map
         * @return
         * @since JDK 1.8
         * @condition 分页获取章
    */
    @Override
    public PageResult<Chapter> getChaptersByPage(Map map) throws Exception{

        int startPage=(int)map.get("iDisplayStart");
        int pageSize=(int)map.get("iDisplayLength");
        String course_id=(String)map.get("course_id");

        PageResult<Chapter> chapterPageResult=new PageResult<>();

        Integer totalCount=course_chapterDao.getCountByCourse(course_id);
        chapterPageResult.setiTotalDisplayRecords(totalCount);
        chapterPageResult.setiTotalRecords(totalCount);

        List<Chapter> chapters=new ArrayList<>();
        String [] chapter_ids=course_chapterDao.getChaptersByPage(course_id,startPage,pageSize);
        if (chapter_ids==null){
            return null;
        }
        for (String chapter_id:chapter_ids){
            Chapter chapter=chapterDao.queryChapterById(chapter_id);
            if (chapter==null){
                throw new RuntimeException("章不存在");
            }
            chapters.add(chapter);
        }

        chapterPageResult.setData(chapters);

        return chapterPageResult;
    }


}

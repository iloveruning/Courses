package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chenliangliang
 * @date 2018/1/4
 */
@Component
public interface CourseMapper {

    Map<String,Object> findCourseAndGroupInfoById(@Param("cid") String cid);

    String findCourseImgById(@Param("cid") String cid);

    Map<String,Object> findCourseAndChapterInfoByChapterId(@Param("chid") String chid);
}

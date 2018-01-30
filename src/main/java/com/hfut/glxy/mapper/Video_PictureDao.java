package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018/1/13 15:56 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "video_pictureDao")
public interface Video_PictureDao {

    /**   
         * 
         * @Date 2018/1/13 15:57
         * @author students_ManagementSchool
         * @param video_id
         * @return
         * @since JDK 1.8
         * @condition  获取视频的缩略图
    */
    @Select("select picture_id from video_picture where video_id=#{video_id} and isDelete=0")
    String getPictureByVideo(@Param("video_id") String video_id);

}

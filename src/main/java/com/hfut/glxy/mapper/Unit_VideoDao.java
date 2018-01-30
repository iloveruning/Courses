package com.hfut.glxy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018/1/7 12:11 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "/unit_videoDao")
public interface Unit_VideoDao {

    /**
         *
         * @Date 2018/1/7 12:15
         * @author students_ManagementSchool
         * @param unit_id
         * @return
         * @since JDK 1.8
         * @condition   获取教学单元的全部视频文件
    */
    @Select("select video_id from unit_video where unit_id=#{unit_id} and isDelete=0")
    String [] getVideosByUnit(@Param("unit_id") String unit_id);

}

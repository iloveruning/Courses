package com.hfut.glxy.mapper;

import com.hfut.glxy.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018/1/7 12:17 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "videoDao")
public interface VideoDao {

    /**
         *
         * @Date 2018/1/7 12:18
         * @author students_ManagementSchool
         * @param id
         * @return
         * @since JDK 1.8
         * @condition 根据id获取视频
    */
    @Select("select * from video where id=#{id} and isDelete=0")
    Video queryVideoById(@Param("id") String id);

}

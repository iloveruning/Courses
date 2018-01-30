package com.hfut.glxy.mapper;

import com.hfut.glxy.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/27 20:17 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Mapper
@Component(value = "pictureDao")
public interface PictureDao {

    /**
         *
         * @Date 2017/11/27 20:18
         * @author students_ManagementSchool
         * @param picture_id
         * @return
         * @since JDK 1.8
         * @condition  根据图片id查找图片
    */
    @Select("select * from picture where id=#{picture_id}")
    Picture queryPictureById(@Param("picture_id") String picture_id);

}

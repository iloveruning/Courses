/*
 * ProjectName: courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年11月21日 <br/>
 *
 * @author students_ManagementSchool
 * @version
 * @since JDK 1.8
 */

package com.hfut.glxy.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenliangliang
 * @date 2017/12/19
 */
@Data
@NoArgsConstructor
public class Picture extends Model<Picture> {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String description;
    private String url;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonIgnore
    private Integer isDelete;

    @Override
    protected Serializable pkVal() {
        return id;
    }


}

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
 * @date  2017/12/19
 */
@Data
@NoArgsConstructor
public class Student extends Model<Student> {

    @TableId(type = IdType.UUID)
    private String id;

    private String account;
    @JsonIgnore
    private String password;
    private String name;
    private Integer sex;
    private String phone;
    private String email;
    private String clazz;
    private String major;
    private String school;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonIgnore
    private int isDelete;

    private String img;

    private String introduction;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;

    @JsonIgnore
    private Integer status;



    @Override
    protected Serializable pkVal() {
        return id;
    }
}

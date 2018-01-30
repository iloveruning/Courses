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
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author chenliangliang
 * @date 2017/12/20
 */
@Data
@NoArgsConstructor
public class Comment extends Model<Comment> {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "评论内容不能为空")
    @Length(min = 5,max = 1024,message = "评论内容最少5个字符，最大1024个字符")
    private String content;
    private Integer agreeCount;
    /**
     * 是否审核
     */
    @JsonIgnore
    private Integer isCheck;
    /**
     * 审核是否通过
     */
    @JsonIgnore
    private Integer isPass;
    @JsonIgnore
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    @JsonIgnore
    private Integer isDelete;
    @NotBlank(message = "评论对应的章号不能为空")
    private String chapterId;
    @JsonIgnore
    private String studentId;

    @TableField(exist = false)
    private Boolean isAgreed;

    /**
     * 回复数
     */
    private Integer reply;

    @TableField(exist = false)
    private String author;

    @TableField(exist = false)
    private String img;


    @Override
    protected Serializable pkVal() {
        return id;
    }
}

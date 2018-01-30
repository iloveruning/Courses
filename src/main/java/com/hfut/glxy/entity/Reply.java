package com.hfut.glxy.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenliangliang
 * @date: 2017/12/18
 */
@Data
@NoArgsConstructor
public class Reply extends Model<Reply> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String studentId;

    @NotBlank(message = "回复内容不能为空")
    private String content;

    @NotNull(message = "要回复的评论ID不能为空")
    private Integer commentId;

    @JsonIgnore
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonIgnore
    private Integer isDelete;

    private Integer reply=0;

    @TableField(exist = false)
    private String from;

    @TableField(exist = false)
    private String headimg;

    @TableField(exist = false)
    private String to;


    @Override
    protected Serializable pkVal() {
        return id;
    }
}

package com.hfut.glxy.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenliangliang
 * @date 2017/12/30
 */
@Data
@NoArgsConstructor
public class Office extends Model<Office>{

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 在线预览地址
     */
    private String viewUrl;

    /**
     * 文件下载地址
     */
    private String fileUrl;

    /**
     * 封面
     */
    private String cover;

    private String name;

    private String description;

    /**
     * 文件类型
     */
    private String type;

    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return id;
    }
}

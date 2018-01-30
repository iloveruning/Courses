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
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/*@Data
@NoArgsConstructor*/
public class Homework extends Model<Homework>{

    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "学生姓名不能为空")
    private String name;
    @NotBlank(message = "上传文件链接不能为空")
    private Integer officeId;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @JsonIgnore
    private int isDelete;
    //@NotBlank(message = "课程号不能为空")
    private String courseId;


    @Override
    protected Serializable pkVal() {
        return id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}

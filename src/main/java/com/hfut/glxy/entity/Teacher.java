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


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

public class Teacher implements Serializable {
    //所属课程组
    private String courseGroup_id;
    //教师图片
    private String picture_id;
    public String getCourseGroup_id() {
        return courseGroup_id;
    }

    public void setCourseGroup_id(String courseGroup_id) {
        this.courseGroup_id = courseGroup_id;
    }

    public String getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(String picture_id) {
        this.picture_id = picture_id;
    }

    private String id;

    @NotNull(message = "教师编号不能为空")
    private String number;
    @NotNull(message = "教师姓名不能为空")
    private String name;
    @NotNull(message = "教师性别不能为空")
    private String sex;
    private String position;
    private String personIntroduction;
    private Timestamp createTime;
    private Timestamp updateTime;
    private int isDelete;

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", position='" + position + '\'' +
                ", personIntroduction='" + personIntroduction + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPersonIntroduction() {
        return personIntroduction;
    }

    public void setPersonIntroduction(String personIntroduction) {
        this.personIntroduction = personIntroduction;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}

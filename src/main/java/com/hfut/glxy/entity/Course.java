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

public class Course implements Serializable {

    private static final long serialVersionUID = -8046862980802695955L;
    @NotNull(message = "课程组不能为空")
    private String courseGroup_id;//所在课程组


    @NotNull(message = "负责教师不能为空")
    /*private String teacher_id;//负责教师*/
    /*private List<String> teacher_ids;//负责教师*/
    private String [] teacher_ids;

    @NotNull(message = "管理员账户不能为空")
    private String manager_account;//管理员账户
    @NotNull(message = "管理员密码不能为空")
    private String manager_password;//管理员密码
    private String picture_id;//图片

    public String getCourseGroup_id() {
        return courseGroup_id;
    }

    public void setCourseGroup_id(String courseGroup_id) {
        this.courseGroup_id = courseGroup_id;
    }

/*    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }*/

   /* public List<String> getTeacher_ids() {
        return teacher_ids;
    }

    public void setTeacher_ids(List<String> teacher_ids) {
        this.teacher_ids = teacher_ids;
    }*/

    public String[] getTeacher_ids() {
        return teacher_ids;
    }

    public void setTeacher_ids(String[] teacher_ids) {
        this.teacher_ids = teacher_ids;
    }

    public String getManager_account() {
        return manager_account;
    }

    public void setManager_account(String manager_account) {
        this.manager_account = manager_account;
    }

    public String getManager_password() {
        return manager_password;
    }

    public void setManager_password(String manager_password) {
        this.manager_password = manager_password;
    }

    public String getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(String picture_id) {
        this.picture_id = picture_id;
    }

    @NotNull(message = "id为空，传参错误")
    private String id;

    @NotNull(message = "没有指定课程编号")
    private String number;

    @NotNull(message = "没有指定课程名称")
    private String name;

    @NotNull(message = "没有指定课程类型")
    private String type;

    @NotNull(message = "没有指定课程简介")
    private String introduction;

    @NotNull(message = "没有指定课程学分")
    private String credit;

    @NotNull(message = "没有指定课程学时")
    private String hours;

    private Timestamp createTime;
    private Timestamp updateTime;
    private int isDelete;

    @Override
    public String toString() {
        return "CourseDao{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", introduction='" + introduction + '\'' +
                ", credit='" + credit + '\'' +
                ", hours='" + hours + '\'' +
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
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

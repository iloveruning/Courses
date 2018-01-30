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

public class Chapter implements Serializable{

    /*@NotNull(message = "该课程不存在")*/
    private String course_id;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    private String id;

    @NotNull(message = "本章编号不能为空")
    private String number;

    @NotNull(message = "本章名称不能为空")
    private String name;

    private Timestamp createTime;
    private Timestamp updateTime;
    private int isDelete;

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

    @Override
    public String toString() {
        return "Chapter{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                '}';
    }
}

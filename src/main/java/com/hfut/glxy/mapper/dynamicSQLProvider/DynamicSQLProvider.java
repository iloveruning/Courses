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

package com.hfut.glxy.mapper.dynamicSQLProvider;

import com.hfut.glxy.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class DynamicSQLProvider {


    /**
         *
         * @Date 2017/11/22 14:22
         * @author students_ManagementSchool
         * @param course
         * @return
         * @since JDK 1.8
         * @condition 对课程的动态更新语句
    */
    public String updateCourse(@Param("course") Course course){
        return new SQL(){
            {
                UPDATE("course");
                if(course.getNumber()!=null){
                    SET("number=#{course.number}");
                }
                if(course.getName()!=null){
                    SET("name=#{course.name}");
                }
                if (course.getType()!=null){
                    SET("type=#{course.type}");
                }
                if (course.getIntroduction()!=null){
                    SET("introduction=#{course.introduction}");
                }
                if (course.getCredit()!=null){
                    SET("credit=#{course.credit}");
                }
                if (course.getHours()!=null){
                    SET("hours=#{course.hours}");
                }
                if (course.getIsDelete()!=0){
                    SET("isDelete=#{course.isDelete}");
                }
                SET("updateTime=NOW()");
                WHERE("id=#{course.id}");
            }
        }.toString();
    }

    /**
         *
         * @Date 2017/11/22 14:33
         * @author students_ManagementSchool
         * @param courseGroup
         * @return
         * @since JDK 1.8
         * @condition  对课程组的动态更新语句
    */
    public String updateCourseGroup(@Param("courseGroup") CourseGroup courseGroup){
        return new SQL(){
            {
                UPDATE("coursegroup");
                if(courseGroup.getNumber()!=null){
                    SET("number=#{courseGroup.number}");
                }
                if(courseGroup.getName()!=null) {
                    SET("name=#{courseGroup.name}");
                }
                if (courseGroup.getIntroduction()!=null) {
                    SET("introduction=#{courseGroup.introduction}");
                }
                if (courseGroup.getIsDelete()!=0){
                    SET("isDelete=#{courseGroup.isDelete}");
                }
                SET("updateTime=NOW()");
                WHERE("id=#{courseGroup.id}");
            }
        }.toString();
    }

    /**
         *
         * @Date 2017/11/22 14:51
         * @author students_ManagementSchool
         * @param teacher
         * @return
         * @since JDK 1.8
         * @condition  对教师的动态更新语句
    */
    public String updateTeacher(@Param("teacher") Teacher teacher){
        return new SQL(){
            {
                UPDATE("teacher");
                if(teacher.getNumber()!=null){
                    SET("number=#{teacher.number}");
                }
                if(teacher.getName()!=null) {
                    SET("name=#{teacher.name}");
                }
                if (teacher.getSex()!=null) {
                    SET("sex=#{teacher.sex}");
                }
                if (teacher.getPosition()!=null) {
                    SET("position=#{teacher.position}");
                }
                if (teacher.getPersonIntroduction()!=null) {
                    SET("personIntroduction=#{teacher.personIntroduction}");
                }
                if (teacher.getIsDelete()!=0){
                    SET("isDelete=#{teacher.isDelete}");
                }
                SET("updateTime=NOW()");
                WHERE("id=#{teacher.id}");
            }
        }.toString();
    }

    /**
         *
         * @Date 2017/12/5 21:00
         * @author students_ManagementSchool
         * @param manager
         * @return
         * @since JDK 1.8
         * @condition  对管理员的更新
    */
    public String updateManager(@Param("manager") Manager manager){
        return new SQL(){
            {
                UPDATE("manager");
                if(manager.getAccount()!=null){
                    SET("account=#{manager.account}");
                }
                if(manager.getPassword()!=null) {
                    SET("password=#{manager.password}");
                }
                if (manager.getIsSuper()!=0) {
                    SET("issuper=#{manager.isSuper}");
                }
                if (manager.getIsDelete()!=0){
                    SET("isDelete=#{manager.isDelete}");
                }
                SET("updateTime=NOW()");
                WHERE("id=#{manager.id}");
            }
        }.toString();
    }

    /**
         *
         * @Date 2017/12/5 21:04
         * @author students_ManagementSchool
         * @param chapter
         * @return
         * @since JDK 1.8
         * @condition  对章进行更新
    */
    public String updateChapter(@Param("chapter") Chapter chapter){
        return new SQL(){
            {
                UPDATE("chapter");
                if(chapter.getNumber()!=null) {
                    SET("number=#{chapter.number}");
                }
                if(chapter.getName()!=null){
                    SET("name=#{chapter.name}");
                }
                if (chapter.getIsDelete()!=0){
                    SET("isDelete=#{chapter.isDelete}");
                }
                SET("updateTime=NOW()");
                WHERE("id=#{chapter.id}");
            }
        }.toString();
    }

    /**
         *
         * @Date 2017/12/13 22:12
         * @author students_ManagementSchool
         * @param unit
         * @return
         * @since JDK 1.8
         * @condition  对教学单元进行更新
    */
    public String updateUnit(@Param("unit") Unit unit){
        return new SQL(){
            {
                UPDATE("unit");
                if(unit.getNumber()!=null) {
                    SET("number=#{unit.number}");
                }
                if(unit.getName()!=null){
                    SET("name=#{unit.name}");
                }
                if (unit.getContent()!=null){
                    SET("content=#{unit.content}");
                }
                if (unit.getIsDelete()!=0){
                    SET("isDelete=#{unit.isDelete}");
                }
                SET("updateTime=NOW()");
                WHERE("id=#{unit.id}");
            }
        }.toString();
    }

    /**
         *
         * @Date 2017/12/28 20:49
         * @author students_ManagementSchool
         * @param knowledgePoint
         * @return
         * @since JDK 1.8
         * @condition  更新知识点
    */
    public String updateKnowledgePoint(@Param("knowledgePoint") KnowledgePoint knowledgePoint){
        return new SQL(){
            {
                UPDATE("knowledgePoint");
                if(knowledgePoint.getContent()!=null) {
                    SET("content=#{knowledgePoint.content}");
                }
                if (knowledgePoint.getIsDelete()!=0){
                    SET("isDelete=#{knowledgePoint.isDelete}");
                }
                SET("updateTime=NOW()");
                WHERE("id=#{knowledgePoint.id}");
            }
        }.toString();
    }

    /**   
         * 
         * @Date 2018/1/6 11:10
         * @author students_ManagementSchool
         * @param contents
         * @return
         * @since JDK 1.8
         * @condition  对知识点进行模糊查询
    */
    public String getSimilarKnowledgePoints(@Param("contents") List<String> contents){

        String sql="select id from knowledgepoint where content like '%"+contents.get(0)+"%'";

        String condition;

        for (int i=1;i<=contents.size()-1;i++){
            condition=" and content like '%"+contents.get(i)+"%'";
            sql+=condition;
        }

        return sql;
    }
}

package com.hfut.glxy.dto;

import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018/1/15 22:13 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
public class PageResult<T> {

    private long iTotalDisplayRecords; //过滤后查询总数
    private long iTotalRecords; //查询总数
    private List<T> data;//分页查到的数据

    public long getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public long getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

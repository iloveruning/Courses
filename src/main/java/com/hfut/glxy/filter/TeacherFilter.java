package com.hfut.glxy.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author chenliangliang
 * @date 2017/11/29
 */
@WebFilter(urlPatterns = {"/homework","/homework/*","/notice","/notice/*","/admin",
        "/adminsidebar","/addInfo","/infoList","/addChapter","/addData","/addExercise","/addHomework",
        "/chapterDetail","/chapterList","/dataList","/exerciseList","/homeworkList","/adminwelcome","/addComment","/commentList"})
public class TeacherFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpSession session=request.getSession();
        if (session==null){
            request.getRequestDispatcher("/adminlogin").forward(servletRequest,servletResponse);
            return;
        }
        //0---->学生，1---->老师，2----->超级管理员
        Integer role=(Integer) session.getAttribute("role");
        if (role==null||role!=1){
            request.getRequestDispatcher("/adminlogin").forward(servletRequest,servletResponse);
            return;
        }
        String courseId=(String)session.getAttribute("courseId");
        if (courseId==null|| StringUtils.isEmpty(courseId)){
            servletResponse.setContentType("text/json;charset=utf8");
            servletResponse.getWriter().write("{\"success\":false,\"message\":\"没有可管理的课程，请联系超级管理员\"}");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}

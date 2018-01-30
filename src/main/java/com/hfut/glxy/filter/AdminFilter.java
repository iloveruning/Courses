package com.hfut.glxy.filter;

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
@WebFilter(urlPatterns = {"/superadmin","/addCourseGroup","/courseGroupList","/addCourse","/courseList",
        "/superadminsidebar","/addTeacher","/teacherList","/superadminWelcome"})
public class AdminFilter extends GenericFilterBean {
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
        if (role==null||role!=2){
            request.getRequestDispatcher("/adminlogin").forward(servletRequest,servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}

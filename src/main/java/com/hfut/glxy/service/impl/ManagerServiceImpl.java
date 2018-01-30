package com.hfut.glxy.service.impl;

/*import com.hfut.glxy.dao.ManagerDao;
import com.hfut.glxy.dao.Manager_CourseDao;
import com.hfut.glxy.entity.Course;*/
import com.hfut.glxy.entity.Manager;
import com.hfut.glxy.mapper.ManagerDao;
import com.hfut.glxy.mapper.Manager_CourseDao;
import com.hfut.glxy.service.ManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProjectName: Courses <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017/11/25 15:30 <br/>
 *
 * @author students_ManagementSchool
 * @since JDK 1.8
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private ManagerDao managerDao;

    @Resource
    private Manager_CourseDao manager_courseDao;

    /**
         *
         * @Date 2017/11/25 15:51
         * @author students_ManagementSchool
         * @param
         * @return
         * @since JDK 1.8
         * @condition  查询超级管理员
    */
    @Override
    public Manager querySuperManager() throws Exception{

        Manager superManager=managerDao.querySuperManager();
        return superManager;

    }

    /**
         *
         * @Date 2017/11/25 15:55
         * @author students_ManagementSchool
         * @param course_id
         * @return
         * @since JDK 1.8
         * @condition 通过选择登录的管理课程，在管理员课程表中查询管理员Id，根据此Id在管理员表查找此管理员
    */
    @Override
    public Manager queryManagerByCourse(String course_id) throws Exception{

        String manager_id=manager_courseDao.queryManager(course_id);
        Manager manager=managerDao.queryManagerById(manager_id);
        return manager;

    }

    /**
         *
         * @Date 2017/11/25 22:48
         * @author students_ManagementSchool
         * @param manager_id
         * @return
         * @since JDK 1.8
         * @condition  通过id获取管理员
    */
    @Override
    public Manager queryManagerById(String manager_id) throws Exception{

        Manager manager=managerDao.queryManagerById(manager_id);
        return manager;

    }

    /**
         *
         * @Date 2017/11/25 22:49
         * @author students_ManagementSchool
         * @return
         * @since JDK 1.8
         * @condition  获取全部管理员，用于超级管理员的后台管理
    */
    @Override
    public List<Manager> getAllManagers() throws Exception{

        List<Manager> managers=managerDao.getAllManagers();

        return managers;
    }
}

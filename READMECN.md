# 课程网站

## 介绍

由管理学院的学生制作,一个电子商务课程网站

本网站是在 Springboot 基础上的搭建的一个平台采用了 bootstrap 框架实现界面设计,后端语言为 java.

定位于课程的学习,通过网上课程加实体课程,共同巩固知识,包括前台和后台界面.

前台:

1. 主页
2. 课程介绍
3. 课程内容

后台:

界面风格采用了结构简单,性能优良,页面美观大气的 Twitter Bootstrap 页面展示框架分为三个管理权限:超级管理员,管理员,教师师

**超级管理员**:课程管理,课程组管理,教师管理

**管理员**:通知管理,章节管理,作业管理,习题管理

**教师师**:课程管理,课件及视频等管理,老师也是管理员

## 界面
前台界面:


超级管理员界面:
![](https://i.imgur.com/92G2Hy2.png)
![](https://i.imgur.com/x5JdKf4.png)
![](https://i.imgur.com/73XmeaP.png)

管理员界面:
![](https://i.imgur.com/6n6UwDU.png)
![](https://i.imgur.com/RwukA9J.png)

## 技术选型

1,后端

* 核心框架：Spring Boot
* 安全框架：
* 模板引擎：Thymeleaf
* 持久层框架：MyBatis
* 数据库连接池：Alibaba Druid
* 缓存框架：
* 日志管理：log4j,
* 工具类：swaggerui,

2,前端:

* js 框架: jQuery
* 界面框架:Bootstrap
* 数据表格:jquerydatatable
*

3,平台

* 服务器中间件：SpringBoot 内置
* 数据库支持：MySql
* 开发环境：Java,IDEA, Java EE ,Maven ,Git

## 安装使用

JDK,maven,数据库(修改数据库密码), 环境准备好之后直接在**IDEA**中运行`CourseApplication`即可,端口 8080,<br>
前台:<br/>
后台:

  * 管理员地址: `localhost:8080/admin`
  * 超级管理员地址: `localhost:8080/superadmin`
  * API地址: `localhost:8080/swagger-ui.html`

## 演示地址:

######

## gitlab 地址 http://gitlab.autobrandcn.com/Courses

## 版权声明

本网站由工大学子制作,版权所有,翻版必究.

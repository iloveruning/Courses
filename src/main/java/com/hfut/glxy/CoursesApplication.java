/*
 * ClassName: CoursesApplication <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年11月21日<br/>
 *
 * @author students_ManagementSchool
 * @version
 * @since JDK 1.8
 */
package com.hfut.glxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.hfut.glxy"})
@EnableTransactionManagement //开启事务管理，因为service类中需要事务注解进行事务管理
@ServletComponentScan(basePackages = "com.hfut.glxy.filter")
public class CoursesApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoursesApplication.class, args);
    }
}


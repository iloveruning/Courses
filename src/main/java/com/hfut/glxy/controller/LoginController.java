package com.hfut.glxy.controller;

import com.hfut.glxy.dto.LoginBody;
import com.hfut.glxy.dto.Result;
import com.hfut.glxy.entity.Student;
import com.hfut.glxy.service.StudentService;
import com.hfut.glxy.utils.ResultUtil;
import com.hfut.glxy.utils.StringRandom;
import com.hfut.glxy.vcode.Captcha;
import com.hfut.glxy.vcode.GifCaptcha;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2017/12/27
 */
@Controller
@RequestMapping(value = "/login")
//@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE}, allowedHeaders = "*")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);


    private JavaMailSender sender;

    private TemplateEngine templateEngine;

    private StudentService studentService;

    @Value("${spring.mail.username}")
    private String from;


    @Autowired
    protected LoginController(JavaMailSender sender, TemplateEngine templateEngine,
                              StudentService studentService) {
        this.sender = sender;
        this.templateEngine = templateEngine;
        this.studentService = studentService;
    }

    /**
     * 用户登录
     *
     * @param loginBody
     * @param session
     * @param result
     * @return
     */
    @PostMapping("/stu")
    @ResponseBody
    public Result login(@Valid @RequestBody LoginBody loginBody, HttpSession session,
                        BindingResult result) {
        if (result.hasErrors()) {
            return ResultUtil.fail(result.getFieldErrors().toString());
        }
        //先进行验证码验证
        String code_expired = (String) session.getAttribute("v_code");
        if (code_expired == null) {
            logger.error("验证码不存在");
            return ResultUtil.fail("验证码不存在!");
        }

        String[] str = StringUtils.split(code_expired, "#");
        String code = str[0];
        if (!StringUtils.equalsIgnoreCase(code, loginBody.getCode())) {
            logger.error("验证码错误");
            return ResultUtil.fail("验证码错误!");
        }
        long expired = Long.parseLong(str[1]);
        if (expired < System.currentTimeMillis()) {
            logger.error("验证码已过期");
            return ResultUtil.fail("验证码已过期!");
        }

        //进行身份验证
        Student stu = studentService.getLoginInfo(loginBody.getEmail());
        if (stu == null) {
            logger.error("用户不存在");
            return ResultUtil.fail("用户不存在");
        }

        if (stu.getStatus() != 0) {
            logger.error("用户状态异常");
            return ResultUtil.fail("用户状态异常");
        }

        if (!StringUtils.equals(stu.getPassword(), loginBody.getPassword())) {
            logger.error("密码错误");
            return ResultUtil.fail("密码错误");
        }

        //登录成功，将基本用户信息存入session
        session.removeAttribute("v_code");
        session.setAttribute("sid", stu.getId());
        session.setAttribute("role", 0);
        //更新登录时间


        //返回基本用户信息
        Map<String, Object> map = new HashMap<>(6);
        map.put("id", stu.getId());
        map.put("name", stu.getName());
        map.put("img", stu.getImg());
        return ResultUtil.OK(map);

    }

    /**
     * 用户注册
     *
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(@Valid @RequestBody LoginBody loginBody, HttpSession session,
                           BindingResult result) {
        if (result.hasErrors()) {
            return ResultUtil.fail(result.getFieldErrors().toString());
        }
        if (StringUtils.isEmpty(loginBody.getName())) {
            return ResultUtil.fail("用户名不能为空");
        }
        System.out.println("注册时sessionid: " + session.getId());
        //验证码验证
        String code_expired = (String) session.getAttribute("code");
        if (code_expired == null) {
            logger.error("验证码不存在");
            return ResultUtil.fail("验证码不存在!");
        }
        session.removeAttribute("code");
        String[] str = StringUtils.split(code_expired, "#");
        String code = str[0];
        if (!StringUtils.equals(code, loginBody.getCode())) {
            logger.error("验证码错误");
            return ResultUtil.fail("验证码错误!");
        }
        long expired = Long.parseLong(str[1]);
        if (expired < System.currentTimeMillis()) {
            logger.error("验证码已过期");
            return ResultUtil.fail("验证码已过期!");
        }
        Student stu = new Student();
        stu.setName(loginBody.getName());
        stu.setEmail(loginBody.getEmail());
        stu.setPassword(loginBody.getPassword());
        if (studentService.insert(stu)) {
            session.removeAttribute("code");
            return ResultUtil.OK();
        } else {
            return ResultUtil.fail("注册失败");
        }
    }


    /**
     * 用户退出登录
     *
     * @param request
     * @return
     */
    @GetMapping("/logout")
    @ResponseBody
    public Result logout(HttpServletRequest request) {

        //防止创建Session
        HttpSession session = request.getSession(false);
        //logger.info("用户 "+session.getAttribute("studentId").toString()+" 退出登录");
        //// 清除session数据
        if (session == null) {
            return ResultUtil.OK();
        }
        Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            session.removeAttribute(names.nextElement());
        }
        session.invalidate();

        return ResultUtil.OK();
    }


    /**
     * 邮箱唯一性验证
     *
     * @param map
     * @return
     */
    @PostMapping("/valid")
    @ResponseBody
    public Result validEmail(@RequestBody Map<String, Object> map) {
        String email = (String) map.get("email");
        if (email == null) {
            return ResultUtil.fail("邮箱不能为空");
        }

        if (studentService.isExistByEmail(email)) {
            return ResultUtil.fail("该邮箱已被注册");
        }
        return ResultUtil.OK();
    }

    /**
     * 获取验证码（Gif版本）
     *
     * @param response
     */
    @GetMapping(value = "/getGifCode")
    public void getGifCode(HttpServletResponse response, HttpSession session) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");
            /**
             * gif格式动画验证码
             * 宽，高，位数。
             */
            Captcha captcha = new GifCaptcha(146, 33, 4);
            //输出
            captcha.out(response.getOutputStream());
            //存入Session
            long expired = System.currentTimeMillis() + 60000;
            //session.removeAttribute("v_code");
            session.setAttribute("v_code", captcha.text() + "#" + expired);
            System.out.println(captcha.text() + "#" + expired);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取验证码异常：" + e.getMessage());
        }

    }


    @GetMapping("/mail")
    @ResponseBody
    public Result sendMail() {

        //MimeMessage mimeMessage=sender.createMimeMessage();
        //MimeMailMessage message=new MimeMailMessage(mimeMessage);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("3304734570@qq.com");
        message.setSubject("测试邮件主题");
        message.setText("测试邮件内容");

        try {
            sender.send(message);
            logger.info("简单邮件已经发送。");
            return ResultUtil.OK("邮件已经发送");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
            return ResultUtil.fail("发送简单邮件时发生异常");
        }

    }


    @GetMapping("/attach")
    @ResponseBody
    public Result sendAttachmentsMail() {
        MimeMessage mimeMessage = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo("3304734570@qq.com");
            helper.setSubject("主题，测试带附件");
            helper.setText("<html><body><img src=\"http://image.chenliangliang.xin/denghuo\" /></body></html>", true);
            FileSystemResource file = new FileSystemResource(new File("F:/test/5f68e4c19a79b56b2369c149b620b4ce.jpg"));
            helper.addAttachment("附件-1.jpg", file);

            sender.send(mimeMessage);
            logger.info("简单邮件已经发送。");
            return ResultUtil.OK("邮件已经发送");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
            return ResultUtil.fail("发送简单邮件时发生异常");
        }
    }


    /**
     * 邮箱验证
     *
     * @param req
     * @param map
     * @return
     */
    @PostMapping("/email")
    @ResponseBody
    public Result sendTemplateMail(HttpServletRequest req, @RequestBody Map<String, Object> map) {

        String to = (String) map.getOrDefault("email", "0");
        if (StringUtils.equals(to, "0")) {
            return ResultUtil.fail("邮箱地址错误");
        }
        MimeMessage mimeMessage = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from, "管理学院精品课程网");
            helper.setTo(to);
            helper.setSubject("注册验证");
            //验证码
            String code = StringRandom.generateCode(4);
            //到期时间
            long expired = System.currentTimeMillis() + 3600000;
            Context context = new Context();
            context.setVariable("email", to);
            context.setVariable("code", code);

            String text = templateEngine.process("email", context);

            helper.setText(text, true);
            sender.send(mimeMessage);
            //存入session
            HttpSession session = req.getSession(true);
            session.setAttribute("code", code + "#" + expired);
            System.out.println("发邮件sessionid: " + session.getId());
            logger.info("邮件已经发送。");
            return ResultUtil.OK("邮件已经发送");
        } catch (Exception e) {
            logger.error("发送邮件时发生异常！", e);
            return ResultUtil.fail("发送邮件时发生异常");
        }
    }


    @GetMapping("/status")
    @ResponseBody
    public Result getLoginStatus(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie);
            System.out.println("value: " + cookie.getValue());
        }
        return ResultUtil.fail("test");
    }


    @PostMapping("/status")
    @ResponseBody
    public Result getLoginStatus1(@RequestBody Map<String,Object> map, HttpSession session){

        String sid=(String)session.getAttribute("id");
        System.out.println("sid: "+sid);
        String id=(String) map.get("id");
        System.out.println("id: "+id);
        if(StringUtils.equals(sid,id)){
            return ResultUtil.OK();
        }
       return ResultUtil.fail("session过期，请重新登录");
    }
}

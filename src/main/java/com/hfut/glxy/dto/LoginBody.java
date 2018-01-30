package com.hfut.glxy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2017/12/27
 */
@Data
@NoArgsConstructor
public class LoginBody implements Serializable{

    @Email(message = "邮箱格式错误")
    private String email;

    //@NotBlank(message = "用户名不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

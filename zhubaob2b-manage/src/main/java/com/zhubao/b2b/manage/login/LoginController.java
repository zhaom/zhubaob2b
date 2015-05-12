package com.zhubao.b2b.manage.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(value = "/login.do")
    public String login(HttpServletRequest request) {
        return "login/login";
    }

    @RequestMapping(value = "/loginSucc.do")
    public String loginSucc(HttpServletRequest request) {
        return "login/login_succ";
    }

}

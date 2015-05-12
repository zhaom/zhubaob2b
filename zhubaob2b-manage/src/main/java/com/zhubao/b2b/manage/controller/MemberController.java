package com.zhubao.b2b.manage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */

@Controller
@RequestMapping(value = "/manage/member")
public class MemberController {

    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    @RequestMapping(value = "/list.do")
    public String list(HttpServletRequest request, Model model) {
        return "member/list";
    }

    public String addMember(HttpServletRequest request, Model model){
        return "member/newMember";
    }
}

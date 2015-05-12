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
 * Date: 14-2-10
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping(value = "/manage/shop")
public class ShopController {

    private static Logger logger = LoggerFactory.getLogger(ShopController.class);

    @RequestMapping(value = "/profile.do")
    public String shopProfile(HttpServletRequest request, Model model){
        logger.debug("request shop profile");
        return "shop/profile";
    }

}

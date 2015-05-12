package com.zhubao.b2b.manage.controller;

import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.model.Vender;
import com.zhubao.b2b.platform.service.GoodsService;
import com.zhubao.b2b.platform.service.OrderService;
import com.zhubao.b2b.platform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/manage/main")
public class MainController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    /**
     * homepage  homepage-all
     * @param request
     * @return
     */
    @RequestMapping(value = "/main.do")
    public String main(HttpServletRequest request, Model model) {
        logger.debug("main request");
        Vender vender = (Vender)request.getAttribute("vender");
        if(vender == null){
            return "error/error";
        }
        logger.debug("user[{}] request homepage all", vender.getId());

        Map<String, Integer> orderMap = orderService.countByStatusVender(vender.getKshopAgencyId()).getValue();

        Map<String, Integer> goodsMap = goodsService.countByMaterial(vender.getKshopAgencyId());

        List<Customer> customerList = userService.getMembers(vender.getId()).getValue();

        model.addAttribute("orderMap", orderMap);
        model.addAttribute("goodsMap", goodsMap);
        model.addAttribute("members", customerList);
        return "main/main";
    }

    /**
     * home-help
     * @return
     */
    @RequestMapping(value = "/help.do")
    public String main() {
        return "main/help";
    }
}

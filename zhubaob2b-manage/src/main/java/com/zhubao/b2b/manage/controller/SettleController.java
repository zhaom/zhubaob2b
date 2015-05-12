package com.zhubao.b2b.manage.controller;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.manage.templateUtils.Long2Date;
import com.zhubao.b2b.platform.entry.SettleAccountQueryParameter;
import com.zhubao.b2b.platform.model.SettleAccount;
import com.zhubao.b2b.platform.model.Vender;
import com.zhubao.b2b.platform.service.SettleAccountService;
import com.zhubao.common.utils.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
@Controller
@RequestMapping(value = "/manage/settle")
public class SettleController {

    private static Logger logger = LoggerFactory.getLogger(SettleController.class);

    @Autowired
    private SettleAccountService settleAccountService;

    @RequestMapping(value = "/list.do")
    public String list(HttpServletRequest request, Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.debug("list request");
        Vender vender = (Vender)request.getAttribute("vender");
        if(vender == null){
            return "error/error";
        }
        SettleAccountQueryParameter settleAccountQueryParameter = new SettleAccountQueryParameter();
        settleAccountQueryParameter.setVenderId(vender.getKshopAgencyId());

        ServiceResult<Pagination<SettleAccount>> serviceResult = settleAccountService.getPaginationSettleAccounts(vender.getId(), page, pageSize);
        model.addAttribute("pagination", serviceResult.getValue());
        model.addAttribute("timer", new Long2Date());
        return "settle/list";
    }

    @RequestMapping(value = "/detail.do")
    public String getDetail(HttpServletRequest request, Model model,
                            @RequestParam(value = "id", required = true) String settleId) {
        logger.debug("get detail of settle:[{}]", settleId);
        Vender vender = (Vender)request.getAttribute("vender");
        if(vender == null){
            return "error/error";
        }
        SettleAccount settleAccount = settleAccountService.getDetail(vender.getId(), settleId).getValue();
        model.addAttribute("settle", settleAccount);
        model.addAttribute("timer", new Long2Date());
        return "settle/detail";
    }

    @RequestMapping(value = "/confirm.do")
    public String confirm(HttpServletRequest request, Model model,
                          @RequestParam(value = "id", required = true)String settleId) {
        logger.debug("confirm settle:[{}]", settleId);
        Vender vender = (Vender)request.getAttribute("vender");
        if(vender == null){
            return "error/error";
        }
        ServiceResult<SettleAccount> serviceResult = settleAccountService.confirmSettleAccount(vender.getId(), settleId);
        model.addAttribute("settle", serviceResult.getValue());
        model.addAttribute("timer", new Long2Date());
        model.addAttribute("message", "确认成功!");
        return "opt_ok";
    }

    @RequestMapping(value = "/check.do")
    public String check(HttpServletRequest request, Model model,
                        @RequestParam(value = "id", required = true)String settleId) {
        logger.debug("check settle:[{}]", settleId);
        Vender vender = (Vender)request.getAttribute("vender");
        if(vender == null){
            return "error/error";
        }
        ServiceResult<SettleAccount> serviceResult = settleAccountService.checkSettleAccount(vender.getId(), settleId);
        model.addAttribute("settle", serviceResult.getValue());
        model.addAttribute("timer", new Long2Date());
        if(serviceResult.isSuccess()) {
            model.addAttribute("message", "付款成功!");
        } else {
            model.addAttribute("message", "付款发生严重错误!");
        }
        return "opt_ok";
    }

    @RequestMapping(value = "/delete.do")
    public String delete(HttpServletRequest request, Model model,
                         @RequestParam(value = "id", required = true)String settleId){
        logger.debug("delete settle:[{}]", settleId);
        Vender vender = (Vender)request.getAttribute("vender");
        if(vender == null){
            return "error/error";
        }
        ServiceResult<SettleAccount> serviceResult = settleAccountService.deleteSettleAccount(vender.getId(), settleId);
        model.addAttribute("settle", serviceResult.getValue());
        model.addAttribute("timer", new Long2Date());
        model.addAttribute("message", "删除成功!");
        return "opt_ok";
    }
}

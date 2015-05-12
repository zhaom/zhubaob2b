package com.zhubao.b2b.manage.interceptor;

import com.zhubao.b2b.platform.model.KshopAgency;
import com.zhubao.b2b.platform.model.Vender;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 模拟数据
        Vender vender = new Vender();
        vender.setId("5295967f0e7832cd7a4f67864700b015");
        vender.setKshopAgencyId("523");
        KshopAgency agency = new KshopAgency();
        agency.setKshopAgencyId("523");
        agency.setKshopAgencyName("天鑫洋金店");
        vender.setAgency(agency);
        request.setAttribute("vender", vender);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //  当前用户信息填充到 model
        //  Vender vender = (Vender) request.getAttribute("vender");
        //  modelAndView.addObject("vender", vender);
    }
}

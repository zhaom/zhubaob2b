package com.zhubao.b2b.manage.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RefererInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  设置来访路径
        String referer = request.getParameter("referer");
        if (StringUtils.isEmpty(referer)) {
            referer = request.getHeader("REFERER");
        }

        if (StringUtils.isNotEmpty(referer)) {
            request.setAttribute("referer", referer);
        }
        return true;
    }
}

package com.zhubao.b2b.manage.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * User: xiaoping lu
 * Date: 13-11-14
 * Time: 下午4:25
 */
public class BasicController {

    protected float getFloatParameter(HttpServletRequest request, String paramName) {
        try {
            return Float.valueOf(request.getParameter(paramName));
        } catch (Exception e) {
            return 0f;
        }
    }

    protected boolean compareParameterValue(HttpServletRequest request, String paramName, String compareValue) {
        try {
            return compareValue.equals(request.getParameter(paramName));
        } catch (Exception e) {
            return false;
        }
    }
}

package com.zhubao.b2b.common.filter;

import com.zhubao.b2b.common.security.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator      `
 */

public class TokenVerifyFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(TokenVerifyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String uri = httpServletRequest.getRequestURI();
        String userid = httpServletRequest.getHeader("uid");
        String token = httpServletRequest.getHeader("token");

        logger.debug("token filter uid:[{}] token:[{}]", userid, token);

        if (userid != null && token != null) {
            int tokenLen = token.length();
            int dtLen = tokenLen - 32;
            int index = Integer.parseInt(token.substring(tokenLen - 2), 16) % dtLen;
            String newToken = token.substring(0, index) + token.substring(index + dtLen);
            String dtStr = token.substring(index, index + dtLen);

            logger.debug("token filter newtoken:[{}] dt:[{}]", newToken, dtStr);

            if (newToken.equalsIgnoreCase(MD5Util.hexString(userid + dtStr, Charset.forName("UTF-8")))) {
                long dt = Long.parseLong(dtStr, 16);
                long nowDt = new Date().getTime();
                if (nowDt - dt < 2 * 60 * 60 * 1000) {
                    logger.debug("valid token");
                    chain.doFilter(request, response);
                } else {
                    logger.error("invalid token,beyond valid time range");
                    httpServletResponse.setStatus(401);
                }
            } else {
                logger.error("invalid token, token not an valid sign");
                httpServletResponse.setStatus(401);
            }
        } else {
            logger.error("token required");
            httpServletResponse.setStatus(401);
        }
    }

    private String getUserid(String uri) {
        String result = null;
        try {
            String userPart = uri.substring(uri.indexOf("/user/"));
            int idEndIndex = userPart.indexOf("/", 6);
            result = userPart.substring(6, idEndIndex - 1);
        } catch (Exception e) {
            logger.error("get userid exception,so invalid request");
        }
        return result;
    }

    @Override
    public void destroy() {

    }
}

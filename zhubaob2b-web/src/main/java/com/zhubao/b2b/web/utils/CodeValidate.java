package com.zhubao.b2b.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

public final class CodeValidate {

	public static boolean validate(HttpServletRequest request, String sessionKey, String mobile,
			String code) {
		Assert.notNull(request, "HttpServletRequest could not be null.");
		Assert.notNull(sessionKey, "Session key could not be null.");

		if (mobile != null && code != null) {
			HttpSession session = request.getSession();
			String sessionCode = (String) session.getAttribute(sessionKey);

			if (sessionCode != null) {
				session.removeAttribute(sessionKey);
				return sessionCode != null && sessionCode.equals(mobile + "_" + code);
			}

			session.removeAttribute(sessionKey);
		}

		return false;
	}

}
package com.zhubao.b2b.web.controller;

/**
 * 
 * 供应商 ID：kshopAgencyId
 * 买家 ID：id
 * 
 */

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.MemcachedClient;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.service.UserService;
import com.zhubao.b2b.web.utils.Constants;

public abstract class AbstractController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected MemcachedClient memcachedClient;

	@Autowired
	protected UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return null;
	}

	@RequestMapping(value = "/{id}")
	public String show(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("id") String id) {
		return null;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable("id") String id) {
		return null;
	}

	protected static Customer getLoginUser() {
		return getLoginUser(false);
	}

	protected static Customer getLoginUser(boolean returnRemembered) {
		Subject subject = SecurityUtils.getSubject();

		if (subject == null) {
			return null;
		}

		Session session = subject.getSession();
		if (session == null) {
			if (subject.isRemembered() == true) {
				return (Customer) subject.getPrincipal();
			}

			return null;
		}

		return (Customer) session.getAttribute(Constants.USER_LOGIN_SESSION_KEY);
	}

	protected static String getCurrentUrl(HttpServletRequest request) {
		String url = request.getPathInfo();
		String queryString = request.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			url += "?" + queryString;
		}

		return url;
	}

	protected static String getPageBaseUrl(HttpServletRequest request) {
		String queryString = request.getQueryString();

		queryString = queryString != null ? queryString.replaceAll("&?page=\\d*&?", "") + "page="
				: "page=";

		return request.getPathInfo() + "?" + queryString;
	}

}
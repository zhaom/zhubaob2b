package com.zhubao.b2b.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.service.ShipAddressService;
import com.zhubao.b2b.web.auth.UsernamePasswordToken;

@Controller
@RequestMapping(value = "/User")
public class UserController extends AbstractController {

	@Autowired
	private ShipAddressService shipAddressService;

	@RequestMapping(value = "/Login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "isAjax", required = false, defaultValue = "0") boolean isAjax) {
		String referer = request.getHeader("Referer");
		if (referer != null) {
			try {
				referer = URLEncoder.encode(referer, request.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
			}
		}

		model.addAttribute("redirect", referer);
		model.addAttribute("isAjax", isAjax);

		return "user/Login";
	}

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> login(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "isRememberMe", required = false, defaultValue = "0") boolean isRememberMe,
			@RequestParam(value = "redirect", required = false, defaultValue = "/") String redirect) {
		Map<String, Object> data = new HashMap<String, Object>();

		if (redirect == null || redirect.length() == 0) {
			redirect = "/";
		} else {
			try {
				redirect = URLDecoder.decode(redirect, request.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				redirect = "/";
			}
		}

		int dot_i = redirect.indexOf(',');
		if (dot_i > -1) {
			redirect = redirect.substring(dot_i + 1);
		}

		Subject subject = SecurityUtils.getSubject();

		if (subject.isAuthenticated() == true) {
			data.put("state", true);
			data.put("redirect", redirect);

			return data;
		}

		UsernamePasswordToken token = new UsernamePasswordToken(username, password, isRememberMe);

		try {
			subject.login(token);
			data.put("state", true);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			data.put("state", false);
			data.put("message", "用户名或密码不正确!");
			logger.error(e.getMessage());
		}

		data.put("redirect", redirect);

		return data;
	}

	@RequestMapping(value = "/Logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();

		return "redirect:/";
	}

	@RequestMapping(value = "/profile")
	@RequiresAuthentication
	public String profile(HttpServletRequest request, HttpServletResponse response, Model model) {
		Customer user = getLoginUser();

		model.addAttribute("user", user);
		model.addAttribute("addresses", shipAddressService.getAddresses(user.getId()));

		return "user/profile";
	}

}
package com.zhubao.b2b.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhubao.b2b.web.utils.Constants;

@Controller
@RequestMapping(value = "/sms")
public class SmsController extends AbstractController {

	@RequestMapping(value = "/sendValidCode", method = RequestMethod.POST)
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> sendValidCode(HttpServletRequest request, HttpServletResponse response,
			Model model, @RequestParam("mobile") String mobile) {
		Map<String, Object> result = new HashMap<String, Object>();

		HttpSession session = request.getSession();

		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
				'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
				'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

		String code = "";
		for (int i = 0; i < 6; i++) {
			int j = (int) Math.round(Math.random() * (hexDigits.length - 1) + 0);
			code += hexDigits[j];
		}
		code = "123456";	// 正式接入短信接口时去掉
		session.setAttribute(Constants.PAY_CODE_SESSION_KEY, mobile + "_" + code);

		result.put("state", true);
		result.put("code", code);

		return result;
	}

}
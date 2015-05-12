package com.zhubao.b2b.web.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.internal.OperationFuture;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Producer;
import com.zhubao.common.utils.Constants;

@Controller
@RequestMapping(value = "/captcha")
public class CaptchaController extends AbstractController {

	@Resource
	private Producer captchaProducer;

	@RequestMapping(value = "/captcha.png")
	public void captcha(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setContentType("image/png; charset=UTF-8");

		String text = captchaProducer.createText();

		OperationFuture<Boolean> result = memcachedClient.add(Constants.CAPTCHA_SESSION_KEY,
				Constants.CAPTCHA_SESSION_LIFETIME, text);
		if (result.getStatus().isSuccess() == true) {
			logger.info("captcha session value: " + text);
		} else {
			logger.debug("set captcha session value failure");
		}
		BufferedImage bi = captchaProducer.createImage(text);

		ServletOutputStream out;
		try {
			out = response.getOutputStream();
			try {
				ImageIO.write(bi, "png", out);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				out.flush();
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/valid")
	public @ResponseBody
	Map<String, Object> valid(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "value", required = false, defaultValue = "") String value) {
		Map<String, Object> data = new HashMap<String, Object>();

		Object sessionValue = memcachedClient.get(Constants.CAPTCHA_SESSION_KEY);
		data.put("state", sessionValue != null && value.equalsIgnoreCase(sessionValue.toString()));

		return data;
	}

}
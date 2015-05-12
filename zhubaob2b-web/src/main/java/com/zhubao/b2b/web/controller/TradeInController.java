package com.zhubao.b2b.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.platform.entry.BuybackQueryParameter;
import com.zhubao.b2b.platform.model.BuybackApply;
import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.service.BuybackService;
import com.zhubao.b2b.web.utils.UrlUtils;
import com.zhubao.common.utils.Constants;
import com.zhubao.common.utils.Pagination;

@Controller
@RequestMapping(value = "/tradeIn")
public class TradeInController extends AbstractController {

	@Autowired
	private BuybackService buybackService;

	@Override
	@RequiresAuthentication
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return list(request, response, model, 1);
	}

	@RequestMapping(value = "/list")
	@RequiresAuthentication
	public String list(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		Customer user = getLoginUser();

		BuybackQueryParameter queryParameter = new BuybackQueryParameter();
		queryParameter.setCustomerId(user.getId());
		ServiceResult<Pagination<BuybackApply>> result = buybackService.getPaginationApply(
				queryParameter, page, Constants.PAGESIZE);

		if (result.isSuccess()) {
			Pagination<BuybackApply> pagination = result.getValue();

			model.addAttribute("totals", pagination.getTotalRecords());
			model.addAttribute("pagination", pagination);
			model.addAttribute("urlUtils", new UrlUtils(request));
			model.addAttribute("pageUrl", getPageBaseUrl(request));
			model.addAttribute("queryString", request.getQueryString());
			model.addAttribute("currentUrl", getCurrentUrl(request));
			model.addAttribute("data", pagination.getData());
		} else {
			model.addAttribute("data", new ArrayList<Object>());
		}

		return "tradeIn/index";
	}

	@RequestMapping(value = "/apply")
	@RequiresAuthentication
	public String apply(HttpServletRequest request, HttpServletResponse response, Model model) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("Y-M-d");
		model.addAttribute("currentDate", dateFormat.format(new Date()));

		Customer user = getLoginUser();
		model.addAttribute("user", user);
		model.addAttribute("venders", userService.getAllVenders());

		return "tradeIn/apply";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> add(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("buybackApply") BuybackApply buybackApply) {
		Map<String, Object> data = new HashMap<String, Object>();

		Customer user = getLoginUser();
		ServiceResult<BuybackApply> result = buybackService.createApply(user.getId(),
				buybackApply.getVenderId(), buybackApply.getApplyTime(), buybackApply.getItems());

		if (result.isSuccess() == true) {
			data.put("state", true);
			data.put("data", result.getValue());
		} else {
			data.put("state", false);
			switch (result.getErrorCode()) {
				case HttpStatus.SC_FORBIDDEN:
					data.put(
							"message",
							"用户"
									+ (user.getKshopUserName() == null ? "" : "["
											+ user.getKshopUserName() + "]") + "不存在");
					break;
				case HttpStatus.SC_BAD_REQUEST:
					data.put("message", "无效的供应商");
					break;
				case HttpStatus.SC_INTERNAL_SERVER_ERROR:
					data.put("message", "系统错误");
					break;
				default:
					data.put("message", result.getValue());
					break;
			}
		}

		return data;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	@RequiresAuthentication
	public String edit(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("buybackApply") BuybackApply buybackApply) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("Y-M-d");
		model.addAttribute("currentDate", dateFormat.format(new Date()));

		Customer user = getLoginUser();
		model.addAttribute("user", user);
		model.addAttribute("venders", userService.getAllVenders());

		return "tradeIn/apply";
	}

	@Override
	@RequiresAuthentication
	public String show(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("id") String id) {
		Customer user = getLoginUser();

		ServiceResult<BuybackApply> result = buybackService.getApplyById(user.getId(), id);
		if (result.isSuccess() == true) {
			model.addAttribute("r", result.getValue());
		}

		return "tradeIn/show";
	}

	@Override
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable(value = "id") String id) {
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}

	protected static String getPageBaseUrl(HttpServletRequest request) {
		String queryString = request.getQueryString();

		queryString = queryString != null ? queryString.replaceAll("&?page=\\d*&?", "") + "page="
				: "page=";

		return "/tradeIn/list?" + queryString;
	}

}
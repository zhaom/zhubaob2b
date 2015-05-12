package com.zhubao.b2b.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresUser;
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
import com.zhubao.b2b.platform.model.BalanceOrderFlow;
import com.zhubao.b2b.platform.model.BuybackApply;
import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.model.Vender;
import com.zhubao.b2b.platform.service.OrderService;
import com.zhubao.b2b.web.utils.UrlUtils;
import com.zhubao.common.utils.Constants;
import com.zhubao.common.utils.Pagination;

@Controller
@RequestMapping(value = "/balance")
public class BalanceController extends AbstractController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/")
	@RequiresAuthentication
	public String index(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "type", required = false, defaultValue = "0") boolean isFinished,
			@RequestParam(value = "companyId", required = false) String companyId,
			@RequestParam(value = "conditionTime", required = false) String conditionTime,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		Customer user = getLoginUser();

		ServiceResult<Pagination<Order>> result;

		if (isFinished == true) {
			result = orderService.getBalancedOrderByUserId(user.getId(), page, Constants.PAGESIZE);
		} else {
			result = orderService.getUnbalanceOrderByUserId(user.getId(), page, Constants.PAGESIZE);
		}

		model.addAttribute("isFinished", isFinished);
		model.addAttribute("companyId", companyId);
		model.addAttribute("companyies", getCompanyies());

		if (result.isSuccess()) {
			Date date = new Date();
			Pagination<Order> pagination = result.getValue();

			model.addAttribute("currentGoldPrice", 220.00);

			model.addAttribute("totals", pagination.getTotalRecords());
			model.addAttribute("pagination", pagination);
			model.addAttribute("urlUtils", new UrlUtils(request));
			model.addAttribute("pageUrl", getPageBaseUrl(request));
			model.addAttribute("queryString", request.getQueryString());
			model.addAttribute("currentUrl", getCurrentUrl(request));
			model.addAttribute("orders", pagination.getData());

			model.addAttribute("date", date);
			model.addAttribute("dateFormat", new SimpleDateFormat("yyyy-MM-dd HH:mm"));
		} else {
			model.addAttribute("orders", new ArrayList<Object>());
		}

		return "order/balance/list";
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
		ServiceResult<BuybackApply> result = null;// buybackService.createApply(user.getId(),
		// buybackApply.getVenderId(), buybackApply.getApplyTime(), buybackApply.getItems());

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

	@Override
	@RequiresAuthentication
	public String show(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("id") String id) {
		Customer user = getLoginUser();
		ServiceResult<Order> orderResult = orderService.getOrder(user.getId(), id);

		if (orderResult.isSuccess() == true) {
			Order order = orderResult.getValue();

			model.addAttribute("currentGoldPrice", 220.00);
			model.addAttribute("r", order);

			ServiceResult<List<BalanceOrderFlow>> flowResult = orderService.listBalanceOrderFlow(
					user.getId(), id);
			if (flowResult.isSuccess() == true) {
				model.addAttribute("dateFormat", new SimpleDateFormat("yyyy-MM-dd HH:mm"));
				model.addAttribute("flows", flowResult.getValue());
			} else {
				model.addAttribute("flows", null);
			}

			return "order/balance/detail";
		}

		return null;
	}

	@RequestMapping(value = "/stat")
	@RequiresUser
	public @ResponseBody
	Map<String, Object> stat(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		Customer user = getLoginUser();
		int total = 0;

		if (user != null) {
			ServiceResult<Pagination<Order>> result = orderService.getBalancedOrderByUserId(
					user.getId(), 1, 10);

			if (result.isSuccess() == true) {
				Pagination<Order> pagination = result.getValue();
				total = pagination.getTotalRecords();
			}
		}

		data.put("state", true);
		data.put("totals", total);

		return data;
	}

	private Map<String, Vender> getCompanyies() {
		List<Vender> companyies = userService.getAllVenders();

		if (companyies == null) {
			return null;
		}

		Map<String, Vender> result = new LinkedHashMap<String, Vender>();
		for (Vender vender : companyies) {
			result.put(vender.getKshopAgencyId(), vender);
		}

		return result;
	}

}
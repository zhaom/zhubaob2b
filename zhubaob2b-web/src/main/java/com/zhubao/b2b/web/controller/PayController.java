package com.zhubao.b2b.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.service.OrderService;
import com.zhubao.b2b.web.utils.CodeValidate;
import com.zhubao.b2b.web.utils.Constants;

@Controller
@RequestMapping(value = "/pay")
public class PayController extends AbstractController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@RequiresAuthentication
	public String pay(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("type") String type, @RequestParam("orderId") String orderId) {
		if ("order".equalsIgnoreCase(type) == true) {
			return orderPay(request, response, model, orderId);
		} else if ("balance".equalsIgnoreCase(type) == true) {
			return balancePay(request, response, model, orderId);
		}

		return null;
	}

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> payment(HttpServletRequest request, HttpServletResponse response,
			Model model, @RequestParam("type") String type, @RequestParam("orderId") String orderId) {
		if ("order".equalsIgnoreCase(type) == true) {
			return orderPayment(request, response, model, orderId);
		} else if ("balance".equalsIgnoreCase(type) == true) {
			return balancePayment(request, response, model, orderId);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("state", false);
		result.put("message", "未知支付类型");
		return result;
	}

	/**
	 * 订单支付页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param orderId
	 * @return
	 */
	private String orderPay(HttpServletRequest request, HttpServletResponse response, Model model,
			String orderId) {
		Customer user = getLoginUser();

		model.addAttribute("orderId", orderId);

		ServiceResult<Order> orderResult = orderService.getOrder(user.getId(), orderId);
		if (orderResult.isSuccess() == true) {
			orderResult = orderService.modifyShipmentAddr(user.getId(), orderId,
					request.getParameter("shipAddressId"));
			orderResult = orderService.modifyPayway(user.getId(), orderId,
					request.getParameter("payway"));

			model.addAttribute("order", orderResult.getValue());
			model.addAttribute("hedging_payway",
					com.zhubao.b2b.common.utils.Constants.PAYWAY_HEDGING_KSHOP);
		}

		return "pay/orderPay";
	}

	/**
	 * 订单支付
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param orderId
	 * @return
	 */
	private @ResponseBody
	Map<String, Object> orderPayment(HttpServletRequest request, HttpServletResponse response,
			Model model, String orderId) {
		Customer user = getLoginUser();
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String validCode = request.getParameter("validCode");
		Map<String, Object> result = new HashMap<String, Object>();

		if (CodeValidate.validate(request, Constants.PAY_CODE_SESSION_KEY, mobile, validCode) == true) {
			ServiceResult<Order> payResult = orderService.payOrder(user.getId(), password, orderId,
					mobile, validCode);
			if (payResult.isSuccess() == true) {
				result.put("state", true);
				return result;
			}

			result.put("message", payResult.getErrorMessage());
		} else {
			result.put("message", "验证码不正确");
		}

		result.put("state", false);

		return result;
	}

	/**
	 * 结价支付页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param orderId
	 * @return
	 */
	private String balancePay(HttpServletRequest request, HttpServletResponse response,
			Model model, String orderId) {
		String[] goodsIds = request.getParameterValues("goodsIds[]");
		Customer user = getLoginUser();
		Map<String, String> weights = new LinkedHashMap<String, String>();

		model.addAttribute("orderId", orderId);
		model.addAttribute("user", user);
		model.addAttribute("weight", request.getParameter("weight"));

		if (goodsIds != null) {
			for (int i = 0; i < goodsIds.length; i++) {
				String goodsId = goodsIds[i];
				weights.put(goodsId, request.getParameter("weights[" + goodsId + "]"));
			}
		}

		model.addAttribute("weights", weights);

		return "pay/balancePay";
	}

	/**
	 * 结价支付
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param orderId
	 * @return
	 */
	private @ResponseBody
	Map<String, Object> balancePayment(HttpServletRequest request, HttpServletResponse response,
			Model model, String orderId) {
		Customer user = getLoginUser();
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String validCode = request.getParameter("validCode");
		String[] goodsIds = request.getParameterValues("goodsIds[]");
		List<String> goodsIdList = new ArrayList<String>();
		List<Float> goodsWeightList = new ArrayList<Float>();
		Map<String, Object> result = new HashMap<String, Object>();

		if (CodeValidate.validate(request, Constants.PAY_CODE_SESSION_KEY, mobile, validCode) == true) {
			if (goodsIds != null) {
				for (int i = 0; i < goodsIds.length; i++) {
					String goodsId = goodsIds[i];
					String weight = request.getParameter("weights[" + goodsId + "]");

					goodsIdList.add(goodsId);
					goodsWeightList.add(Float.parseFloat(weight));
				}
			}

			ServiceResult<Order> payResult = orderService.balancePay(user.getId(), password,
					orderId, goodsIdList, goodsWeightList);
			if (payResult.isSuccess() == true) {
				result.put("state", true);
				return result;
			}

			result.put("message", payResult.getErrorMessage());
		} else {
			result.put("message", "验证码不正确");
		}

		result.put("state", false);

		return result;
	}
}
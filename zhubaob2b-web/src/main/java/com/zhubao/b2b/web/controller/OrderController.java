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

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.common.utils.OrderStatusMap;
import com.zhubao.b2b.platform.entry.OrderQueryParameter;
import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.model.ShipAddress;
import com.zhubao.b2b.platform.model.Vender;
import com.zhubao.b2b.platform.service.OrderService;
import com.zhubao.b2b.platform.service.PaywayService;
import com.zhubao.b2b.platform.service.ShipAddressService;
import com.zhubao.b2b.platform.service.ShopCartService;
import com.zhubao.b2b.web.utils.UrlUtils;
import com.zhubao.common.utils.Constants;
import com.zhubao.common.utils.Pagination;

@Controller
@RequestMapping(value = "/order")
public class OrderController extends AbstractController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ShopCartService shopCartService;

	@Autowired
	private ShipAddressService shipAddressService;

	@Autowired
	private PaywayService paywayService;

	private final static Map<String, String> orderStatus = OrderStatusMap.getAll();

	@Override
	@RequiresAuthentication
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return my(request, response, model, null, null, null, 0);
	}

	@RequestMapping(value = "/my")
	@RequiresAuthentication
	public String my(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "companyId", required = false) String companyId,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "conditionTime", required = false) String conditionTime,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		return list(request, response, model, companyId, status, conditionTime, page);
	}

	@RequestMapping(value = "/history")
	@RequiresAuthentication
	public String history(HttpServletRequest request, HttpServletResponse response, Model model) {
		return my(request, response, model, null, null, null, 0);
	}

	public String list(HttpServletRequest request, HttpServletResponse response, Model model,
			String companyId, String status, String conditionTime, int page) {
		Customer user = getLoginUser();

		model.addAttribute("companyId", companyId);
		model.addAttribute("companyies", getCompanyies());

		model.addAttribute("status", status);
		assignStatus(model, user.getId());

		model.addAttribute("conditionTime", conditionTime);

		OrderQueryParameter parameter = new OrderQueryParameter();

		parameter.setCustomerId(user.getId());
		parameter.setVenderId(companyId);
		parameter.setStatus(status);
		parameter.setConditionTime(conditionTime);

		ServiceResult<Pagination<Order>> orderResult = orderService.getPaginationOrders(
				user.getId(), parameter, page, Constants.PAGESIZE);

		if (orderResult.isSuccess() == true) {
			Date date = new Date();

			model.addAttribute("currentTime", date.getTime());
			model.addAttribute("date", date);
			model.addAttribute("dateFormat", new SimpleDateFormat("yyyy-MM-dd HH:mm"));

			assisnStatus(model);

			Pagination<Order> pagination = orderResult.getValue();
			model.addAttribute("orders", pagination.getData());

			model.addAttribute("pagination", pagination);
			model.addAttribute("urlUtils", new UrlUtils(request));
			model.addAttribute("pageUrl", getPageBaseUrl(request, ""));
			model.addAttribute("queryString", request.getQueryString());
		}

		return "order/list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> add(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "companyId") String companyId,
			@RequestParam(value = "shopCartGoods[]") List<String> shopCartGoods) {
		Map<String, Object> data = new HashMap<String, Object>();

		if (companyId != null && companyId.length() > 0 && shopCartGoods != null
				&& shopCartGoods.size() > 0) {
			Customer user = getLoginUser();
			List<String> shopcartGoodsIds = new ArrayList<String>();

			for (String e : shopCartGoods) {
				String[] values = e.split("_");
				if (values.length == 2) {
					shopCartService.updateShopCartGoodsAmount(user.getId(), values[0],
							Integer.parseInt(values[1]));
					shopcartGoodsIds.add(values[0]);
				}
			}

			if (shopcartGoodsIds.size() > 0) {
				ServiceResult<List<String>> result = orderService.createOrderFromShopcart(
						user.getId(), shopcartGoodsIds);

				if (result.isSuccess() == true) {
					data.put("state", true);
				} else {
					data.put("state", false);
					data.put("message", result.getErrorMessage());
				}

				return data;
			}
		}

		data.put("state", false);
		data.put("message", "数据异常");

		return data;
	}

	@RequestMapping(value = "/confirmOrder")
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> confirmOrder(HttpServletRequest request, HttpServletResponse response,
			Model model, @RequestParam(value = "orderId") String orderId) {
		Map<String, Object> data = new HashMap<String, Object>();
		Customer user = getLoginUser();

		ServiceResult<Order> result = orderService.confirmOrder(user.getId(), orderId);
		if (result.isSuccess() == true) {
			data.put("state", true);
		} else {
			data.put("state", false);
			data.put("message", result.getErrorMessage());
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

			assisnStatus(model);
			model.addAttribute("r", order);

			String status = order.getStatus();
			boolean isPayed = !(com.zhubao.b2b.common.utils.Constants.ORDER_STATUS_WAIT_WEIGHT
					.equals(status)
					|| com.zhubao.b2b.common.utils.Constants.ORDER_STATUS_WEIGHTING.equals(status) || com.zhubao.b2b.common.utils.Constants.ORDER_STATUS_WEIGHTED
					.equals(status));

			if (isPayed == false
					&& com.zhubao.b2b.common.utils.Constants.ORDER_STATUS_WAIT_WEIGHT
							.equals(status) == false
					&& com.zhubao.b2b.common.utils.Constants.ORDER_STATUS_WEIGHTING.equals(status) == false) {
				List<ShipAddress> addresses = shipAddressService.getAddresses(user.getId());

				/* 如果已更新了订单收货地址，则显示所设置的订单收货地址；否则默认显示联系人中的第一个联系人收货地址 */
				ShipAddress orderShipAddress = order.getAddress();
				if (orderShipAddress != null) {
					model.addAttribute("shipAddressId", orderShipAddress.getId());
				} else if (addresses != null) {
					ShipAddress defaultAddress = addresses.get(0);

					if (defaultAddress != null) {
						model.addAttribute("shipAddressId", defaultAddress.getId());
					}
				}

				model.addAttribute("addresses", addresses);
				model.addAttribute("payways", paywayService.getPayways("0"));
				model.addAttribute("hedging_payway",
						com.zhubao.b2b.common.utils.Constants.PAYWAY_HEDGING_KSHOP);
			}

			model.addAttribute("isPayed", isPayed);

			return "order/detail";
		}

		return null;
	}

	@Override
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable("id") String id) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();

		try {
			Customer user = getLoginUser();
			orderService.cancelOrder(user.getId(), id);

			data.put("state", true);
		} catch (Exception e) {
			data.put("state", false);
			data.put("message", e.getMessage());
		}

		return data;
	}

	@RequestMapping(value = "/deleteProduct/{orderId}/{id}")
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> deleteProduct(HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable("orderId") String orderId, @PathVariable("id") String id) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();

		try {
			Customer user = getLoginUser();
			orderService.deleteOrderItem(user.getId(), orderId, id);

			data.put("state", true);
		} catch (Exception e) {
			data.put("state", false);
			data.put("message", e.getMessage());
		}

		return data;
	}

	@RequestMapping(value = "/stat")
	@RequiresUser
	public @ResponseBody
	Map<String, Object> stat(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		Customer user = getLoginUser(true);

		Map<String, String> statusMap = OrderStatusMap.getAll();
		Map<String, Map<String, Object>> statusList = new LinkedHashMap<String, Map<String, Object>>();
		int orderCount = 0;

		if (user != null) {
			ServiceResult<Map<String, Integer>> statusCountResult = orderService.countByStatus(user
					.getId());
			Map<String, Integer> counts = statusCountResult.getValue();

			for (Map.Entry<String, String> entry : statusMap.entrySet()) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("name", entry.getValue());

				Integer count = 0;

				if (counts != null) {
					if ((count = counts.get(entry.getKey())) == null) {
						count = 0;
					}
				}

				orderCount += count;
				row.put("amount", count);

				statusList.put(entry.getKey(), row);
			}
		}

		data.put("state", true);
		data.put("totals", orderCount);
		data.put("data", statusList);

		return data;
	}

	private void assignStatus(Model model, String userId) {
		Map<String, String> statusMap = OrderStatusMap.getAll();
		Map<String, Map<String, Object>> statusList = new LinkedHashMap<String, Map<String, Object>>();

		ServiceResult<Map<String, Integer>> statusCountResult = orderService.countByStatus(userId);
		Map<String, Integer> counts = statusCountResult.getValue();

		for (Map.Entry<String, String> entry : statusMap.entrySet()) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("name", entry.getValue());

			Integer count = 0;

			if (counts != null) {
				if ((count = counts.get(entry.getKey())) == null) {
					count = 0;
				}
			}

			row.put("amount", count);

			statusList.put(entry.getKey(), row);
			model.addAttribute("order_status_" + entry.getKey().toLowerCase(), entry.getKey());
		}

		model.addAttribute("statusList", statusList);
	}

	private static String getPageBaseUrl(HttpServletRequest request, String categoryId) {
		String queryString = request.getQueryString();

		queryString = queryString != null ? queryString.replaceAll("&?page=\\d*&?", "") + "&page="
				: "page=";

		return request.getPathInfo() + "?" + queryString;
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

	private static void assisnStatus(Model model) {
		model.addAttribute("orderStatus", orderStatus);
		for (Map.Entry<String, String> entry : orderStatus.entrySet()) {
			model.addAttribute("order_status_" + entry.getKey().toLowerCase(), entry.getKey());
		}
	}

}
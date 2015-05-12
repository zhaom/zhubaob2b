package com.zhubao.b2b.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.model.ShopCart;
import com.zhubao.b2b.platform.model.ShopCartGoods;
import com.zhubao.b2b.platform.service.ShopCartService;

@Controller
@RequestMapping(value = "/cart")
public class CartController extends AbstractController {

	@Autowired
	private ShopCartService shopCartService;

	@Override
	@RequiresAuthentication
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Customer user = getLoginUser();

		ShopCart cart = shopCartService.getShopCart(user.getId());

		if (cart != null) {
			model.addAttribute("groups", cart.getCartGoodsGroupItems());
		}

		return "cart/my";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> add(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("shopCartGoods") ShopCartGoods shopCartGoods) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();

		try {
			Customer user = getLoginUser();

			shopCartService.createShopCartGoods(user.getId(), shopCartGoods.getGoodsId(),
					shopCartGoods.getSkuId(), shopCartGoods.getAmount());

			data.put("state", true);
			data.put("totals", shopCartService.getShopCartGoodsAmount(user.getId()));
			data.put("message", "success");
		} catch (Exception e) {
			data.put("state", false);
			data.put("message", e.getMessage());
		}

		return data;
	}

	@Override
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable("id") String id) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();

		try {
			Customer user = getLoginUser();
			shopCartService.deleteShopCart(user.getId());

			data.put("state", true);
		} catch (Exception e) {
			data.put("state", false);
			data.put("message", e.getMessage());
		}

		return null;
	}

	@RequestMapping(value = "/editGoodsAmount/{goodsId}")
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> editProductAmount(HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable("goodsId") String goodsId,
			@RequestParam(value = "amount") int amount) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		Customer user = getLoginUser();

		try {
			shopCartService.updateShopCartGoodsAmount(user.getId(), goodsId, amount);
			data.put("state", true);
		} catch (Exception e) {
			data.put("state", false);
			data.put("message", e.getMessage());
		}

		return data;
	}

	@RequestMapping(value = "/deleteProduct/{id}", method = RequestMethod.DELETE)
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> deleteProduct(HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable("id") String id) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();

		try {
			Customer user = getLoginUser();
			shopCartService.deleteShopCartGoods(user.getId(), id);

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

		data.put("state", true);
		data.put("totals", user == null ? 0 : shopCartService.getShopCartGoodsAmount(user.getId()));

		return data;
	}

}
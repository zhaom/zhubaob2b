package com.zhubao.b2b.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhubao.b2b.platform.model.Goods;
import com.zhubao.b2b.platform.model.GoodsSku;
import com.zhubao.b2b.platform.service.GoodsService;
import com.zhubao.b2b.platform.service.GoodsSkuAttributeService;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends AbstractController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsSkuAttributeService goodsSkuAttributeService;

	@Override
	public String show(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("id") String id) {
		Goods goods = goodsService.getGoods(id);

		if (goods != null) {
			model.addAttribute("page_title", goods.getName());
			model.addAttribute("r", goods);
			model.addAttribute("attributes",
					goodsSkuAttributeService.getSkuAttributes(goods.getStyleId()));
		}

		return "product/show";
	}

	@RequestMapping(value = "/getSku/{id}")
	public @ResponseBody
	Map<String, Object> getSku(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@PathVariable("id") String id,
			@RequestParam(value = "attributeValueIds[]", required = false) List<String> attributeValueIds) {
		Map<String, Object> data = new HashMap<String, Object>();

		if (attributeValueIds == null) {
			attributeValueIds = new ArrayList<String>();
			attributeValueIds.add("0");
		}

		data.put("state", true);
		data.put("sku", goodsService.getGoodsSku(id, attributeValueIds));

		return data;
	}

	@RequestMapping(value = "/getStoreAmount/{id}")
	public @ResponseBody
	Map<String, Object> getStoreAmount(HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable("id") String id, @RequestParam(value = "skuId") String skuId) {
		Map<String, Object> data = new HashMap<String, Object>();

		GoodsSku sku = goodsService.getGoodsSku(id, skuId);

		data.put("state", sku != null);
		data.put("amount", sku == null ? 0 : sku.getCurCount());

		return data;
	}

}
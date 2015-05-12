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
import org.springframework.util.StringUtils;

import com.zhubao.b2b.platform.entry.GoodsQueryParameter;
import com.zhubao.b2b.platform.model.GoodsMaterial;
import com.zhubao.b2b.platform.service.GoodsMaterialService;
import com.zhubao.b2b.platform.service.GoodsService;
import com.zhubao.b2b.platform.service.GoodsStyleService;
import com.zhubao.b2b.platform.service.GoodsUseService;

@Controller
public class HomeController extends AbstractController {

	@Autowired
	private GoodsMaterialService goodsMaterialService;

	@Autowired
	private GoodsStyleService goodsStyleService;

	@Autowired
	private GoodsUseService goodsUseService;

	@Autowired
	private GoodsService goodsService;

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<GoodsMaterial> materials = goodsMaterialService.getMaterials();

		model.addAttribute("materials", goodsMaterialService.getMaterials());
		model.addAttribute("styles", goodsStyleService.getStyles());
		model.addAttribute("series", goodsUseService.getUses());

		List<Object> groups = new ArrayList<Object>();

		if (materials != null) {
			for (int i = 0; i < materials.size(); i++) {
				GoodsMaterial material = materials.get(i);
				if (material == null) {
					continue;
				}

				Map<String, Object> group = new HashMap<String, Object>();

				GoodsQueryParameter parameter = new GoodsQueryParameter();
				parameter.setMaterialId(material.getId());

				group.put("id", material.getId());
				group.put("name", material.getName());
				group.put("alias", material.getAlias());
				group.put("title", StringUtils.capitalize(material.getAlias()) + " Jewelry");
				group.put("products", goodsService.getTopXGoods(parameter, material.getAlias()
						.equals("gold") == true ? 6 : 3));
				groups.add(group);
			}
		}

		model.addAttribute("groups", groups);

		return "index";
	}

}
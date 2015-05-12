package com.zhubao.b2b.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhubao.b2b.platform.entry.GoodsQueryParameter;
import com.zhubao.b2b.platform.model.Goods;
import com.zhubao.b2b.platform.model.GoodsAttribute;
import com.zhubao.b2b.platform.model.GoodsAttributeValue;
import com.zhubao.b2b.platform.model.GoodsMaterial;
import com.zhubao.b2b.platform.model.GoodsStyle;
import com.zhubao.b2b.platform.model.GoodsUse;
import com.zhubao.b2b.platform.service.GoodsAttributeService;
import com.zhubao.b2b.platform.service.GoodsMaterialService;
import com.zhubao.b2b.platform.service.GoodsService;
import com.zhubao.b2b.platform.service.GoodsStyleService;
import com.zhubao.b2b.platform.service.GoodsUseService;
import com.zhubao.b2b.web.utils.UrlUtils;
import com.zhubao.common.utils.Constants;
import com.zhubao.common.utils.Pagination;

@Controller
@RequestMapping(value = "/category")
public class CategoryController extends AbstractController {

	@Autowired
	private GoodsMaterialService goodsMaterialService;

	@Autowired
	private GoodsStyleService goodsStyleService;

	@Autowired
	private GoodsUseService goodsUseService;

	@Autowired
	private GoodsAttributeService goodsAttributeService;

	@Autowired
	private GoodsService goodsService;

	private UrlUtils urlUtils = null;

	@RequestMapping(value = "/")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "styleId", required = false) String styleId,
			@RequestParam(value = "seriesId", required = false) String seriesId) {
		return list(request, response, model, null, null, styleId, seriesId, null, null, 1, null,
				null);
	}

	@RequestMapping(value = "/dingjia.html")
	public String dingjia(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value = "styleId", required = false) String styleId,
			@RequestParam(value = "seriesId", required = false) String seriesId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "orderby", required = false, defaultValue = "addtime") String orderby,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		return list(request, response, model, null, "dingjia", styleId, seriesId, null, null, page,
				orderby, order);
	}

	@RequestMapping(value = "/jijia.html")
	public String jiejia(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value = "styleId", required = false) String styleId,
			@RequestParam(value = "seriesId", required = false) String seriesId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "orderby", required = false, defaultValue = "addtime") String orderby,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		return list(request, response, model, null, "jijia", styleId, seriesId, null, null, page,
				orderby, order);
	}

	@RequestMapping(value = "/{materialId}.html")
	public String list(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@PathVariable(value = "materialId") String materialId,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "styleId", required = false) String styleId,
			@RequestParam(value = "seriesId", required = false) String seriesId,
			@RequestParam(value = "properties", required = false) String properties,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "orderby", required = false, defaultValue = "addtime") String orderby,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		return list(request, response, model, materialId, type, styleId, seriesId, properties,
				null, page, orderby, order);
	}

	@RequestMapping(value = "/{materialId}/{type}.html")
	public String list(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@PathVariable(value = "materialId") String materialId,
			@PathVariable(value = "type") String type,
			@RequestParam(value = "styleId", required = false) String styleId,
			@RequestParam(value = "seriesId", required = false) String seriesId,
			@RequestParam(value = "properties", required = false) String properties,
			@RequestParam(value = "keywords", required = false) String keywords,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "orderby", required = false, defaultValue = "onShelfTime") String orderby,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		model.addAttribute("materialId", materialId);
		model.addAttribute("type", type);
		model.addAttribute("styleId", styleId);
		model.addAttribute("seriesId", seriesId);
		model.addAttribute("keywords", keywords);
		model.addAttribute("orderby", orderby);
		model.addAttribute("order", "asc".equals(order) ? "desc" : "asc");

		if (urlUtils == null) {
			urlUtils = new UrlUtils(request);
		}

		List<String> attributes = null;
		if (properties != null && properties.length() > 0) {
			String[] temp = properties.split(",");

			attributes = new ArrayList<String>();
			for (int i = 0; i < temp.length; i++) {
				if (StringUtils.hasText(temp[i]) == true) {
					attributes.add(temp[i]);
				}
			}
		}

		model.addAttribute("page_title", materialId);
		model.addAttribute("poisitions", getPoisitions(materialId, styleId, seriesId));
		model.addAttribute("materials", geMaterials());
		model.addAttribute("styles", getGoodsStyles());
		model.addAttribute("series", getSeries());
		model.addAttribute("attributes", getAttributes(materialId, styleId, attributes));

		GoodsQueryParameter parameter = new GoodsQueryParameter();
		parameter.setMaterialId(materialId);
		parameter.setStyleId(styleId);
		parameter.setUseId(seriesId);
		parameter.setAttrValueIds(attributes);
		if (StringUtils.hasText(keywords) == true) {
			parameter.setKeywords(keywords);
		}
		if (type != null) {
			parameter.setPriceType("dingjia".equals(type) == true ? 1 : 0);
		}

		parameter.setOrderBy(orderby);
		parameter.setOrder(order);

		Pagination<Goods> pagination = goodsService.getPaginationGoods(parameter, page,
				Constants.PAGESIZE);

		model.addAttribute("totals", pagination.getTotalRecords());
		model.addAttribute("products", pagination.getData());
		model.addAttribute("pagination", pagination);
		model.addAttribute("urlUtils", new UrlUtils(request));
		model.addAttribute("pageUrl", getPageBaseUrl(request));
		model.addAttribute("queryString", request.getQueryString());
		model.addAttribute("currentUrl", getCurrentUrl(request));

		return "category/list";
	}

	@RequestMapping(value = "/search")
	public String search(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value = "materialId", required = false) String materialId,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "styleId", required = false) String styleId,
			@RequestParam(value = "seriesId", required = false) String seriesId,
			@RequestParam(value = "properties", required = false) String properties,
			@RequestParam(value = "keywords", required = false) String keywords,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "orderby", required = false, defaultValue = "addtime") String orderby,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		return list(request, response, model, materialId, type, styleId, seriesId, properties,
				keywords, page, orderby, order);
	}

	private Map<String, String> getPoisitions(String materialId, String styleId, String seriesId) {
		Map<String, String> poisitions = new LinkedHashMap<String, String>();

		if (materialId != null) {
			GoodsMaterial material = goodsMaterialService.getMaterial(materialId);
			if (material != null) {
				poisitions.put(material.getId(), material.getName());
			}
		}

		if (styleId != null) {
			GoodsStyle style = goodsStyleService.getStyle(styleId);
			if (style != null) {
				poisitions.put(style.getId(), style.getName());
			}
		}

		if (seriesId != null) {
			GoodsUse series = goodsUseService.getUse(seriesId);
			if (series != null) {
				poisitions.put(series.getId(), series.getName());
			}
		}

		return poisitions;
	}

	private Map<String, GoodsMaterial> geMaterials() {
		List<GoodsMaterial> materials = goodsMaterialService.getMaterials();

		if (materials == null) {
			return null;
		}

		Map<String, GoodsMaterial> result = new LinkedHashMap<String, GoodsMaterial>();
		for (GoodsMaterial r : materials) {
			result.put(r.getId(), r);
		}

		return result;
	}

	private Map<String, GoodsStyle> getGoodsStyles() {
		List<GoodsStyle> styles = goodsStyleService.getStyles();

		if (styles == null) {
			return null;
		}

		Map<String, GoodsStyle> result = new LinkedHashMap<String, GoodsStyle>();
		for (GoodsStyle r : styles) {
			result.put(r.getId(), r);
		}

		return result;
	}

	private Map<String, GoodsUse> getSeries() {
		List<GoodsUse> series = goodsUseService.getUses();

		if (series == null) {
			return null;
		}

		Map<String, GoodsUse> result = new LinkedHashMap<String, GoodsUse>();
		for (GoodsUse r : series) {
			result.put(r.getId(), r);
		}

		return result;
	}

	private List<Map<String, Object>> getAttributes(String materialId, String styleId,
			List<String> selectedAttributeValues) {
		List<GoodsAttribute> attributes = goodsAttributeService.getAttributes(materialId, styleId);

		if (attributes == null) {
			return null;
		}

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, List<String>> params = null;

		for (GoodsAttribute attribute : attributes) {
			List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
			ArrayList<String> attributeAttributes = selectedAttributeValues == null ? new ArrayList<String>()
					: new ArrayList<String>(selectedAttributeValues);
			boolean isSelected = false;

			List<GoodsAttributeValue> tempValues = attribute.getValues();
			if (tempValues != null) {
				for (GoodsAttributeValue v : tempValues) {
					Map<String, Object> value = new HashMap<String, Object>();
					ArrayList<String> valueAttributes = selectedAttributeValues == null ? new ArrayList<String>()
							: new ArrayList<String>(selectedAttributeValues);
					String valueId = v.getId();
					boolean _isSelected = selectedAttributeValues == null ? false
							: selectedAttributeValues.contains(valueId);

					for (GoodsAttributeValue v1 : tempValues) {
						valueAttributes.remove(v1.getId());
						attributeAttributes.remove(v1.getId());
					}
					valueAttributes.add(valueId);

					value.put("id", valueId);
					value.put("value", v.getValue());

					params = new TreeMap<String, List<String>>();
					List<String> properties = new ArrayList<String>();
					properties.add(parsePropertyValue(valueAttributes));
					params.put("properties", properties);
					params.put("page", null);
					value.put("url", urlUtils.parseUrl(params));
					value.put("isSelected", _isSelected);
					values.add(value);

					if (_isSelected == true) {
						isSelected = true;
					}
				}
			}

			Map<String, Object> attr = new HashMap<String, Object>();
			attr.put("id", attribute.getId());
			attr.put("name", attribute.getName());
			attr.put("url",
					urlUtils.parseUrl("properties", parsePropertyValue(attributeAttributes)));
			attr.put("values", values);

			attr.put("isSelected", isSelected);
			result.add(attr);
		}

		return result;
	}

	private static String parsePropertyValue(List<String> values) {
		String str = "";

		if (values == null) {
			return str;
		}

		int i = 0;
		for (String value : values) {
			if (StringUtils.hasText(value) == true) {
				if (i > 0) {
					str += ",";
				}

				str += value;
				++i;
			}
		}

		return str;
	}

}
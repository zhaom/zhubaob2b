package com.zhubao.b2b.manage.controller;

import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.entity.GoodsPrice;
import com.zhubao.b2b.platform.entry.GoodsQueryParameter;
import com.zhubao.b2b.platform.model.Goods;
import com.zhubao.b2b.platform.model.GoodsSku;
import com.zhubao.b2b.platform.model.Vender;
import com.zhubao.b2b.platform.service.*;
import com.zhubao.common.utils.Pagination;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping(value = "/manage/goods")
public class GoodsController extends BasicController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsSkuAttributeService goodsSkuAttributeService;
    @Autowired
    private GoodsAttributeService goodsAttributeService;
    @Autowired
    private GoodsMaterialService goodsMaterialService;
    @Autowired
    private GoodsStyleService goodsStyleService;
    @Autowired
    private GoodsUseService goodsUseService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/onshelf.do")
    public String listOnshelf(HttpServletRequest request, Model model,
                              @RequestParam(value = "m", required = false, defaultValue = "") String materialId,
                              @RequestParam(value = "s", required = false, defaultValue = "") String styleId,
                              @RequestParam(value = "u", required = false, defaultValue = "") String useId,
                              @RequestParam(value = "k", required = false, defaultValue = "") String keywords,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.debug("request onself list");
        // 查询商品
        GoodsQueryParameter param = new GoodsQueryParameter();
        param.setMaterialId(materialId);
        param.setStyleId(styleId);
        param.setUseId(useId);
        param.setStatus(1);
        param.setKeywords(keywords);

        Vender vender = (Vender) request.getAttribute("vender");
        param.setVenderId(vender.getKshopAgencyId());

        Pagination pagination = goodsService.getPaginationGoods(param, page, pageSize);

        //  填充数据到 model
        model.addAttribute("materials", goodsMaterialService.getMaterials());
        model.addAttribute("styles", goodsStyleService.getStyles());
        model.addAttribute("uses", goodsUseService.getUses());
        model.addAttribute("m", materialId);
        model.addAttribute("s", styleId);
        model.addAttribute("u", useId);
        model.addAttribute("k", keywords);
        model.addAttribute("pagination", pagination);
        return "goods/onshelf";
    }

    @RequestMapping(value = "/offshelf.do")
    public String listOffshelf(HttpServletRequest request, Model model,
                              @RequestParam(value = "m", required = false, defaultValue = "") String materialId,
                              @RequestParam(value = "s", required = false, defaultValue = "") String styleId,
                              @RequestParam(value = "u", required = false, defaultValue = "") String useId,
                              @RequestParam(value = "k", required = false, defaultValue = "") String keywords,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.debug("request offself list");
        // 查询商品
        GoodsQueryParameter param = new GoodsQueryParameter();
        param.setMaterialId(materialId);
        param.setStyleId(styleId);
        param.setUseId(useId);
        param.setStatus(2);
        param.setKeywords(keywords);

        Vender vender = (Vender) request.getAttribute("vender");
        param.setVenderId(vender.getKshopAgencyId());

        Pagination pagination = goodsService.getPaginationGoods(param, page, pageSize);

        //  填充数据到 model
        model.addAttribute("materials", goodsMaterialService.getMaterials());
        model.addAttribute("styles", goodsStyleService.getStyles());
        model.addAttribute("uses", goodsUseService.getUses());
        model.addAttribute("m", materialId);
        model.addAttribute("s", styleId);
        model.addAttribute("u", useId);
        model.addAttribute("k", keywords);
        model.addAttribute("pagination", pagination);
        return "goods/offshelf";
    }

    @RequestMapping(value = "/waitonshelf.do")
    public String listWaitshelf(HttpServletRequest request, Model model,
                               @RequestParam(value = "m", required = false, defaultValue = "") String materialId,
                               @RequestParam(value = "s", required = false, defaultValue = "") String styleId,
                               @RequestParam(value = "u", required = false, defaultValue = "") String useId,
                               @RequestParam(value = "k", required = false, defaultValue = "") String keywords,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.debug("request offself list");
        // 查询商品
        GoodsQueryParameter param = new GoodsQueryParameter();
        param.setMaterialId(materialId);
        param.setStyleId(styleId);
        param.setUseId(useId);
        param.setStatus(0);
        param.setKeywords(keywords);

        Vender vender = (Vender) request.getAttribute("vender");
        param.setVenderId(vender.getKshopAgencyId());

        Pagination pagination = goodsService.getPaginationGoods(param, page, pageSize);

        //  填充数据到 model
        model.addAttribute("materials", goodsMaterialService.getMaterials());
        model.addAttribute("styles", goodsStyleService.getStyles());
        model.addAttribute("uses", goodsUseService.getUses());
        model.addAttribute("m", materialId);
        model.addAttribute("s", styleId);
        model.addAttribute("u", useId);
        model.addAttribute("k", keywords);
        model.addAttribute("pagination", pagination);
        return "goods/waitonshelf";
    }


    @RequestMapping(value = "/delete.do")
    public String delete(HttpServletRequest request, Model model,
                         @RequestParam(value = "goodsId", required = true) String goodsId) {
        Vender vender = (Vender) request.getAttribute("vender");
        goodsService.deleteGoods(vender.getKshopAgencyId(), goodsId);
        model.addAttribute("message", "删除商品成功!");
        return "opt_ok";
    }

    @RequestMapping(value = "/newSelectBasic.do")
    public String newSelectBasic(Model model) {
        model.addAttribute("materials", goodsMaterialService.getMaterials());
        model.addAttribute("styles", goodsStyleService.getStyles());
        return "goods/new_select_basic";
    }

    @RequestMapping(value = "/newSelectDetail.do")
    public String newSelectDetail(HttpServletRequest request,
                                  Model model,
                                  @RequestParam(value = "materialId", required = true) String materialId,
                                  @RequestParam(value = "styleId", required = true) String styleId) {
        Goods goods = new Goods();
        goods.setMaterialId(materialId);
        goods.setStyleId(styleId);
        model.addAttribute("goods", goods);
        model.addAttribute("attributes", goodsAttributeService.getAttributes(materialId, styleId));
        model.addAttribute("skuAttributes", goodsSkuAttributeService.getSkuAttributes(styleId));
        model.addAttribute("uses", goodsUseService.getUses());
        return "goods/new_select_detail";
    }

    @RequestMapping(value = "/newSave.do")
    public String newSave(HttpServletRequest request, Model model, Goods goods) {
        if (goods != null) {
            goods.setId(IdFactory.generateId());
            goods.setStatus(0);


            // 供货商
            Vender vender = (Vender) request.getAttribute("vender");
            goods.setVenderId(vender.getKshopAgencyId());

            // sku  参数
            goods.setSkus(new ArrayList<GoodsSku>());
            Map<String, String[]> paramMap = request.getParameterMap();
            GoodsSku sku = null;
            for (String paramKey : paramMap.keySet()) {
                if (StringUtils.startsWith(paramKey, "skuSel") && !StringUtils.endsWith(paramKey, "Count")) {
                    sku = new GoodsSku();
                    sku.setId(IdFactory.generateId());
                    sku.setGoodsId(goods.getId());
                    sku.setSkuAttrValueIds(Arrays.asList(paramMap.get(paramKey)));
                    sku.setCurCount(Integer.parseInt(paramMap.get(paramKey + "Count")[0]));
                    sku.setDefaultSku(false);
                    goods.getSkus().add(sku);
                }
            }
            if (goods.getSkus().size() == 0) {
                sku = new GoodsSku();
                sku.setId(IdFactory.generateId());
                sku.setGoodsId(goods.getId());
                sku.setCurCount(Integer.parseInt(paramMap.get("defSkuCount")[0]));
                sku.setSkuAttrValueIds(Arrays.asList(new String[]{"0"}));
                sku.setDefaultSku(true);
                goods.getSkus().add(sku);
            }

            // 价格
            goods.setPrices(new ArrayList<GoodsPrice>());
            GoodsPrice price = null;
            if (compareParameterValue(request, "priceCondition1", "1")) {
                price = new GoodsPrice();
                price.setType(Constants.GOODS_PRICE_TYPE_FIXED_PRICE);
                price.setDesc("一口价商品");
                price.setPrice(getFloatParameter(request, "priceInput1"));
                goods.getPrices().add(price);
                goods.setIsFixedPrice(1);
                goods.setPrice(price.getPrice());
            } else {
                if (compareParameterValue(request, "priceCondition2", "1")) {
                    price = new GoodsPrice();
                    price.setType(Constants.GOODS_PRICE_TYPE_FIXED_MATERIALFEE);
                    price.setDesc("贵金属材料一口价");
                    price.setPrice(getFloatParameter(request, "priceInput2"));
                    goods.getPrices().add(price);
                } else {
                    price = new GoodsPrice();
                    price.setType(Constants.GOODS_PRICE_TYPE_VALUATED_MATERIALFEE);
                    price.setDesc("贵金属材料称重计价");
                    price.setPrice(0f);
                    goods.getPrices().add(price);
                }

                if (compareParameterValue(request, "priceCondition3", "1")) {
                    price = new GoodsPrice();
                    price.setType(Constants.GOODS_PRICE_TYPE_FIXED_MATERIALFEE);
                    price.setDesc("非贵金属材料价格");
                    price.setPrice(getFloatParameter(request, "priceInput3"));
                    goods.getPrices().add(price);
                }

                if (compareParameterValue(request, "priceCondition4", "1")) {
                    price = new GoodsPrice();
                    price.setType(Constants.GOODS_PRICE_TYPE_FIXED_MANUALFEE);
                    price.setDesc("手工费一口价");
                    price.setPrice(getFloatParameter(request, "priceInput4"));
                    goods.getPrices().add(price);
                } else {
                    price = new GoodsPrice();
                    price.setType(Constants.GOODS_PRICE_TYPE_VALUATED_MANUALFEE);
                    price.setDesc("手工费称重计价");
                    price.setPrice(0f);
                    goods.getPrices().add(price);
                }
            }

            goodsService.createGoods(goods);
        }
        model.addAttribute("message", "保存商品成功!");
        return "goods/opt_ok";
    }

    @RequestMapping(value = "/edit.do")
    public String edit(HttpServletRequest request, Model model,
                       @RequestParam(value = "goodsId", required = true, defaultValue = "") String goodsId) {
        Vender vender = (Vender) request.getAttribute("vender");
        Goods goods = goodsService.getGoods(vender.getKshopAgencyId(), goodsId);
        if (goods != null) {
            model.addAttribute("goods", goods);
            model.addAttribute("attributes", goodsAttributeService.getAttributes(goods.getMaterialId(), goods.getStyleId()));
            model.addAttribute("skuAttributes", goodsSkuAttributeService.getSkuAttributes(goods.getStyleId()));
            model.addAttribute("uses", goodsUseService.getUses());
            return "goods/edit";
        }
        return "error/error";
    }

    @RequestMapping(value = "/editSave.do")
    public String editSave(HttpServletRequest request, Model model, Goods goods) {
        // 供货商
        Vender vender = (Vender) request.getAttribute("vender");
        if (goods != null && vender.getKshopAgencyId().equals(goods.getVenderId())) {
            // sku  参数
            goods.setSkus(new ArrayList<GoodsSku>());
            Map<String, String[]> paramMap = request.getParameterMap();
            GoodsSku sku = null;
            for (String paramKey : paramMap.keySet()) {
                if (StringUtils.startsWith(paramKey, "skuSel") && !StringUtils.endsWith(paramKey, "Count")) {
                    sku = new GoodsSku();
                    sku.setId(IdFactory.generateId());
                    sku.setGoodsId(goods.getId());
                    sku.setSkuAttrValueIds(Arrays.asList(paramMap.get(paramKey)));
                    sku.setCurCount(Integer.parseInt(paramMap.get(paramKey + "Count")[0]));
                    sku.setDefaultSku(false);
                    goods.getSkus().add(sku);
                }
            }
            if (goods.getSkus().size() == 0) {
                sku = new GoodsSku();
                sku.setId(IdFactory.generateId());
                sku.setGoodsId(goods.getId());
                sku.setCurCount(Integer.parseInt(paramMap.get("defSkuCount")[0]));
                sku.setSkuAttrValueIds(Arrays.asList(new String[]{"0"}));
                sku.setDefaultSku(true);
                goods.getSkus().add(sku);
            }

            // 价格
            goods.setPrices(new ArrayList<GoodsPrice>());
            GoodsPrice price = null;
            if (compareParameterValue(request, "priceCondition1", "1")) {
                price = new GoodsPrice();
                price.setType(Constants.GOODS_PRICE_TYPE_FIXED_PRICE);
                price.setDesc("一口价商品");
                price.setPrice(getFloatParameter(request, "priceInput1"));
                goods.getPrices().add(price);
                goods.setIsFixedPrice(1);
                goods.setPrice(price.getPrice());
            } else {
                if (compareParameterValue(request, "priceCondition2", "1")) {
                    price = new GoodsPrice();
                    price.setType(Constants.GOODS_PRICE_TYPE_FIXED_MATERIALFEE);
                    price.setDesc("贵金属材料一口价");
                    price.setPrice(getFloatParameter(request, "priceInput2"));
                    goods.getPrices().add(price);
                } else {
                    price = new GoodsPrice();
                    price.setType(Constants.GOODS_PRICE_TYPE_VALUATED_MATERIALFEE);
                    price.setDesc("贵金属材料称重计价");
                    price.setPrice(0f);
                    goods.getPrices().add(price);
                }

                if (compareParameterValue(request, "priceCondition3", "1")) {
                    price = new GoodsPrice();
                    price.setType(Constants.GOODS_PRICE_TYPE_FIXED_MATERIALFEE);
                    price.setDesc("非贵金属材料价格");
                    price.setPrice(getFloatParameter(request, "priceInput3"));
                    goods.getPrices().add(price);
                }

                if (compareParameterValue(request, "priceCondition4", "1")) {
                    price = new GoodsPrice();
                    price.setType(Constants.GOODS_PRICE_TYPE_FIXED_MANUALFEE);
                    price.setDesc("手工费一口价");
                    price.setPrice(getFloatParameter(request, "priceInput4"));
                    goods.getPrices().add(price);
                } else {
                    price = new GoodsPrice();
                    price.setType(Constants.GOODS_PRICE_TYPE_VALUATED_MANUALFEE);
                    price.setDesc("手工费称重计价");
                    price.setPrice(0f);
                    goods.getPrices().add(price);
                }
            }

            goodsService.updateGoods(goods);
        }
        model.addAttribute("message", "保存商品成功!");
        return "goods/opt_ok";
    }

    @RequestMapping(value = "/doOnShelf.do")
    public String doOnShelf(HttpServletRequest request, Model model,
                        @RequestParam(value = "goodsId", required = true)String goodsId){
        logger.debug("request goods:[{}] do onshelf", goodsId);
        Vender vender = (Vender)request.getAttribute("vender");
        Goods goods = goodsService.doOnShelf(vender.getKshopAgencyId(),goodsId);
        model.addAttribute("message", "商品上架成功!");
        return "opt_ok";
    }

    @RequestMapping(value = "/doOffShelf.do")
    public String doOffShelf(HttpServletRequest request, Model model,
                             @RequestParam(value = "goodsId", required = true)String goodsId){
        logger.debug("request goods:[{}] do offshelf", goodsId);
        Vender vender = (Vender)request.getAttribute("vender");
        Goods goods = goodsService.doOffShelf(vender.getKshopAgencyId(),goodsId);
        model.addAttribute("message","商品下架成功!");
        return "opt_ok";
    }

    @RequestMapping(value = "/cate.do")
    public String getCateInfo(HttpServletRequest request, Model model){
        logger.debug("request cate info");
        return "goods/cate";
    }


}

package com.zhubao.b2b.api.resource;

import com.zhubao.b2b.common.utils.Symbols;
import com.zhubao.b2b.platform.entry.GoodsQueryParameter;
import com.zhubao.b2b.platform.model.Goods;
import com.zhubao.b2b.platform.model.GoodsSku;
import com.zhubao.b2b.platform.service.GoodsService;
import com.zhubao.common.utils.Pagination;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/api")
public class GoodsResource extends BasicResourceSupport {

    private GoodsService goodsService;

    @GET
    @Path("/0/goods/item/{goodsId}")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response getGoods(@PathParam("goodsId") String goodsId) {
        if (logger.isDebugEnabled()) {
            logger.debug("get goods: {}", new Object[]{goodsId});
        }
        Goods goods = goodsService.getGoods(goodsId);
        if (goods != null) {
            return Response.ok(goods).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/0/goods/topx-list")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response listTopXGoods(@QueryParam("venderId") String venderId, @QueryParam("materialId") String materialId, @QueryParam("styleId") String styleId, @QueryParam("useId") String useId, @QueryParam("size") int size) {
        if (logger.isDebugEnabled()) {
            logger.debug("list topX goods: vender:{}, materialId:{}, styleId:{}, useId:{}, size:{}", new Object[]{venderId, materialId, styleId, useId, size});
        }
        GoodsQueryParameter param = new GoodsQueryParameter();
        param.setMaterialId(materialId);
        param.setVenderId(venderId);
        param.setStyleId(styleId);
        param.setUseId(useId);
        List<Goods> goods = goodsService.getTopXGoods(param, size);
        return Response.ok(goods).build();
    }

    @GET
    @Path("/0/goods/list")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response listPaginationGoods(@QueryParam("venderId") String venderId, @QueryParam("materialId") String materialId, @QueryParam("styleId") String styleId, @QueryParam("useId") String useId, @QueryParam("attrValueIds") String attrValueIds, @QueryParam("page") int page, @QueryParam("pageSize") int pageSize, @QueryParam("orderby")String orderby, @QueryParam("order")String order) {
        if (logger.isDebugEnabled()) {
            logger.debug("list goods: vender:{}, materialId:{}, styleId:{}, useId:{}, attrValueIds:{}, page:{}, pageSize:{}", new Object[]{venderId, materialId, styleId, useId, attrValueIds, page, pageSize});
        }
        GoodsQueryParameter param = new GoodsQueryParameter();
        param.setMaterialId(materialId);
        param.setVenderId(venderId);
        param.setStyleId(styleId);
        param.setUseId(useId);
        param.setOrder(order);
        param.setOrderBy(orderby);

        if (attrValueIds != null) {
            param.setAttrValueIds(Arrays.asList(StringUtils.split(attrValueIds, ':')));
        }
        Pagination pagination = goodsService.getPaginationGoods(param, page, pageSize);
        return Response.ok(pagination).build();
    }

    @GET
    @Path("/0/goods/sku-id")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response getGoodsSkuBySkuId(@QueryParam("goodsId") String goodsId, @QueryParam("skuId") String skuId) {
        if (logger.isDebugEnabled()) {
            logger.debug("get goods sku: goodsId:{}, skuId:{}", new Object[]{goodsId, skuId});
        }
        GoodsSku sku = goodsService.getGoodsSku(goodsId, skuId);
        if (sku != null) {
            return Response.ok(sku).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/0/goods/sku-attr-values")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response getGoodsSkuByAttrValueIds(@QueryParam("goodsId") String goodsId, @QueryParam("attrValueIds") String attrValueIds) {
        if (logger.isDebugEnabled()) {
            logger.debug("get goods sku: goodsId:{}, attrValueIds:{}", new Object[]{goodsId, attrValueIds});
        }
        GoodsSku sku = goodsService.getGoodsSku(goodsId, Arrays.asList(attrValueIds.split(",")));
        if (sku != null) {
            return Response.ok(sku).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

}

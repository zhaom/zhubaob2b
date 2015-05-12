package com.zhubao.b2b.api.resource;

import com.zhubao.b2b.common.utils.Symbols;
import com.zhubao.b2b.platform.model.*;
import com.zhubao.b2b.platform.service.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午4:42
 */
@Path("/api")
public class GoodsCommonResource extends BasicResourceSupport {

    private GoodsMaterialService goodsMaterialService;
    private GoodsStyleService goodsStyleService;
    private GoodsUseService goodsUseService;
    private GoodsAttributeService goodsAttributeService;
    private GoodsSkuAttributeService goodsSkuAttributeService;

    @GET
    @Path("/0/goods/material/list")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response listMaterials() {
        if (logger.isDebugEnabled()) {
            logger.debug("list materials...");
        }
        List<GoodsMaterial> materials = goodsMaterialService.getMaterials();
        return Response.ok(materials).build();
    }

    @GET
    @Path("/0/goods/style/list")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response listStyles() {
        if (logger.isDebugEnabled()) {
            logger.debug("list styles...");
        }
        List<GoodsStyle> styles = goodsStyleService.getStyles();
        return Response.ok(styles).build();
    }

    @GET
    @Path("/0/goods/use/list")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response listUses() {
        if (logger.isDebugEnabled()) {
            logger.debug("list uses...");
        }
        List<GoodsUse> uses = goodsUseService.getUses();
        return Response.ok(uses).build();
    }

    @GET
    @Path("/0/goods/attribute/list")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response listAttributes(@QueryParam("materialId") String materialId, @QueryParam("styleId") String styleId) {
        if (logger.isDebugEnabled()) {
            logger.debug("list attributes...");
        }
        List<GoodsAttribute> attributes = goodsAttributeService.getAttributes(materialId, styleId);
        return Response.ok(attributes).build();
    }

    @GET
    @Path("/0/goods/skuattribute/list")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response listSkuAttributes(@QueryParam("styleId") String styleId) {
        if (logger.isDebugEnabled()) {
            logger.debug("list sku attributes...");
        }
        List<GoodsSkuAttribute> skuAttributes = goodsSkuAttributeService.getSkuAttributes(styleId);
        return Response.ok(skuAttributes).build();
    }

    public void setGoodsMaterialService(GoodsMaterialService goodsMaterialService) {
        this.goodsMaterialService = goodsMaterialService;
    }

    public void setGoodsStyleService(GoodsStyleService goodsStyleService) {
        this.goodsStyleService = goodsStyleService;
    }

    public void setGoodsUseService(GoodsUseService goodsUseService) {
        this.goodsUseService = goodsUseService;
    }

    public void setGoodsAttributeService(GoodsAttributeService goodsAttributeService) {
        this.goodsAttributeService = goodsAttributeService;
    }

    public void setGoodsSkuAttributeService(GoodsSkuAttributeService goodsSkuAttributeService) {
        this.goodsSkuAttributeService = goodsSkuAttributeService;
    }
}

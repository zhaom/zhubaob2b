package com.zhubao.b2b.api.resource;

import com.zhubao.b2b.common.utils.Symbols;
import com.zhubao.b2b.platform.model.ShopCart;
import com.zhubao.b2b.platform.service.ShopCartService;
import org.apache.commons.httpclient.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api")
public class ShopCartResource extends BasicResourceSupport {

    private ShopCartService shopCartService;

    @GET
    @Path("/1/order/shopcart/item")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response getShopCart(@HeaderParam("uid") String userId) {
        if (logger.isDebugEnabled()) {
            logger.debug("[{}] query shopcart...", userId);
        }
        ShopCart cart = shopCartService.getShopCart(userId);
        if (cart != null) {
            return Response.ok(cart).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/1/order/shopcart/amount")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response getShopCartGoodsAmount(@HeaderParam("uid") String userId) {
        if (logger.isDebugEnabled()) {
            logger.debug("[{}] query shopcart goods amount...", userId);
        }
        Integer count = shopCartService.getShopCartGoodsAmount(userId);
        return Response.ok(count).build();
    }

    @DELETE
    @Path("/1/order/shopcart/delete")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response clearShopCart(@HeaderParam("uid") String userId) {
        if (logger.isDebugEnabled()) {
            logger.debug("[{}] clear shopcart...", userId);
        }
        shopCartService.deleteShopCart(userId);
        return Response.ok().build();
    }

    @POST
    @Path("/1/order/shopcart/add-goods")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response addGoodsToShopCart(@HeaderParam("uid") String userId, @FormParam("goodsId") String goodsId, @FormParam("skuId") String skuId, @FormParam("amount") int amount) {
        if (logger.isDebugEnabled()) {
            logger.debug("[{}] add [{}, {}] to shopcart...", new Object[]{userId, goodsId, skuId});
        }
        shopCartService.createShopCartGoods(userId, goodsId, skuId, amount);
        return Response.ok().build();
    }

    @POST
    @Path("/1/order/shopcart/remove-goods")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response removeGoodsFromShopCart(@HeaderParam("uid") String userId, @FormParam("shopCartGoodsId") String shopCartGoodsId) {
        if (logger.isDebugEnabled()) {
            logger.debug("[{}] remove [{}] from shopcart...", new Object[]{userId, shopCartGoodsId});
        }
        shopCartService.deleteShopCartGoods(userId, shopCartGoodsId);
        return Response.ok().build();
    }

    @POST
    @Path("/1/order/shopcart/modify-goods-amount")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response modifyShopCartGoodsAmount(@HeaderParam("uid") String userId, @FormParam("shopCartGoodsId") String shopCartGoodsId, @FormParam("amount") int amount) {
        if (logger.isDebugEnabled()) {
            logger.debug("[{}] modify goods [{}] amount [{}] from shopcart...", new Object[]{userId, shopCartGoodsId, amount});
        }
        shopCartService.updateShopCartGoodsAmount(userId, shopCartGoodsId, amount);
        return Response.ok().build();
    }

    public void setShopCartService(ShopCartService shopCartService) {
        this.shopCartService = shopCartService;
    }
}

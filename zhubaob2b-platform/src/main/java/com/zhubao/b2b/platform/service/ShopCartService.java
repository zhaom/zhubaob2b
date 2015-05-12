package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.platform.model.ShopCart;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 下午5:14
 */
public interface ShopCartService {

    public ShopCart getShopCart(String customerId);

    public void deleteShopCart(String customerId);

    public void createShopCartGoods(String customerId, String goodsId, String skuId, int amount);

    public void deleteShopCartGoods(String customerId, String shopCartGoodsId);

    public void updateShopCartGoodsAmount(String customerId, String shopCartGoodsId, int amount);

    public Integer getShopCartGoodsAmount(String customerId);

    public float getShopCartTotalPrice(String customerId);
}

package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.ShopCartGoods;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-14
 * Time: 上午10:13
 */
public interface ShopCartGoodsDAO {

    public void insert(ShopCartGoods cartGoods);

    public void deleteByShopCartId(String shopCartId);

    public void delete(String shopCartId, String shopCartGoodsId);

    public void updateAmount(String shopCartId, String shopCartGoodsId, int amount);

    public List<ShopCartGoods> selectListByShopCartId(String shopCartId);

    public ShopCartGoods select(String shopCartId, String shopCartGoodsId);

    public ShopCartGoods select(String shopCartId, String goodsId, String skuId);

}

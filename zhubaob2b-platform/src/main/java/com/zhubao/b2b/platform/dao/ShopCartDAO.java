package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.ShopCart;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 下午5:08
 */
public interface ShopCartDAO {

    public void insert(ShopCart cart);

    public ShopCart select(String customerId);

    public void delete(String customerId);
}

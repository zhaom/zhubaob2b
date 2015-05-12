package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.query.Query;
import com.zhubao.b2b.platform.dao.ShopCartDAO;
import com.zhubao.b2b.platform.model.ShopCart;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 下午5:11
 */
public class ShopCartDAOImpl extends BasicDAOSupport implements ShopCartDAO {

    @Override
    public void insert(ShopCart cart) {
        getDatastore().save(cart);
    }

    @Override
    public ShopCart select(String customerId) {
        return getDatastore().find(ShopCart.class).field("customerId").equal(customerId).get();
    }

    @Override
    public void delete(String customerId) {
        Query<ShopCart> query = getDatastore().createQuery(ShopCart.class).field("customerId").equal(customerId);
        getDatastore().delete(query);
    }
}

package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.OrderGoodsDAO;
import com.zhubao.b2b.platform.model.OrderGoods;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class OrderGoodsDAOImpl extends BasicDAOSupport implements OrderGoodsDAO {
    @Override
    public void insert(OrderGoods orderGoods) {
        getDatastore().save(orderGoods);
    }

    @Override
    public OrderGoods getById(String id) {
        return getDatastore().get(OrderGoods.class, id);
    }

    @Override
    public OrderGoods getByOrderAndGoods(String orderId, String goodsId, String goodsSkuId) {
        return getDatastore().find(OrderGoods.class).field("orderId").equal(orderId).field("goodsId").equal(goodsId).field("goodsSkuId").equal(goodsSkuId).get();
    }

    @Override
    public List<OrderGoods> getByOrderId(String orderId) {
        return getDatastore().find(OrderGoods.class).field("orderId").equal(orderId).asList();
    }

    @Override
    public void deleteById(String id) {
        getDatastore().delete(OrderGoods.class, id);
    }

    @Override
    public void deleteByOrderAndGoods(String orderId, String goodsId, String goodsSkuId) {
        getDatastore().delete(getDatastore().find(OrderGoods.class).field("orderId").equal(orderId).field("goodsId").equal(goodsId).field("goodsSkuId").equal(goodsSkuId));
    }
}

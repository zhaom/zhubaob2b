package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.OrderGoods;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-15
 */
public interface OrderGoodsDAO {

    public void insert(OrderGoods orderGoods);

    public OrderGoods getById(String id);

    public OrderGoods getByOrderAndGoods(String orderId, String goodsId, String goodsSkuId);

    public List<OrderGoods> getByOrderId(String orderId);

    public void deleteById(String id);

    public void deleteByOrderAndGoods(String orderId, String goodsId, String goodsSkuId);
}

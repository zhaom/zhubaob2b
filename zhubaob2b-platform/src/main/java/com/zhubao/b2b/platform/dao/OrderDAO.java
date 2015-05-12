package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.entry.QueryParameter;
import com.zhubao.b2b.platform.model.Order;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 上午11:36
 */
public interface OrderDAO {

    public void insert(Order order);

    public List<Order> selectListByCustomerUserId(String userId);

    public List<Order> selectPageList(QueryParameter<Order> param, int start, int range);

    public Integer count(QueryParameter<Order> param);

    public Order select(String orderId);

    public void update(Order order);

    public List<Order> selectListByVenderId(String venderId);

    public List<Order> selectListByVenderIdPayTime(String venderId, long beginTime, long endTime);
}

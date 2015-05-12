package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.OrderStatusDAO;
import com.zhubao.b2b.platform.model.OrderStatus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class OrderStatusDAOImpl extends BasicDAOSupport implements OrderStatusDAO {

    @Override
    public String insert(OrderStatus orderStatus) {
        getDatastore().save(orderStatus);
        return orderStatus.getId();
    }

    @Override
    public List<OrderStatus> listByOrderId(String orderId) {
        return getDatastore().find(OrderStatus.class).field("orderId").equal(orderId).asList();
    }

}

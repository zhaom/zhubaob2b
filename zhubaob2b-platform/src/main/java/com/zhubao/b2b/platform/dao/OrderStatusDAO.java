package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.OrderStatus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface OrderStatusDAO {

    String insert(OrderStatus orderStatus);

    List<OrderStatus> listByOrderId(String orderId);
}

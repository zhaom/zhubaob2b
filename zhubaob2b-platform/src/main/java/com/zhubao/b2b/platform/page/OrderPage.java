package com.zhubao.b2b.platform.page;

import com.zhubao.b2b.platform.model.Order;
import com.zhubao.common.utils.Pagination;

import java.io.Serializable;
import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-25
 * Time: 上午11:49
 */
public class OrderPage implements Serializable {
    private List<Order> orders;
    private Pagination<Order> pagination;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Pagination<Order> getPagination() {
        return pagination;
    }

    public void setPagination(Pagination<Order> pagination) {
        this.pagination = pagination;
    }
}

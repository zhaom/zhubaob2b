package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.model.SettleAccountItem;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-2-27
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
public interface SettleService {

    public SettleAccountItem doSettle(String settleId, Order order);
}

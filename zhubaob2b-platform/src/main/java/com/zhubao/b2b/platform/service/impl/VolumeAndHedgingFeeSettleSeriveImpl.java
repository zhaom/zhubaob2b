package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.model.SettleAccountItem;
import com.zhubao.b2b.platform.service.SettleService;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
public class VolumeAndHedgingFeeSettleSeriveImpl implements SettleService {


    @Override
    public SettleAccountItem doSettle(String settleId, Order order) {
        SettleAccountItem settleAccountItem = new SettleAccountItem();
        settleAccountItem.setId(order.getId());
        settleAccountItem.setSettleId(settleId);
        settleAccountItem.setPayTime(order.getPayTime());
        settleAccountItem.setOrderAmount(order.getOrderSumPrice());
        settleAccountItem.setOrderStatus(order.getStatus());
        settleAccountItem.setOrderStatusDesc(order.getStatusInfo().getDesc());
        float tempComm = order.getOrderSumPrice() * 0.004f;
        settleAccountItem.setCommission((float)(Math.round(tempComm * 100)) / 100);
        if(order.getPayway() != null && order.getPayway().getType().equalsIgnoreCase(Constants.ORDER_PAYWAY_TYPE_HEDGING)){
            settleAccountItem.setServiceFee(10.00f);
        } else {
            settleAccountItem.setServiceFee(0.00f);
        }
        return settleAccountItem;
    }
}

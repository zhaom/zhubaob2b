package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.dao.BalanceOrderFlowDAO;
import com.zhubao.b2b.platform.model.BalanceOrderFlow;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */

public class BalanceOrderFlowDAOImpl extends BasicDAOSupport implements BalanceOrderFlowDAO {

    @Override
    public void insert(BalanceOrderFlow balanceOrderFlow) {
        getDatastore().save(balanceOrderFlow);
    }

    @Override
    public List<BalanceOrderFlow> listByOrderId(String orderId) {
        return getDatastore().find(BalanceOrderFlow.class).field("orderId").equal(orderId).field("status").equal(Constants.BALANCE_ORDER_STATUS_ENDED).order("-createdDate").asList();
    }
}

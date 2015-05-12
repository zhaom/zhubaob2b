package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.BalanceOrderFlow;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface BalanceOrderFlowDAO {

    public void insert(BalanceOrderFlow balanceOrderFlow);

    public List<BalanceOrderFlow> listByOrderId(String orderId);
}

package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.DemandOrderFlow;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface DemandOrderFlowDAO {

    public DemandOrderFlow insert(DemandOrderFlow demandOrderFlow);

    public List<DemandOrderFlow> getByOrderId(String orderId);
}

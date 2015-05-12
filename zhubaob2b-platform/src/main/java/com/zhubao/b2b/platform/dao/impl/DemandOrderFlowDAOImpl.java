package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.DemandOrderFlowDAO;
import com.zhubao.b2b.platform.model.DemandOrderFlow;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class DemandOrderFlowDAOImpl extends BasicDAOSupport implements DemandOrderFlowDAO {
    @Override
    public DemandOrderFlow insert(DemandOrderFlow demandOrderFlow) {
        getDatastore().save(demandOrderFlow);
        return demandOrderFlow;
    }

    @Override
    public List<DemandOrderFlow> getByOrderId(String orderId) {
        return getDatastore().find(DemandOrderFlow.class).field("orderId").equal(orderId).order("-createdDate").asList();
    }
}

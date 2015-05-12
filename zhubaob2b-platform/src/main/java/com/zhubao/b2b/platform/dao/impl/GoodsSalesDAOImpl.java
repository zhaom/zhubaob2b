package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.GoodsSalesDAO;
import com.zhubao.b2b.platform.model.GoodsSales;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class GoodsSalesDAOImpl extends BasicDAOSupport implements GoodsSalesDAO{
    @Override
    public GoodsSales insert(GoodsSales goodsSales) {
        getDatastore().save(goodsSales);
        return goodsSales;
    }

    @Override
    public GoodsSales getById(String id) {
        return getDatastore().get(GoodsSales.class, id);
    }

    @Override
    public List<GoodsSales> getByOrderId(String orderId) {
        return getDatastore().find(GoodsSales.class).field("orderId").equal(orderId).order("-createdDate").asList();
    }
}

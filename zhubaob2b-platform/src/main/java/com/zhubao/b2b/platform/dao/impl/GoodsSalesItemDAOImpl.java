package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.GoodsSalesItemDAO;
import com.zhubao.b2b.platform.model.GoodsSalesItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class GoodsSalesItemDAOImpl extends BasicDAOSupport implements GoodsSalesItemDAO {
    @Override
    public GoodsSalesItem insert(GoodsSalesItem goodsSalesItem) {
        getDatastore().save(goodsSalesItem);
        return goodsSalesItem;
    }

    @Override
    public List<GoodsSalesItem> getBySalesId(String salesId) {
        return getDatastore().find(GoodsSalesItem.class).field("salesId").equal(salesId).asList();
    }
}

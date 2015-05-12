package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.query.Query;
import com.zhubao.b2b.platform.dao.SettleAccountItemDAO;
import com.zhubao.b2b.platform.model.SettleAccountItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-2-27
 * Time: 下午12:03
 * To change this template use File | Settings | File Templates.
 */
public class SettleAccountItemDaoImpl extends BasicDAOSupport implements SettleAccountItemDAO {
    @Override
    public void insert(SettleAccountItem settleAccountItem) {
        getDatastore().save(settleAccountItem);
    }

    @Override
    public List<SettleAccountItem> listBySettleAccountId(String settleAmountId) {
        return getDatastore().find(SettleAccountItem.class).field("settleId").equal(settleAmountId).asList();
    }

    @Override
    public void removeBySettleId(String id) {
        Query<SettleAccountItem> query = getDatastore().createQuery(SettleAccountItem.class).filter("settleId", id);
        getDatastore().delete(query);
    }

}

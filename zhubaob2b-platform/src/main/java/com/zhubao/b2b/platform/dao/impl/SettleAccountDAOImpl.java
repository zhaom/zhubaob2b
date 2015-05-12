package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.query.Query;
import com.zhubao.b2b.platform.dao.SettleAccountDAO;
import com.zhubao.b2b.platform.entry.QueryParameter;
import com.zhubao.b2b.platform.model.SettleAccount;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-2-27
 * Time: 下午12:05
 * To change this template use File | Settings | File Templates.
 */
public class SettleAccountDAOImpl extends BasicDAOSupport implements SettleAccountDAO {
    @Override
    public void insert(SettleAccount settleAccount) {
        getDatastore().save(settleAccount);
    }

    @Override
    public long count(QueryParameter<SettleAccount> queryParameter) {
        return getDatastore().getCount(queryParameter.buildQuery(getDatastore()));
    }

    @Override
    public List<SettleAccount> select(QueryParameter<SettleAccount> queryParameter, int start, int range) {
        Query<SettleAccount> settleAccountQuery = queryParameter.buildQuery(getDatastore());
        return settleAccountQuery.order(queryParameter.getOrderBy()).offset(start).limit(range).asList();
    }


    @Override
    public SettleAccount getById(String id) {
        return getDatastore().get(SettleAccount.class, id);
    }

    @Override
    public SettleAccount getLastSettleAccount(String venderId) {
        return getDatastore().find(SettleAccount.class).field("venderId").equal(venderId).order("-endTime").get();
    }

    @Override
    public SettleAccount getByVenderTime(String venderId, long beginTime, long endTime) {
        return getDatastore().find(SettleAccount.class).field("venderId").equal(venderId).field("beginTime").equal(beginTime).field("endTime").equal(endTime).get();
    }

    @Override
    public void remove(SettleAccount settleAccount1) {
       getDatastore().delete(settleAccount1);
    }
}

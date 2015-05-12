package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.query.Query;
import com.zhubao.b2b.platform.dao.LoginStatDAO;
import com.zhubao.b2b.platform.model.LoginStat;

/**
 * User: xiaoping lu
 * Date: 13-8-19
 * Time: 上午11:43
 */
public class LoginStatDAOImpl extends BasicDAOSupport implements LoginStatDAO {

    @Override
    public void insert(LoginStat stat) {
        getDatastore().save(stat);
    }

    @Override
    public void delete(String userId) {
        Query<LoginStat> query = getDatastore().createQuery(LoginStat.class).field("userId").equal(userId);
        getDatastore().delete(query);
    }

    @Override
    public LoginStat select(String userId) {
        return getDatastore().find(LoginStat.class).field("userId").equal(userId).get();
    }
}

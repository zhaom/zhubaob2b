package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.PaywayDAO;
import com.zhubao.b2b.platform.model.Payway;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class PaywayDAOImpl extends BasicDAOSupport implements PaywayDAO {

    @Override
    public void insert(Payway payway) {
        getDatastore().save(payway);
    }

    @Override
    public List<Payway> selectList(String status) {
        if (StringUtils.isNotEmpty(status)) {
            return getDatastore().find(Payway.class).field("status").equal(status).asList();
        }
        return getDatastore().find(Payway.class).asList();
    }

    @Override
    public Payway select(String paywayId) {
        return getDatastore().get(Payway.class, paywayId);
    }
}

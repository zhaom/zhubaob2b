package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.KshopAgencyDAO;
import com.zhubao.b2b.platform.model.KshopAgency;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
public class KshopAgencyDAOImpl extends BasicDAOSupport implements KshopAgencyDAO {
    @Override
    public KshopAgency select(String id) {
        return getDatastore().get(KshopAgency.class, id);
    }

    @Override
    public void insert(KshopAgency kshopAgency) {
        getDatastore().save(kshopAgency);
    }
}

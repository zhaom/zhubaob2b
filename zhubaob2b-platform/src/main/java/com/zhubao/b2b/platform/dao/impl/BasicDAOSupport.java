package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.Datastore;

/**
 * User: xiaoping lu
 * Date: 13-8-19
 * Time: 上午11:37
 */
public class BasicDAOSupport {

    private Datastore datastore;

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }
}

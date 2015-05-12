package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.zhubao.b2b.platform.dao.ShipAddressDAO;
import com.zhubao.b2b.platform.model.ShipAddress;

import java.util.List;

public class ShipAddressDAOImpl extends BasicDAOSupport implements ShipAddressDAO {

    @Override
    public void insert(ShipAddress address) {
        getDatastore().save(address);
    }

    @Override
    public List<ShipAddress> selectList(String customerId) {
        return getDatastore().find(ShipAddress.class).field("customerId").equal(customerId).asList();
    }

    @Override
    public ShipAddress select(String customerId, String addressId) {
        return getDatastore().find(ShipAddress.class).field("customerId").equal(customerId).field(Mapper.ID_KEY).equal(addressId).get();
    }

    @Override
    public void delete(String customerId, String addressId) {
        Query<ShipAddress> query = getDatastore().createQuery(ShipAddress.class).field("customerId").equal(customerId).field(Mapper.ID_KEY).equal(addressId);
        getDatastore().delete(query);
    }
}

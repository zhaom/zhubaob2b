package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.mapping.Mapper;
import com.zhubao.b2b.platform.dao.CustomerDAO;
import com.zhubao.b2b.platform.model.Customer;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-4
 * Time: 上午10:07
 */
public class CustomerDAOImpl extends BasicDAOSupport implements CustomerDAO {

    @Override
    public void insert(Customer user) {
        getDatastore().save(user);
    }

    @Override
    public Customer select(String customerId) {
        return getDatastore().find(Customer.class).field(Mapper.ID_KEY).equal(customerId).get();
    }

    @Override
    public Customer selectByKShopUserName(String kshopUserName) {
        return getDatastore().find(Customer.class).field("kshopUserName").equal(kshopUserName).get();
    }

    @Override
    public List<Customer> selectByVenderKshopAgencyId(String venderKshopAgencyId) {
        return getDatastore().find(Customer.class).field("venderKshopAgencyId").equal(venderKshopAgencyId).asList();
    }

    @Override
    public Customer selectByKShopUserId(String kshopUserId) {
        return getDatastore().find(Customer.class).field("kshopUserId").equal(kshopUserId).get();
    }
}

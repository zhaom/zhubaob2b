package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.Customer;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-4
 * Time: 上午10:03
 */
public interface CustomerDAO {

    public void insert(Customer customer);

    public Customer select(String customerId);

    public Customer selectByKShopUserId(String kshopUserId);

    public Customer selectByKShopUserName(String kshopUserName);

    public List<Customer> selectByVenderKshopAgencyId(String venderKshopAgencyId);
}

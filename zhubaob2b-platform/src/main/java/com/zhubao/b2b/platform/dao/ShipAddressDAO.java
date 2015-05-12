package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.ShipAddress;

import java.util.List;

public interface ShipAddressDAO {

    public void insert(ShipAddress address);

    public List<ShipAddress> selectList(String customerId);

    public ShipAddress select(String customerId, String addressId);

    public void delete(String customerId, String addressId);
}

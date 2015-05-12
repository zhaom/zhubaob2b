package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.platform.model.ShipAddress;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-14
 * Time: 下午2:32
 */
public interface ShipAddressService {

    public void createShipAddress(ShipAddress address);

    public List<ShipAddress> getAddresses(String customerId);

    public void deleteAddress(String customerId, String addressId);

    public void setDefaultAddress(String userId, String addrId);
}

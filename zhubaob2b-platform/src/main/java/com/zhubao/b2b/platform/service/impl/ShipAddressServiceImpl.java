package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.exception.ServiceException;
import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.platform.dao.ShipAddressDAO;
import com.zhubao.b2b.platform.model.ShipAddress;
import com.zhubao.b2b.platform.service.ShipAddressService;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-14
 * Time: 下午2:38
 */
public class ShipAddressServiceImpl extends BasicServiceSupport implements ShipAddressService {

    private ShipAddressDAO shipAddressDAO;

    public void setShipAddressDAO(ShipAddressDAO shipAddressDAO) {
        this.shipAddressDAO = shipAddressDAO;
    }

    @Override
    public void createShipAddress(ShipAddress address) {
        try {
        	address.setId(IdFactory.generateId());
            shipAddressDAO.insert(address);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public List<ShipAddress> getAddresses(String customerId) {
        try {
            return shipAddressDAO.selectList(customerId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteAddress(String customerId, String addressId) {
        try {
            shipAddressDAO.delete(customerId, addressId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void setDefaultAddress(String userId, String addrId) {
        try{
            ShipAddress shipAddress = shipAddressDAO.select(userId, addrId);
            shipAddress.setDefault(true);
            shipAddressDAO.insert(shipAddress);
        }catch (Exception e){
            logger.error("Error occured:", e);
            throw  new ServiceException("service error:", e);
        }
    }
}

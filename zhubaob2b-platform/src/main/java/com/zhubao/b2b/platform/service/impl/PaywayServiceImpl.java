package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.exception.ServiceException;
import com.zhubao.b2b.platform.dao.PaywayDAO;
import com.zhubao.b2b.platform.model.Payway;
import com.zhubao.b2b.platform.service.PaywayService;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-21
 * Time: 下午2:06
 */
public class PaywayServiceImpl extends BasicServiceSupport implements PaywayService {

    private PaywayDAO paywayDAO;

    public void setPaywayDAO(PaywayDAO paywayDAO) {
        this.paywayDAO = paywayDAO;
    }

    @Override
    public void createPayway(Payway payway) {
        try {
            paywayDAO.insert(payway);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public List<Payway> getPayways(String status) {
        try {
            return paywayDAO.selectList(status);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }
}

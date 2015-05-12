package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.exception.ServiceException;
import com.zhubao.b2b.platform.dao.GoodsUseDAO;
import com.zhubao.b2b.platform.model.GoodsUse;
import com.zhubao.b2b.platform.service.GoodsUseService;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午4:36
 */
public class GoodsUseServiceImpl extends BasicServiceSupport implements GoodsUseService {

    private GoodsUseDAO goodsUseDAO;

    public void setGoodsUseDAO(GoodsUseDAO goodsUseDAO) {
        this.goodsUseDAO = goodsUseDAO;
    }

    @Override
    public void createUse(GoodsUse use) {
        try {
            goodsUseDAO.insert(use);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void updateUse(GoodsUse use) {
        try {
            goodsUseDAO.update(use);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteUse(String useId) {
        try {
            goodsUseDAO.delete(useId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public GoodsUse getUse(String useId) {
        try {
            return goodsUseDAO.select(useId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public List<GoodsUse> getUses() {
        try {
            return goodsUseDAO.selectList();
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }
}

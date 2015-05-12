package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.exception.ServiceException;
import com.zhubao.b2b.platform.dao.GoodsStyleDAO;
import com.zhubao.b2b.platform.model.GoodsStyle;
import com.zhubao.b2b.platform.service.GoodsStyleService;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午4:30
 */
public class GoodsStyleServiceImpl extends BasicServiceSupport implements GoodsStyleService {

    private GoodsStyleDAO goodsStyleDAO;

    public void setGoodsStyleDAO(GoodsStyleDAO goodsStyleDAO) {
        this.goodsStyleDAO = goodsStyleDAO;
    }

    @Override
    public void createStyle(GoodsStyle style) {
        try {
            goodsStyleDAO.insert(style);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void updateStyle(GoodsStyle style) {
        try {
            goodsStyleDAO.update(style);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteStyle(String styleId) {
        try {
            goodsStyleDAO.delete(styleId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public GoodsStyle getStyle(String styleId) {
        try {
            return goodsStyleDAO.select(styleId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public List<GoodsStyle> getStyles() {
        try {
            return goodsStyleDAO.selectList();
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }
}

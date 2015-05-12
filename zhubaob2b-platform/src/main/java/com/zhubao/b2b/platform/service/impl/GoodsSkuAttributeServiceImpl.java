package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.exception.ServiceException;
import com.zhubao.b2b.platform.dao.GoodsSkuAttributeDAO;
import com.zhubao.b2b.platform.dao.GoodsSkuAttributeValueDAO;
import com.zhubao.b2b.platform.model.GoodsSkuAttribute;
import com.zhubao.b2b.platform.model.GoodsSkuAttributeValue;
import com.zhubao.b2b.platform.service.GoodsSkuAttributeService;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午4:15
 */
public class GoodsSkuAttributeServiceImpl extends BasicServiceSupport implements GoodsSkuAttributeService {

    private GoodsSkuAttributeDAO goodsSkuAttributeDAO;
    private GoodsSkuAttributeValueDAO goodsSkuAttributeValueDAO;

    public void setGoodsSkuAttributeDAO(GoodsSkuAttributeDAO goodsSkuAttributeDAO) {
        this.goodsSkuAttributeDAO = goodsSkuAttributeDAO;
    }

    public void setGoodsSkuAttributeValueDAO(GoodsSkuAttributeValueDAO goodsSkuAttributeValueDAO) {
        this.goodsSkuAttributeValueDAO = goodsSkuAttributeValueDAO;
    }

    @Override
    public void createSkuAttribute(GoodsSkuAttribute attribute) {
        try {
            goodsSkuAttributeDAO.insert(attribute);
            if (attribute.getValues() != null) {
                for (GoodsSkuAttributeValue value : attribute.getValues()) {
                    goodsSkuAttributeValueDAO.insert(value);
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void updateSkuAttribute(GoodsSkuAttribute attribute) {
        try {
            goodsSkuAttributeDAO.update(attribute);
            // todo
            // values.....
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteSkuAttribute(String attributeId) {
        try {
            goodsSkuAttributeDAO.delete(attributeId);
            goodsSkuAttributeValueDAO.deleteByAttributeId(attributeId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public GoodsSkuAttribute getSkuAttribute(String attributeId) {
        try {
            GoodsSkuAttribute attribute = goodsSkuAttributeDAO.select(attributeId);
            if (attribute != null) {
                attribute.setValues(goodsSkuAttributeValueDAO.selectList(attributeId));
            }
            return attribute;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public List<GoodsSkuAttribute> getSkuAttributes(String styleId) {
        try {
            List<GoodsSkuAttribute> attributes = goodsSkuAttributeDAO.selectList(styleId);
            if (attributes != null && attributes.size() > 0) {
                for (GoodsSkuAttribute attribute : attributes) {
                    attribute.setValues(goodsSkuAttributeValueDAO.selectList(attribute.getId()));
                }
            }
            return attributes;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }
}

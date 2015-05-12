package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.exception.ServiceException;
import com.zhubao.b2b.platform.dao.GoodsAttributeDAO;
import com.zhubao.b2b.platform.dao.GoodsAttributeValueDAO;
import com.zhubao.b2b.platform.model.GoodsAttribute;
import com.zhubao.b2b.platform.model.GoodsAttributeValue;
import com.zhubao.b2b.platform.service.GoodsAttributeService;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-23
 * Time: 下午2:50
 */
public class GoodsAttributeServiceImpl extends BasicServiceSupport implements GoodsAttributeService {

    private GoodsAttributeDAO goodsAttributeDAO;
    private GoodsAttributeValueDAO goodsAttributeValueDAO;

    public void setGoodsAttributeDAO(GoodsAttributeDAO goodsAttributeDAO) {
        this.goodsAttributeDAO = goodsAttributeDAO;
    }

    public void setGoodsAttributeValueDAO(GoodsAttributeValueDAO goodsAttributeValueDAO) {
        this.goodsAttributeValueDAO = goodsAttributeValueDAO;
    }

    @Override
    public void createAttribute(GoodsAttribute attribute) {
        try {
            goodsAttributeDAO.insert(attribute);
            if (attribute.getValues() != null) {
                for (GoodsAttributeValue value : attribute.getValues()) {
                    goodsAttributeValueDAO.insert(value);
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void updateAttribute(GoodsAttribute attribute) {
        try {
            goodsAttributeDAO.update(attribute);
            // todo
            // values......
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteAttribute(String attributeId) {
        try {
            goodsAttributeDAO.delete(attributeId);
            goodsAttributeValueDAO.deleteByAttributeId(attributeId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public GoodsAttribute getAttribute(String attributeId) {
        try {
            GoodsAttribute attribute = goodsAttributeDAO.select(attributeId);
            if (attribute != null) {
                attribute.setValues(goodsAttributeValueDAO.selectList(attributeId));
            }
            return attribute;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public List<GoodsAttribute> getAttributes(String materialId, String styleId) {
        try {
            List<GoodsAttribute> attributes = goodsAttributeDAO.selectList(materialId, styleId);
            if (attributes != null) {
                for (GoodsAttribute attribute : attributes) {
                    attribute.setValues(goodsAttributeValueDAO.selectList(attribute.getId()));
                }
            }
            return attributes;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

}

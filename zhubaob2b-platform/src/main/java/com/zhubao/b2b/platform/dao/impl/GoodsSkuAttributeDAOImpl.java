package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.GoodsSkuAttributeDAO;
import com.zhubao.b2b.platform.model.GoodsSkuAttribute;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午4:00
 */
public class GoodsSkuAttributeDAOImpl extends BasicDAOSupport implements GoodsSkuAttributeDAO {

    @Override
    public void insert(GoodsSkuAttribute attribute) {
        getDatastore().save(attribute);
    }

    @Override
    public void delete(String attributeId) {
        getDatastore().delete(GoodsSkuAttribute.class, attributeId);
    }

    @Override
    public void update(GoodsSkuAttribute attribute) {
        getDatastore().save(attribute);
    }

    @Override
    public GoodsSkuAttribute select(String attributeId) {
        return getDatastore().get(GoodsSkuAttribute.class, attributeId);
    }

    @Override
    public List<GoodsSkuAttribute> selectList(String styleId) {
        return getDatastore().find(GoodsSkuAttribute.class).field("styleId").equal(styleId).asList();
    }
}

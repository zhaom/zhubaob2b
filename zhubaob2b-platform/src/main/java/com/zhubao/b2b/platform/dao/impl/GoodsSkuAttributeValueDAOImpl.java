package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.query.Query;
import com.zhubao.b2b.platform.dao.GoodsSkuAttributeValueDAO;
import com.zhubao.b2b.platform.model.GoodsSkuAttributeValue;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-15
 * Time: 上午10:15
 */
public class GoodsSkuAttributeValueDAOImpl extends BasicDAOSupport implements GoodsSkuAttributeValueDAO {

    @Override
    public void insert(GoodsSkuAttributeValue value) {
        getDatastore().save(value);
    }

    @Override
    public void deleteByAttributeId(String attributeId) {
        Query<GoodsSkuAttributeValue> query = getDatastore().createQuery(GoodsSkuAttributeValue.class).field("attributeId").equal(attributeId);
        getDatastore().delete(query);
    }

    @Override
    public GoodsSkuAttributeValue select(String valueId) {
        return getDatastore().get(GoodsSkuAttributeValue.class, valueId);
    }

    @Override
    public List<GoodsSkuAttributeValue> selectList(String attributeId) {
        return getDatastore().find(GoodsSkuAttributeValue.class).field("attributeId").equal(attributeId).asList();
    }
}

package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.query.Query;
import com.zhubao.b2b.platform.dao.GoodsAttributeValueDAO;
import com.zhubao.b2b.platform.model.GoodsAttributeValue;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-14
 * Time: 下午5:17
 */
public class GoodsAttributeValueDAOImpl extends BasicDAOSupport implements GoodsAttributeValueDAO {

    @Override
    public List<GoodsAttributeValue> selectList(String attributeId) {
        return getDatastore().find(GoodsAttributeValue.class).field("attributeId").equal(attributeId).asList();
    }

    @Override
    public GoodsAttributeValue select(String valueId) {
        return getDatastore().get(GoodsAttributeValue.class, valueId);
    }

    @Override
    public void deleteByAttributeId(String attributeId) {
        Query<GoodsAttributeValue> query = getDatastore().createQuery(GoodsAttributeValue.class).field("attributeId").equal(attributeId);
        getDatastore().delete(query);
    }

    @Override
    public void delete(String valueId) {
        getDatastore().delete(GoodsAttributeValue.class, valueId);
    }

    @Override
    public void insert(GoodsAttributeValue value) {
        getDatastore().save(value);
    }
}

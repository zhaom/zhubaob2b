package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.GoodsAttributeDAO;
import com.zhubao.b2b.platform.model.GoodsAttribute;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-25
 * Time: 下午2:05
 */
public class GoodsAttributeDAOImpl extends BasicDAOSupport implements GoodsAttributeDAO {

    @Override
    public void insert(GoodsAttribute attribute) {
        getDatastore().save(attribute);
    }

    @Override
    public void update(GoodsAttribute attribute) {
        getDatastore().save(attribute);
    }

    @Override
    public void delete(String attributeId) {
        getDatastore().delete(GoodsAttribute.class, attributeId);
    }

    @Override
    public GoodsAttribute select(String attributeId) {
        return getDatastore().get(GoodsAttribute.class, attributeId);
    }

    @Override
    public List<GoodsAttribute> selectList(String materialId, String styleId) {
        return getDatastore().find(GoodsAttribute.class).field("materialId").equal(materialId).field("styleId").equal(styleId).asList();
    }
}

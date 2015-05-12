package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.GoodsAttributeValue;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-14
 * Time: 下午5:14
 */
public interface GoodsAttributeValueDAO {

    public List<GoodsAttributeValue> selectList(String attributeId);

    public GoodsAttributeValue select(String valueId);

    public void deleteByAttributeId(String attributeId);

    public void delete(String valueId);

    public void insert(GoodsAttributeValue value);
}

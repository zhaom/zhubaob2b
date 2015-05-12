package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.GoodsSkuAttributeValue;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-15
 * Time: 上午10:12
 */
public interface GoodsSkuAttributeValueDAO {

    public void insert(GoodsSkuAttributeValue value);

    public void deleteByAttributeId(String attributeId);

    public GoodsSkuAttributeValue select(String valueId);

    public List<GoodsSkuAttributeValue> selectList(String attributeId);

}

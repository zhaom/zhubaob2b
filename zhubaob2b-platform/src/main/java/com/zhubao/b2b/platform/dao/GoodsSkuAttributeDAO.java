package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.GoodsSkuAttribute;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午3:53
 */
public interface GoodsSkuAttributeDAO {

    public void insert(GoodsSkuAttribute attribute);

    public void delete(String attributeId);

    public void update(GoodsSkuAttribute attribute);

    public GoodsSkuAttribute select(String attributeId);

    public List<GoodsSkuAttribute> selectList(String styleId);

}

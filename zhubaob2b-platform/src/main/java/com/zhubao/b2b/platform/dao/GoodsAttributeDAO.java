package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.GoodsAttribute;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-25
 * Time: 上午11:49
 */

public interface GoodsAttributeDAO {

    public void insert(GoodsAttribute attribute);

    public void update(GoodsAttribute attribute);

    public void delete(String attributeId);

    public GoodsAttribute select(String attributeId);

    public List<GoodsAttribute> selectList(String materialId, String styleId);
}

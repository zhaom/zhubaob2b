package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.GoodsStyle;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-23
 * Time: 上午11:36
 */
public interface GoodsStyleDAO {

    public void insert(GoodsStyle style);

    public void delete(String styleId);

    public void update(GoodsStyle style);

    public GoodsStyle select(String styleId);

    public List<GoodsStyle> selectList();
}

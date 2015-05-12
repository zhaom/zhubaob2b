package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.GoodsStyleDAO;
import com.zhubao.b2b.platform.model.GoodsStyle;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-23
 * Time: 上午11:42
 */
public class GoodsStyleDAOImpl extends BasicDAOSupport implements GoodsStyleDAO {

    @Override
    public void insert(GoodsStyle style) {
        getDatastore().save(style);
    }

    @Override
    public void delete(String styleId) {
        getDatastore().delete(GoodsStyle.class, styleId);
    }

    @Override
    public void update(GoodsStyle style) {
        getDatastore().save(style);
    }

    @Override
    public GoodsStyle select(String styleId) {
        return getDatastore().get(GoodsStyle.class, styleId);
    }

    @Override
    public List<GoodsStyle> selectList() {
        return getDatastore().find(GoodsStyle.class).order("-orderNum").asList();
    }
}

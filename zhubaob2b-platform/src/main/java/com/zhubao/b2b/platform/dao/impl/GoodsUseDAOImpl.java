package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.mapping.Mapper;
import com.zhubao.b2b.platform.dao.GoodsUseDAO;
import com.zhubao.b2b.platform.model.GoodsUse;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-23
 * Time: 上午10:45
 */
public class GoodsUseDAOImpl extends BasicDAOSupport implements GoodsUseDAO {

    @Override
    public void insert(GoodsUse use) {
        getDatastore().save(use);
    }

    @Override
    public void delete(String useId) {
        getDatastore().delete(GoodsUse.class, useId);
    }

    @Override
    public void update(GoodsUse use) {
        getDatastore().save(use);
    }

    @Override
    public GoodsUse select(String useId) {
        return getDatastore().get(GoodsUse.class, useId);
    }

    @Override
    public List<GoodsUse> selectList() {
        return getDatastore().find(GoodsUse.class).order("-orderNum").asList();
    }

    @Override
    public List<GoodsUse> selectListByIds(List<String> useIds) {
        return getDatastore().find(GoodsUse.class).field(Mapper.ID_KEY).in(useIds).asList();
    }
}

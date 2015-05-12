package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.GoodsUse;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-23
 * Time: 上午10:41
 */
public interface GoodsUseDAO {

    public void insert(GoodsUse use);

    public void delete(String useId);

    public void update(GoodsUse use);

    public GoodsUse select(String useId);

    public List<GoodsUse> selectList();

    public List<GoodsUse> selectListByIds(List<String> useIds);
}

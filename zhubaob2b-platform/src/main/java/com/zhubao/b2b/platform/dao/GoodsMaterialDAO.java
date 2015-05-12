package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.GoodsMaterial;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-23
 * Time: 上午11:04
 */
public interface GoodsMaterialDAO {

    public void insert(GoodsMaterial material);

    public void delete(String materialId);

    public void update(GoodsMaterial material);

    public GoodsMaterial select(String materialId);

    public GoodsMaterial selectByAlias(String alias);

    public List<GoodsMaterial> selectList();
}

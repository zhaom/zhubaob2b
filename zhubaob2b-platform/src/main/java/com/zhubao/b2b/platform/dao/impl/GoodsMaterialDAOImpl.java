package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.GoodsMaterialDAO;
import com.zhubao.b2b.platform.model.GoodsMaterial;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-23
 * Time: 上午11:16
 */
public class GoodsMaterialDAOImpl extends BasicDAOSupport implements GoodsMaterialDAO {

    @Override
    public void insert(GoodsMaterial material) {
        getDatastore().save(material);
    }

    @Override
    public void delete(String materialId) {
        getDatastore().delete(GoodsMaterial.class, materialId);
    }

    @Override
    public void update(GoodsMaterial material) {
        getDatastore().save(material);
    }

    @Override
    public GoodsMaterial select(String materialId) {
        return getDatastore().get(GoodsMaterial.class, materialId);
    }

    @Override
    public GoodsMaterial selectByAlias(String alias) {
        return getDatastore().find(GoodsMaterial.class).field("alias").equal(alias).get();
    }

    @Override
    public List<GoodsMaterial> selectList() {
        return getDatastore().find(GoodsMaterial.class).order("-orderNum").asList();
    }
}

package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.platform.model.GoodsMaterial;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午4:24
 */
public interface GoodsMaterialService {

    public void createMaterial(GoodsMaterial material);

    public void updateMaterial(GoodsMaterial material);

    public void deleteMaterial(String materialId);

    public GoodsMaterial getMaterial(String materialId);

    public GoodsMaterial getMaterialByAlias(String alias);

    public List<GoodsMaterial> getMaterials();
}

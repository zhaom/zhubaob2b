package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.platform.model.GoodsUse;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午4:26
 */
public interface GoodsUseService {

    public void createUse(GoodsUse use);

    public void updateUse(GoodsUse use);

    public void deleteUse(String useId);

    public GoodsUse getUse(String useId);

    public List<GoodsUse> getUses();
}

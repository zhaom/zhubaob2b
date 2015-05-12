package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.GoodsSku;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-15
 * Time: 上午11:13
 */
public interface GoodsSkuDAO {

    public void insert(GoodsSku sku);

    public void deleteByGoodsId(String goodsId);

    public GoodsSku select(String goodsId, String skuId);

    public GoodsSku selectBySkuAttrValueIds(String goodsId, List<String> skuAttrValueIds);

    public void delete(String goodsId, String skuId);

    public List<GoodsSku> selectList(String goodsId);

    public void updateCurCountAndFreezeCount(String goodsId, String skuId, int curCount, int optFlag);

    public void updateSellCountAndFreezeCount(String goodsId, String skuId, int sellCount);
}

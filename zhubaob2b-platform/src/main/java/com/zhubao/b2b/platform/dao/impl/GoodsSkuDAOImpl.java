package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.zhubao.b2b.platform.dao.GoodsSkuDAO;
import com.zhubao.b2b.platform.model.GoodsSku;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-15
 * Time: 上午11:20
 */
public class GoodsSkuDAOImpl extends BasicDAOSupport implements GoodsSkuDAO {

    @Override
    public void insert(GoodsSku sku) {
        getDatastore().save(sku);
    }

    @Override
    public void deleteByGoodsId(String goodsId) {
        Query<GoodsSku> query = getDatastore().createQuery(GoodsSku.class).field("goodsId").equal(goodsId);
        getDatastore().delete(query);
    }

    @Override
    public GoodsSku select(String goodsId, String skuId) {
        return getDatastore().createQuery(GoodsSku.class).field("goodsId").equal(goodsId).field(Mapper.ID_KEY).equal(skuId).get();
    }

    @Override
    public GoodsSku selectBySkuAttrValueIds(String goodsId, List<String> skuAttrValueIds) {
        return getDatastore().createQuery(GoodsSku.class).field("goodsId").equal(goodsId).field("skuAttrValueIds").hasAllOf(skuAttrValueIds).get();
    }

    @Override
    public void delete(String goodsId, String skuId) {
        Query<GoodsSku> query = getDatastore().createQuery(GoodsSku.class).field("goodsId").equal(goodsId).field(Mapper.ID_KEY).equal(skuId);
        getDatastore().delete(query);
    }

    @Override
    public List<GoodsSku> selectList(String goodsId) {
        return getDatastore().find(GoodsSku.class).field("goodsId").equal(goodsId).asList();
    }

    @Override
    public void updateCurCountAndFreezeCount(String goodsId, String skuId, int freezeCount, int optFlag) {
        Query<GoodsSku> query = getDatastore().createQuery(GoodsSku.class).field("goodsId").equal(goodsId).field(Mapper.ID_KEY).equal(skuId);
        UpdateOperations<GoodsSku> updateOpt = null;
        if (optFlag > 0) {
            updateOpt = getDatastore().createUpdateOperations(GoodsSku.class).inc("curCount", -freezeCount).inc("freezeCount", freezeCount);
        } else {
            updateOpt = getDatastore().createUpdateOperations(GoodsSku.class).inc("curCount", freezeCount).inc("freezeCount", -freezeCount);
        }
        getDatastore().update(query, updateOpt);
    }

    @Override
    public void updateSellCountAndFreezeCount(String goodsId, String skuId, int sellCount) {
        Query<GoodsSku> query = getDatastore().createQuery(GoodsSku.class).field("goodsId").equal(goodsId).field(Mapper.ID_KEY).equal(skuId);
        UpdateOperations<GoodsSku> updateOpt = getDatastore().createUpdateOperations(GoodsSku.class).inc("sellCount", sellCount).inc("freezeCount", -sellCount);
        getDatastore().update(query, updateOpt);
    }
}

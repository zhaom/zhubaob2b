package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.zhubao.b2b.platform.dao.ShopCartGoodsDAO;
import com.zhubao.b2b.platform.model.ShopCartGoods;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-14
 * Time: 上午10:27
 */
public class ShopCartGoodsDAOImpl extends BasicDAOSupport implements ShopCartGoodsDAO {

    @Override
    public void insert(ShopCartGoods cartGoods) {
        getDatastore().save(cartGoods);
    }

    @Override
    public void deleteByShopCartId(String shopCartId) {
        Query<ShopCartGoods> query = getDatastore().createQuery(ShopCartGoods.class).field("shopCartId").equal(shopCartId);
        getDatastore().delete(query);
    }

    @Override
    public void delete(String shopCartId, String shopCartGoodsId) {
        Query<ShopCartGoods> query = getDatastore().createQuery(ShopCartGoods.class).field(Mapper.ID_KEY).equal(shopCartGoodsId).field("shopCartId").equal(shopCartId);
        getDatastore().delete(query);
    }

    @Override
    public void updateAmount(String shopCartId, String shopCartGoodsId, int amount) {
        Query<ShopCartGoods> query = getDatastore().createQuery(ShopCartGoods.class).field(Mapper.ID_KEY).equal(shopCartGoodsId).field("shopCartId").equal(shopCartId);
        UpdateOperations<ShopCartGoods> updateOpt = getDatastore().createUpdateOperations(ShopCartGoods.class).set("amount", amount);
        getDatastore().update(query, updateOpt);
    }

    @Override
    public List<ShopCartGoods> selectListByShopCartId(String shopCartId) {
        return getDatastore().find(ShopCartGoods.class).field("shopCartId").equal(shopCartId).asList();
    }

    @Override
    public ShopCartGoods select(String shopCartId, String shopCartGoodsId) {
        return getDatastore().find(ShopCartGoods.class).field(Mapper.ID_KEY).equal(shopCartGoodsId).field("shopCartId").equal(shopCartId).get();
    }

    @Override
    public ShopCartGoods select(String shopCartId, String goodsId, String skuId) {
        return getDatastore().find(ShopCartGoods.class).field("shopCartId").equal(shopCartId).field("goodsId").equal(goodsId).field("skuId").equal(skuId).get();
    }

}

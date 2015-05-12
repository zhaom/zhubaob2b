package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.zhubao.b2b.platform.dao.GoodsDAO;
import com.zhubao.b2b.platform.entry.GoodsQueryParameter;
import com.zhubao.b2b.platform.model.Goods;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-8-20
 * Time: 下午6:05
 */
public class GoodsDAOImpl extends BasicDAOSupport implements GoodsDAO {

    @Override
    public void insert(Goods goods) {
        getDatastore().save(goods);
    }

    @Override
    public Goods select(String venderId, String goodsId) {
        Query<Goods> query = getDatastore().createQuery(Goods.class).field(Mapper.ID_KEY).equal(goodsId);
        if (StringUtils.isNotEmpty(venderId)) {
            query.field("venderId").equal(venderId);
        }
        return query.get();
    }

    @Override
    public void delete(String venderId, String goodsId) {
        Query<Goods> query = getDatastore().createQuery(Goods.class).field(Mapper.ID_KEY).equal(goodsId);
        if (StringUtils.isNotEmpty(venderId)) {
            query.field("venderId").equal(venderId);
        }
        getDatastore().delete(query);
    }

    @Override
    public void update(Goods goods) {
        getDatastore().save(goods);
    }

    @Override
    public List<Goods> selectList(GoodsQueryParameter param, int range) {
        return param.buildQuery(getDatastore()).offset(0).limit(range).asList();
    }

    @Override
    public List<Goods> selectPageList(GoodsQueryParameter param, int start, int range) {
        return param.buildQuery(getDatastore()).offset(start).limit(range).asList();
    }

    @Override
    public Integer count(GoodsQueryParameter param) {
        return (int) getDatastore().getCount(param.buildQuery(getDatastore()));
    }

    @Override
    public List<Goods> selectByIds(List<String> goodsIds) {
        return getDatastore().find(Goods.class).field(Mapper.ID_KEY).in(goodsIds).asList();
    }

    @Override
    public void updateSellCount(String goodsId, int count) {
        Query<Goods> query = getDatastore().createQuery(Goods.class).field(Mapper.ID_KEY).equal(goodsId);
        UpdateOperations<Goods> updateOpt = getDatastore().createUpdateOperations(Goods.class).inc("sellCount", count);
        getDatastore().update(query, updateOpt);
    }
}

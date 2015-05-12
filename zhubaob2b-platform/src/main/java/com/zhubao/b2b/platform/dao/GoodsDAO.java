package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.entry.GoodsQueryParameter;
import com.zhubao.b2b.platform.model.Goods;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-8-20
 * Time: 下午5:47
 */
public interface GoodsDAO {

    public void insert(Goods goods);

    public Goods select(String venderId, String goodsId);

    public void delete(String venderId, String goodsId);

    public void update(Goods goods);

    public List<Goods> selectList(GoodsQueryParameter param, int range);

    public List<Goods> selectPageList(GoodsQueryParameter param, int start, int range);

    public Integer count(GoodsQueryParameter param);

    public List<Goods> selectByIds(List<String> goodsIds);

    public void updateSellCount(String goodsId, int count);
}

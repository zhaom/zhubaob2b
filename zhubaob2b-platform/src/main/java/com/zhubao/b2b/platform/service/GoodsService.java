package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.platform.entry.GoodsQueryParameter;
import com.zhubao.b2b.platform.model.Goods;
import com.zhubao.b2b.platform.model.GoodsSku;
import com.zhubao.common.utils.Pagination;

import java.util.List;
import java.util.Map;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 上午9:08
 */
public interface GoodsService {

    public void createGoods(Goods goods);

    public void deleteGoods(String goodsId);

    public void deleteGoods(String venderId, String goodsId);

    public void updateGoods(Goods goods);

    public Goods getGoods(String goodsId);

    public Goods getGoods(String venderId, String goodsId);

    public GoodsSku getGoodsSku(String goodsId, List<String> skuAttrValueIds);

    public GoodsSku getGoodsSku(String goodsId, String skuId);

    public List<Goods> getTopXGoods(GoodsQueryParameter param, int range);

    public Pagination<Goods> getPaginationGoods(GoodsQueryParameter param, int page, int pageSize);

    public List<Goods> getGoodsByIds(List<String> goodsIds);

    public Map<String, Integer> countByMaterial(String venderId);

    public Goods doOnShelf(String venderId, String goodsId);

    public Goods doOffShelf(String venderid, String goodsId);
}

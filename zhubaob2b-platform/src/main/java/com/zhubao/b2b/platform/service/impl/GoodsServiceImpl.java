package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.exception.ServiceException;
import com.zhubao.b2b.platform.dao.*;
import com.zhubao.b2b.platform.entry.GoodsQueryParameter;
import com.zhubao.b2b.platform.model.Goods;
import com.zhubao.b2b.platform.model.GoodsMaterial;
import com.zhubao.b2b.platform.model.GoodsSku;
import com.zhubao.b2b.platform.service.GoodsService;
import com.zhubao.b2b.platform.utils.AttributeSpecTranslateUtils;
import com.zhubao.common.utils.Pagination;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 上午9:11
 */
public class GoodsServiceImpl extends BasicServiceSupport implements GoodsService {

    private VenderDAO venderDAO;
    private GoodsDAO goodsDAO;
    private GoodsMaterialDAO goodsMaterialDAO;
    private GoodsStyleDAO goodsStyleDAO;
    private GoodsUseDAO goodsUseDAO;
    private GoodsAttributeValueDAO goodsAttributeValueDAO;
    private GoodsAttributeDAO goodsAttributeDAO;
    private GoodsSkuDAO goodsSkuDAO;
    private GoodsSkuAttributeValueDAO goodsSkuAttributeValueDAO;
    private GoodsSkuAttributeDAO goodsSkuAttributeDAO;
    private AttributeSpecTranslateUtils attributeSpecTranslateUtils;

    public void setVenderDAO(VenderDAO venderDAO) {
        this.venderDAO = venderDAO;
    }

    public void setGoodsDAO(GoodsDAO goodsDAO) {
        this.goodsDAO = goodsDAO;
    }

    public void setGoodsMaterialDAO(GoodsMaterialDAO goodsMaterialDAO) {
        this.goodsMaterialDAO = goodsMaterialDAO;
    }

    public void setGoodsStyleDAO(GoodsStyleDAO goodsStyleDAO) {
        this.goodsStyleDAO = goodsStyleDAO;
    }

    public void setGoodsUseDAO(GoodsUseDAO goodsUseDAO) {
        this.goodsUseDAO = goodsUseDAO;
    }

    public void setGoodsAttributeValueDAO(GoodsAttributeValueDAO goodsAttributeValueDAO) {
        this.goodsAttributeValueDAO = goodsAttributeValueDAO;
    }

    public void setGoodsAttributeDAO(GoodsAttributeDAO goodsAttributeDAO) {
        this.goodsAttributeDAO = goodsAttributeDAO;
    }

    public void setGoodsSkuDAO(GoodsSkuDAO goodsSkuDAO) {
        this.goodsSkuDAO = goodsSkuDAO;
    }

    public void setGoodsSkuAttributeValueDAO(GoodsSkuAttributeValueDAO goodsSkuAttributeValueDAO) {
        this.goodsSkuAttributeValueDAO = goodsSkuAttributeValueDAO;
    }

    public void setGoodsSkuAttributeDAO(GoodsSkuAttributeDAO goodsSkuAttributeDAO) {
        this.goodsSkuAttributeDAO = goodsSkuAttributeDAO;
    }

    public void setAttributeSpecTranslateUtils(AttributeSpecTranslateUtils attributeSpecTranslateUtils) {
        this.attributeSpecTranslateUtils = attributeSpecTranslateUtils;
    }

    @Override
    public void createGoods(Goods goods) {
        try {
            goodsDAO.insert(goods);
            if (goods.getSkus() != null) {
                for (GoodsSku sku : goods.getSkus()) {
                    if (goodsSkuDAO.selectBySkuAttrValueIds(sku.getGoodsId(), sku.getSkuAttrValueIds()) == null) {
                        goodsSkuDAO.insert(sku);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteGoods(String goodsId) {
        try {
            goodsDAO.delete(null, goodsId);
            goodsSkuDAO.deleteByGoodsId(goodsId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteGoods(String venderId, String goodsId) {
        try {
            goodsDAO.delete(venderId, goodsId);
            goodsSkuDAO.deleteByGoodsId(goodsId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void updateGoods(Goods goods) {
        try {
            goodsDAO.update(goods);
            goodsSkuDAO.deleteByGoodsId(goods.getId());
            if (goods.getSkus() != null) {
                for (GoodsSku sku : goods.getSkus()) {
                    if (goodsSkuDAO.selectBySkuAttrValueIds(sku.getGoodsId(), sku.getSkuAttrValueIds()) == null) {
                        goodsSkuDAO.insert(sku);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public GoodsSku getGoodsSku(String goodsId, List<String> skuAttrValueIds) {
        try {
            return goodsSkuDAO.selectBySkuAttrValueIds(goodsId, skuAttrValueIds);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public GoodsSku getGoodsSku(String goodsId, String skuId) {
        try {
            return goodsSkuDAO.select(goodsId, skuId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public Goods getGoods(String goodsId) {
        try {
            Goods goods = goodsDAO.select(null, goodsId);
            if (goods != null) {
                if (StringUtils.isNotEmpty(goods.getMaterialId())) {
                    goods.setMaterial(goodsMaterialDAO.select(goods.getMaterialId()));
                }
                if (StringUtils.isNotEmpty(goods.getStyleId())) {
                    goods.setStyle(goodsStyleDAO.select(goods.getStyleId()));
                }
                if (goods.getUseIds() != null) {
                    goods.setUses(goodsUseDAO.selectListByIds(goods.getUseIds()));
                }
                if (StringUtils.isNotEmpty(goods.getVenderId())) {
                    goods.setVender(venderDAO.selectByKShopAgencyId(goods.getVenderId()));
                }
                if (goods.getAttrValueIds() != null) {
                    goods.setAttrSpecs(attributeSpecTranslateUtils.translateGoodsAttrValueIdsToSpecs(goods.getAttrValueIds()));
                }
                List<GoodsSku> skus = goodsSkuDAO.selectList(goods.getId());
                if (skus != null && skus.size() > 0) {
                    for (GoodsSku sku : skus) {
                        if (sku.getSkuAttrValueIds() != null) {
                            sku.setSkuAttrSpecs(attributeSpecTranslateUtils.translateGoodsSkuAttrValueIdsToSkuSpecs(sku.getSkuAttrValueIds()));
                        }
                    }
                }
                goods.setSkus(skus);
            }
            return goods;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public Goods getGoods(String venderId, String goodsId) {
        try {
            Goods goods = goodsDAO.select(venderId, goodsId);
            if (goods != null) {
                goods.setSkus(goodsSkuDAO.selectList(goods.getId()));
            }
            return goods;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public List<Goods> getTopXGoods(GoodsQueryParameter param, int range) {
        try {
            List<Goods> goodsList = goodsDAO.selectList(param, range);
            for (Goods goods : goodsList){
                if(StringUtils.isNotEmpty(goods.getVenderId())){
                    goods.setVender(venderDAO.selectByKShopAgencyId(goods.getVenderId()));
                }
            }
            return goodsList;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public Pagination<Goods> getPaginationGoods(GoodsQueryParameter param, int page, int pageSize) {
        try {
            Pagination<Goods> pagination = new Pagination<Goods>(page, pageSize);

            int totalCount = goodsDAO.count(param);
            pagination.setTotalRecord(totalCount);

            int start = (pagination.getPage() - 1) * pagination.getPagesize();
            int range = pagination.getPagesize();

            List<Goods> goodsList = goodsDAO.selectPageList(param, start, range);
            for (Goods goods : goodsList){
                if(StringUtils.isNotEmpty(goods.getVenderId())){
                    goods.setVender(venderDAO.selectByKShopAgencyId(goods.getVenderId()));
                }
            }
            pagination.setData(goodsList);

            return pagination;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public List<Goods> getGoodsByIds(List<String> goodsIds) {
        try {
            return goodsDAO.selectByIds(goodsIds);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public Map<String, Integer> countByMaterial(String venderId) {
        GoodsQueryParameter goodsQueryParameter = new GoodsQueryParameter();
        goodsQueryParameter.setVenderId(venderId);
        goodsQueryParameter.setStatus(1);

        List<Goods> goodsList = getPaginationGoods(goodsQueryParameter, 1, 10000).getData();
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<GoodsMaterial> materialList = goodsMaterialDAO.selectList();
        for(GoodsMaterial materialEntry: materialList){
            map.put(materialEntry.getId(), 0);
        }

        for (Goods entry : goodsList) {
            String material = entry.getMaterialId();
            int value = map.get(material);
            map.put(material, value + 1);
        }
        return map;
    }

    @Override
    public Goods doOnShelf(String venderid, String goodsId) {
        try{
            Goods goods = goodsDAO.select(venderid, goodsId);
            goods.setStatus(1);
            goodsDAO.update(goods);
            return getGoods(goodsId);
        }catch (Exception e){
            logger.error("error", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public Goods doOffShelf(String venderid, String goodsId) {
        try{
            Goods goods = goodsDAO.select(venderid, goodsId);
            goods.setStatus(2);
            goodsDAO.update(goods);
            return getGoods(goodsId);
        }catch (Exception e){
            logger.error("error", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

}
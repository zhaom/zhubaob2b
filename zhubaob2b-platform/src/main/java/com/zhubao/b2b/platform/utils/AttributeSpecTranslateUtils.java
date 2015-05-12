package com.zhubao.b2b.platform.utils;

import com.zhubao.b2b.platform.dao.GoodsAttributeDAO;
import com.zhubao.b2b.platform.dao.GoodsAttributeValueDAO;
import com.zhubao.b2b.platform.dao.GoodsSkuAttributeDAO;
import com.zhubao.b2b.platform.dao.GoodsSkuAttributeValueDAO;
import com.zhubao.b2b.platform.entity.GoodsAttrSpec;
import com.zhubao.b2b.platform.entity.GoodsSkuAttrSpec;
import com.zhubao.b2b.platform.model.GoodsAttribute;
import com.zhubao.b2b.platform.model.GoodsAttributeValue;
import com.zhubao.b2b.platform.model.GoodsSkuAttribute;
import com.zhubao.b2b.platform.model.GoodsSkuAttributeValue;

import java.util.ArrayList;
import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-24
 * Time: 下午4:17
 */
public class AttributeSpecTranslateUtils {

    private GoodsAttributeDAO goodsAttributeDAO;
    private GoodsAttributeValueDAO goodsAttributeValueDAO;
    private GoodsSkuAttributeDAO goodsSkuAttributeDAO;
    private GoodsSkuAttributeValueDAO goodsSkuAttributeValueDAO;

    public List<GoodsAttrSpec> translateGoodsAttrValueIdsToSpecs(List<String> attrValueIds) {
        List<GoodsAttrSpec> specs = new ArrayList<GoodsAttrSpec>();
        GoodsAttribute attr = null;
        GoodsAttributeValue attrValue = null;
        for (String attrValueId : attrValueIds) {
            attrValue = goodsAttributeValueDAO.select(attrValueId);
            if (attrValue != null) {
                attr = goodsAttributeDAO.select(attrValue.getAttributeId());
            }
            specs.add(new GoodsAttrSpec(attr, attrValue));
        }
        return specs;
    }

    public List<GoodsSkuAttrSpec> translateGoodsSkuAttrValueIdsToSkuSpecs(List<String> skuAttrValueIds) {
        List<GoodsSkuAttrSpec> specs = new ArrayList<GoodsSkuAttrSpec>();
        GoodsSkuAttribute attr = null;
        GoodsSkuAttributeValue attrValue = null;
        for (String attrValueId : skuAttrValueIds) {
            attrValue = goodsSkuAttributeValueDAO.select(attrValueId);
            if (attrValue != null) {
                attr = goodsSkuAttributeDAO.select(attrValue.getAttributeId());
            }
            specs.add(new GoodsSkuAttrSpec(attr, attrValue));
        }
        return specs;
    }

    public void setGoodsAttributeDAO(GoodsAttributeDAO goodsAttributeDAO) {
        this.goodsAttributeDAO = goodsAttributeDAO;
    }

    public void setGoodsAttributeValueDAO(GoodsAttributeValueDAO goodsAttributeValueDAO) {
        this.goodsAttributeValueDAO = goodsAttributeValueDAO;
    }

    public void setGoodsSkuAttributeDAO(GoodsSkuAttributeDAO goodsSkuAttributeDAO) {
        this.goodsSkuAttributeDAO = goodsSkuAttributeDAO;
    }

    public void setGoodsSkuAttributeValueDAO(GoodsSkuAttributeValueDAO goodsSkuAttributeValueDAO) {
        this.goodsSkuAttributeValueDAO = goodsSkuAttributeValueDAO;
    }
}

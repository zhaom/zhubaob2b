package com.zhubao.b2b.platform.entry;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.model.Goods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: xiaoping lu
 * Date: 13-8-20
 * Time: 下午5:53
 */
public class GoodsQueryParameter {

    private final static Logger logger = LoggerFactory.getLogger(GoodsQueryParameter.class);

    private String venderId;
    private String materialId;
    private String styleId;
    private String useId;
    private int status = -1;
    private String keywords;
    private int priceType = -1;
    private List<String> attrValueIds;
    private String order = "desc";
    private String orderBy = Constants.GOODS_ORDER_BY_ON_SHELF_TIME;

    public Query<Goods> buildQuery(Datastore datastore) {
        Query<Goods> query = datastore.createQuery(Goods.class);
        if (StringUtils.isNotEmpty(venderId)) {
            query.field("venderId").equal(venderId);
        }
        if (StringUtils.isNotEmpty(materialId)) {
            query.field("materialId").equal(materialId);
        }
        if (StringUtils.isNotEmpty(styleId)) {
            query.field("styleId").equal(styleId);
        }
        if (StringUtils.isNotEmpty(useId)) {
            query.field("useIds").hasThisOne(useId);
        }
        if(status != -1){
            query.field("status").equal(status);
        }
        if (priceType != -1) {
            query.field("isFixedPrice").equal(priceType);
        }
        if (StringUtils.isNotEmpty(keywords)) {
            query.field("name").containsIgnoreCase(keywords);
        }
        if (attrValueIds != null && attrValueIds.size() > 0) {
            query.field("attrValueIds").hasAllOf(attrValueIds);
        }
        // order
        String orderColumn = "onShelfTime";
        if (Constants.GOODS_ORDER_BY_ON_SHELF_TIME.equals(orderBy) && "desc".equals(order)) {
            orderColumn = "-onShelfTime";
        } else if (Constants.GOODS_ORDER_BY_SELL_COUNT.equals(orderBy) && "asc".equals(order)) {
            orderColumn = "sellCount";
        } else if (Constants.GOODS_ORDER_BY_SELL_COUNT.equals(orderBy) && "desc".equals(order)) {
            orderColumn = "-sellCount";
        }
        query.order(orderColumn);

        logger.debug("query finaly [{}]", query);
        return query;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<String> getAttrValueIds() {
        return attrValueIds;
    }

    public void setAttrValueIds(List<String> attrValueIds) {
        this.attrValueIds = attrValueIds;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

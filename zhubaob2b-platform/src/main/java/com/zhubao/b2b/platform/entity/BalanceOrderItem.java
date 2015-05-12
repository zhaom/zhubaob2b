package com.zhubao.b2b.platform.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class BalanceOrderItem implements Serializable {

    private String orderItemId;

    private String goodsId;

    private String goodsSkuId;

    private String materialId;

    private String name;

    private float agencyPrice;

    private float balanceWeight;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(String goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAgencyPrice() {
        return agencyPrice;
    }

    public void setAgencyPrice(float agencyPrice) {
        this.agencyPrice = agencyPrice;
    }

    public float getBalanceWeight() {
        return balanceWeight;
    }

    public void setBalanceWeight(float balanceWeight) {
        this.balanceWeight = balanceWeight;
    }
}

package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class GoodsSalesItem implements Serializable {

	private static final long serialVersionUID = -4365195527502769802L;
	
	@Id
    private String id;
    @Indexed
    private String salesId;
    @Indexed
    private String goodsId;
    @Indexed
    private String goodsSkuId;

    private int amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

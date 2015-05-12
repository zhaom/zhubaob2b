package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;
import com.zhubao.b2b.platform.entity.GoodsPrice;

import java.io.Serializable;
import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-3
 * Time: 下午5:38
 */
@Entity(value = "shop_cart_goods", noClassnameStored = true)
public class ShopCartGoods implements Serializable {

    private static final long serialVersionUID = 8783155256126556367L;

    @Id
    private String id;
    @Indexed
    private String shopCartId;
    private String goodsId;
    private String venderId;
    private String skuId;
    private int amount;
    private String code;
    private String name;
    private String img;
    private List<GoodsPrice> prices;
    private String materialId;
    private float materialWeight;
    private float price;
    private int isFixedPrice;

    @Transient
    private GoodsSku sku;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(String shopCartId) {
        this.shopCartId = shopCartId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<GoodsPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<GoodsPrice> prices) {
        this.prices = prices;
    }

    public float getMaterialWeight() {
        return materialWeight;
    }

    public void setMaterialWeight(float materialWeight) {
        this.materialWeight = materialWeight;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getIsFixedPrice() {
        return isFixedPrice;
    }

    public void setIsFixedPrice(int fixedPrice) {
        isFixedPrice = fixedPrice;
    }

    public GoodsSku getSku() {
        return sku;
    }

    public void setSku(GoodsSku sku) {
        this.sku = sku;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }
}

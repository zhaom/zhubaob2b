package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;
import com.zhubao.b2b.platform.entity.GoodsSkuAttrSpec;

import java.io.Serializable;
import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-15
 * Time: 上午10:36
 */
@Entity(value = "goods_sku", noClassnameStored = true)
public class GoodsSku implements Serializable {

	private static final long serialVersionUID = -1946452625687080429L;
	
	@Id
    private String id;
    @Indexed
    private String goodsId;
    private String img;
    private List<String> imgs;
    private int curCount;
    private int sellCount;
    private int freezeCount;
    private boolean defaultSku;
    @Indexed
    private List<String> skuAttrValueIds;
    @Transient
    private List<GoodsSkuAttrSpec> skuAttrSpecs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public int getCurCount() {
        return curCount;
    }

    public void setCurCount(int curCount) {
        this.curCount = curCount;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getFreezeCount() {
        return freezeCount;
    }

    public void setFreezeCount(int freezeCount) {
        this.freezeCount = freezeCount;
    }

    public List<String> getSkuAttrValueIds() {
        return skuAttrValueIds;
    }

    public void setSkuAttrValueIds(List<String> skuAttrValueIds) {
        this.skuAttrValueIds = skuAttrValueIds;
    }

    public List<GoodsSkuAttrSpec> getSkuAttrSpecs() {
        return skuAttrSpecs;
    }

    public void setSkuAttrSpecs(List<GoodsSkuAttrSpec> skuAttrSpecs) {
        this.skuAttrSpecs = skuAttrSpecs;
    }

    public boolean isDefaultSku() {
        return defaultSku;
    }

    public void setDefaultSku(boolean defaultSku) {
        this.defaultSku = defaultSku;
    }
}

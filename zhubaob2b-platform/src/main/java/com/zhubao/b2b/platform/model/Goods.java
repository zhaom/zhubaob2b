package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;
import com.zhubao.b2b.platform.entity.GoodsAttrSpec;
import com.zhubao.b2b.platform.entity.GoodsPrice;

import java.io.Serializable;
import java.util.List;

@Entity(value = "goods", noClassnameStored = true)
public class Goods implements Serializable {

	private static final long serialVersionUID = 5177541113208753816L;
	
	@Id
    private String id;
    @Indexed
    private String code;
    private String name;
    private String img;
    private List<String> imgs;
    private int status;
    private long offShelfTime;
    @Indexed
    private long onShelfTime;
    @Indexed
    private int sellCount;
    private List<GoodsPrice> prices;
    private float price;
    private int isFixedPrice;
    private float materialWeight;
    @Indexed
    private String materialId;
    @Indexed
    private String venderId;
    @Indexed
    private String styleId;
    @Indexed
    private List<String> useIds;
    @Indexed
    private List<String> attrValueIds;

    @Transient
    private GoodsMaterial material;
    @Transient
    private Vender vender;
    @Transient
    private GoodsStyle style;
    @Transient
    private List<GoodsUse> uses;
    @Transient
    private List<GoodsAttrSpec> attrSpecs;
    @Transient
    private List<GoodsSku> skus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getOffShelfTime() {
        return offShelfTime;
    }

    public void setOffShelfTime(long offShelfTime) {
        this.offShelfTime = offShelfTime;
    }

    public long getOnShelfTime() {
        return onShelfTime;
    }

    public void setOnShelfTime(long onShelfTime) {
        this.onShelfTime = onShelfTime;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public List<GoodsPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<GoodsPrice> prices) {
        this.prices = prices;
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

    public float getMaterialWeight() {
        return materialWeight;
    }

    public void setMaterialWeight(float materialWeight) {
        this.materialWeight = materialWeight;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public List<String> getUseIds() {
        return useIds;
    }

    public void setUseIds(List<String> useIds) {
        this.useIds = useIds;
    }

    public List<String> getAttrValueIds() {
        return attrValueIds;
    }

    public void setAttrValueIds(List<String> attrValueIds) {
        this.attrValueIds = attrValueIds;
    }

    public GoodsMaterial getMaterial() {
        return material;
    }

    public void setMaterial(GoodsMaterial material) {
        this.material = material;
    }

    public Vender getVender() {
        return vender;
    }

    public void setVender(Vender vender) {
        this.vender = vender;
    }

    public GoodsStyle getStyle() {
        return style;
    }

    public void setStyle(GoodsStyle style) {
        this.style = style;
    }

    public List<GoodsUse> getUses() {
        return uses;
    }

    public void setUses(List<GoodsUse> uses) {
        this.uses = uses;
    }

    public List<GoodsAttrSpec> getAttrSpecs() {
        return attrSpecs;
    }

    public void setAttrSpecs(List<GoodsAttrSpec> attrSpecs) {
        this.attrSpecs = attrSpecs;
    }

    public List<GoodsSku> getSkus() {
        return skus;
    }

    public void setSkus(List<GoodsSku> skus) {
        this.skus = skus;
    }
}


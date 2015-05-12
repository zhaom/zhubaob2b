package com.zhubao.b2b.platform.model;

import java.io.Serializable;
import java.util.List;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;
import com.zhubao.b2b.platform.entity.GoodsAttrSpec;
import com.zhubao.b2b.platform.entity.GoodsPrice;

/**
 * User: xiaoping lu
 * Date: 13-9-3
 * Time: 下午1:59
 */
@Entity(value = "order_goods", noClassnameStored = true)
public class OrderGoods implements Serializable {

	private static final long serialVersionUID = -5591788635214831823L;
	//主键，IdFactory产生
	@Id
    private String id;
    //所属订单主键id
    @Indexed
    private String orderId;
    //对应商品的主键id，来源于商品
    @Indexed
    private String goodsId;
    //对应商品的sku主键id，来源于商品sku
    @Indexed
    private String goodsSkuId;
    //商品编码，商家自定义的货号，来源于商品
    @Indexed
    private String code;
    //商品名称，来源于商品
    private String name;
    //商品主图，来源于商品
    private String coverImg;
    //商品的材质id，来源于商品
    private String materialId;
    //商品的款式id，来源于商品
    private String styleId;
    //商品的主题id列表，来源于商品
    private List<String> useIds;
    //商品的属性id列表，来源于商品
    private List<String> attrValueIds;
    //所选的商品sku，来源于商品
    private GoodsSku goodsSku;
    //数量，用户填写
    private int amount;
    //单个商品的估重，来源商品，对于计价商品有意义
    private float valuedWeight;
    //称重的实重，卖家填写
    private float materialWeight;
    //已经结价的重量，买家填写的累计
    private float balanceWeight;
    //特殊要求，买家在发货状态前填写
    private String specNeed;
    //商品计价列表，来源于商品
    private List<GoodsPrice> prices;
    //是否定价商品，1表示定价商品，0表示计价商品
    private int isFixedPrice;
    //价格小计明细
    @Transient
    private List<GoodsPrice> orderGoodsPrice;
    //价格小计
    @Transient
    private float subSumPrice;
    //价格类型，暂时未用
    @Transient
    private String priceType;
    //商品的主题详细信息
    @Transient
    private List<GoodsUse> uses;
    //商品的材质详细信息
    @Transient
    private GoodsMaterial material;
    //商品的供应商信息
    @Transient
    private Vender vender;
    //商品的款式信息
    @Transient
    private GoodsStyle style;
    //商品的属性信息
    @Transient
    private List<GoodsAttrSpec> attrSpecs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
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

    public GoodsSku getGoodsSku() {
        return goodsSku;
    }

    public void setGoodsSku(GoodsSku goodsSku) {
        this.goodsSku = goodsSku;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getBalanceWeight() {
        return balanceWeight;
    }

    public void setBalanceWeight(float balanceWeight) {
        this.balanceWeight = balanceWeight;
    }

    public float getMaterialWeight() {
        return materialWeight;
    }

    public void setMaterialWeight(float materialWeight) {
        this.materialWeight = materialWeight;
    }

    public String getSpecNeed() {
        return specNeed;
    }

    public void setSpecNeed(String specNeed) {
        this.specNeed = specNeed;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public List<GoodsPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<GoodsPrice> prices) {
        this.prices = prices;
    }

    public List<GoodsUse> getUses() {
        return uses;
    }

    public void setUses(List<GoodsUse> uses) {
        this.uses = uses;
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

    public List<GoodsAttrSpec> getAttrSpecs() {
        return attrSpecs;
    }

    public void setAttrSpecs(List<GoodsAttrSpec> attrSpecs) {
        this.attrSpecs = attrSpecs;
    }

    public int getIsFixedPrice() {
        return this.isFixedPrice;
    }

    public void setIsFixedPrice(int isFixedPrice) {
        this.isFixedPrice = isFixedPrice;
    }

    public float getValuedWeight() {
        return valuedWeight;
    }

    public void setValuedWeight(float valuedWeight) {
        this.valuedWeight = valuedWeight;
    }

    public List<GoodsPrice> getOrderGoodsPrice() {
        return orderGoodsPrice;
    }

    public void setOrderGoodsPrice(List<GoodsPrice> orderGoodsPrice) {
        this.orderGoodsPrice = orderGoodsPrice;
    }

    public float getSubSumPrice() {
        float subSum = 0.00f;
        for (GoodsPrice priceEntry : orderGoodsPrice){
            subSum += priceEntry.getPrice();
        }
        return subSum;
    }

    public void setSubSumPrice(float subSumPrice) {
        this.subSumPrice = subSumPrice;
    }
}

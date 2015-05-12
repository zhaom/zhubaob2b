package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;
import com.zhubao.b2b.platform.entity.ShopCartGoodsGroupItem;

import java.io.Serializable;
import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-3
 * Time: 下午5:38
 */
@Entity(value = "shop_cart", noClassnameStored = true)
public class ShopCart implements Serializable {

	private static final long serialVersionUID = -2472465750674562363L;
	
	@Id
    private String id;
    @Indexed
    private String customerId;
    private long createTime;

    @Transient
    private List<ShopCartGoodsGroupItem> cartGoodsGroupItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<ShopCartGoodsGroupItem> getCartGoodsGroupItems() {
        return cartGoodsGroupItems;
    }

    public void setCartGoodsGroupItems(List<ShopCartGoodsGroupItem> cartGoodsGroupItems) {
        this.cartGoodsGroupItems = cartGoodsGroupItems;
    }
}

package com.zhubao.b2b.platform.entity;

import com.zhubao.b2b.platform.model.ShopCartGoods;
import com.zhubao.b2b.platform.model.Vender;

import java.io.Serializable;
import java.util.List;

public class ShopCartGoodsGroupItem implements Serializable {

    private Vender vender;
    private List<ShopCartGoods> cartGoodsList;
    private int amount;
    private float totalPrice;

    public Vender getVender() {
        return vender;
    }

    public void setVender(Vender vender) {
        this.vender = vender;
    }

    public List<ShopCartGoods> getCartGoodsList() {
        return cartGoodsList;
    }

    public void setCartGoodsList(List<ShopCartGoods> cartGoodsList) {
        this.cartGoodsList = cartGoodsList;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}

package com.zhubao.b2b.platform.entity;

import java.io.Serializable;

/**
 * User: xiaoping lu
 * Date: 13-9-9
 * Time: 上午8:52
 */
public class GoodsPrice implements Serializable {

    private String type;
    private String desc;
    private float price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

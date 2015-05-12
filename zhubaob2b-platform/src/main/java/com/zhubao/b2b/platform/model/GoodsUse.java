package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import java.io.Serializable;

/**
 * User: xiaoping lu
 * Date: 13-9-22
 * Time: 下午4:41
 */
@Entity(value = "goods_use", noClassnameStored = true)
public class GoodsUse implements Serializable {

    private static final long serialVersionUID = 5836698664554111293L;

    @Id
    private String id;
    private String name;
    private int orderNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}

package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import java.io.Serializable;

/**
 * User: xiaoping lu
 * Date: 13-9-22
 * Time: 下午4:46
 */
@Entity(value = "goods_style", noClassnameStored = true)
public class GoodsStyle implements Serializable {

    private static final long serialVersionUID = 2276880838383862226L;

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

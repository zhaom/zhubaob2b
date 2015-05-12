package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "goods_sales", noClassnameStored = true)
public class GoodsSales implements Serializable {

	private static final long serialVersionUID = 8726680664071534388L;
	
	@Id
    private String id;
    @Indexed
    private String orderId;

    private String type;
    @Indexed
    private String venderId;

    private String status;

    private long createdDate;
    @Transient
    private List<GoodsSalesItem> items;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public List<GoodsSalesItem> getItems() {
        return items;
    }

    public void setItems(List<GoodsSalesItem> items) {
        this.items = items;
    }
}

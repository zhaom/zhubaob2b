package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.zhubao.b2b.platform.entity.DemandOrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "demand_flow", noClassnameStored = true)
public class DemandOrderFlow implements Serializable {

	private static final long serialVersionUID = -5901549148287742979L;
	
	@Id
    private String id;
    @Indexed
    private String orderId;
    @Indexed
    private String kshopOrderId;

    private long createdDate;

    private String status;

    private List<DemandOrderItem> items;

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

    public String getKshopOrderId() {
        return kshopOrderId;
    }

    public void setKshopOrderId(String kshopOrderId) {
        this.kshopOrderId = kshopOrderId;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DemandOrderItem> getItems() {
        return items;
    }

    public void setItems(List<DemandOrderItem> items) {
        this.items = items;
    }
}

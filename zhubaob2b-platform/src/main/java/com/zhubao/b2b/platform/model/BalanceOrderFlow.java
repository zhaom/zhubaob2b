package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.zhubao.b2b.platform.entity.BalanceOrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "balance_flow", noClassnameStored = true)
public class BalanceOrderFlow implements Serializable {

	private static final long serialVersionUID = 1587067370786201318L;
	
	@Id
    private String id;
    @Indexed
    private String orderId;
    @Indexed
    private String kshopOrderId;

    private long createdDate;

    private String status;

    private List<BalanceOrderItem> items;

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

    public List<BalanceOrderItem> getItems() {
        return items;
    }

    public void setItems(List<BalanceOrderItem> items) {
        this.items = items;
    }
}

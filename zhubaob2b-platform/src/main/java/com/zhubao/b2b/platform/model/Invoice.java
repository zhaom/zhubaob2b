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
@Entity(value = "invoice", noClassnameStored = true)
public class Invoice implements Serializable {

	private static final long serialVersionUID = 3549581940523012805L;
	
	@Id
    private String id;
    @Indexed
    private String orderId;
    @Indexed
    private String customerId;
    @Indexed
    private String venderId;

    private String venderName;

    private String status;

    private long createdDate;

    private ShipAddress addr;

    @Transient
    private List<InvoiceItem> item;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
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

    public ShipAddress getAddr() {
        return addr;
    }

    public void setAddr(ShipAddress addr) {
        this.addr = addr;
    }

    public List<InvoiceItem> getItem() {
        return item;
    }

    public void setItem(List<InvoiceItem> item) {
        this.item = item;
    }
}

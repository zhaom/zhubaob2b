package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;
import com.zhubao.b2b.platform.entity.BuybackApplyItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
@Entity(value = "buybackapply", noClassnameStored = true)
public class BuybackApply {

    @Id
    private String id;
    @Indexed
    private String customerId;
    @Transient
    private Customer customer;
    @Indexed
    private String venderId;
    @Transient
    private Vender vender;

    private String applyTime;

    private List<BuybackApplyItem> items;

    private long createdTime;

    private String status;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public Vender getVender() {
        return vender;
    }

    public void setVender(Vender vender) {
        this.vender = vender;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public List<BuybackApplyItem> getItems() {
        return items;
    }

    public void setItems(List<BuybackApplyItem> items) {
        this.items = items;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "order_status", noClassnameStored = true)
public class OrderStatus implements Serializable {

	private static final long serialVersionUID = -6699946367835950608L;
	
	@Id
    String id;
    @Indexed
    String orderId;

    String oldstatus;

    String newstatus;

    String desc;

    String user;

    String usercate;

    String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOldstatus() {
        return oldstatus;
    }

    public void setOldstatus(String oldstatus) {
        this.oldstatus = oldstatus;
    }

    public String getNewstatus() {
        return newstatus;
    }

    public void setNewstatus(String newstatus) {
        this.newstatus = newstatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsercate() {
        return usercate;
    }

    public void setUsercate(String usercate) {
        this.usercate = usercate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}

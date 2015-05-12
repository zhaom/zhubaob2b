package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "custpayway", noClassnameStored = true)
public class CustPayway implements Serializable {

	private static final long serialVersionUID = -8433848093941124743L;
	
	@Id
    private String id;
    @Indexed
    private String customerid;

    private Payway payway;

    private String status;

    private long lastupdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public Payway getPayway() {
        return payway;
    }

    public void setPayway(Payway payway) {
        this.payway = payway;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(long lastupdate) {
        this.lastupdate = lastupdate;
    }
}

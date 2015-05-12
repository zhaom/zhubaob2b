package com.zhubao.b2b.platform.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class KShopAgencyAccount implements Serializable {

    private String agencyId;
    private double total;
    private double thawy;
    private double frozen;

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getThawy() {
        return thawy;
    }

    public void setThawy(double thawy) {
        this.thawy = thawy;
    }

    public double getFrozen() {
        return frozen;
    }

    public void setFrozen(double frozen) {
        this.frozen = frozen;
    }
}

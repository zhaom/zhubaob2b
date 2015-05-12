package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-1-30
 * Time: 上午11:03
 * To change this template use File | Settings | File Templates.
 */

@Entity(value = "agency", noClassnameStored = true)
public class KshopAgency {
    @Id
    private String kshopAgencyId;

    private String kshopAgencyGradeName;

    private String kshopAgencyName;

    private String kshopAgencyParentId;

    private String kshopAgencyStatus;

    public String getKshopAgencyId() {
        return kshopAgencyId;
    }

    public void setKshopAgencyId(String kshopAgencyId) {
        this.kshopAgencyId = kshopAgencyId;
    }

    public String getKshopAgencyGradeName() {
        return kshopAgencyGradeName;
    }

    public void setKshopAgencyGradeName(String kshopAgencyGradeName) {
        this.kshopAgencyGradeName = kshopAgencyGradeName;
    }

    public String getKshopAgencyName() {
        return kshopAgencyName;
    }

    public void setKshopAgencyName(String kshopAgencyName) {
        this.kshopAgencyName = kshopAgencyName;
    }

    public String getKshopAgencyParentId() {
        return kshopAgencyParentId;
    }

    public void setKshopAgencyParentId(String kshopAgencyParentId) {
        this.kshopAgencyParentId = kshopAgencyParentId;
    }

    public String getKshopAgencyStatus() {
        return kshopAgencyStatus;
    }

    public void setKshopAgencyStatus(String kshopAgencyStatus) {
        this.kshopAgencyStatus = kshopAgencyStatus;
    }
}

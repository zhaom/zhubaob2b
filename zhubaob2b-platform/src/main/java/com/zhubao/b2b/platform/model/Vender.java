package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;

import java.io.Serializable;

@Entity(value = "vender", noClassnameStored = true)
public class Vender implements Serializable {

	private static final long serialVersionUID = -3382387325391443015L;
	
	@Id
    private String id;
    @Indexed
    private String kshopUserId;
    private String kshopUserTel;
    private String kshopUserEmail;
    private String kshopToken;
    private String kshopUserName;
    @Indexed
    private String kshopAgencyId;
    private String kshopUserRegDate;
    private String kshopUserNickName;
    @Transient
    private KshopAgency agency;
    @Transient
    private String kshopAgencyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKshopUserId() {
        return kshopUserId;
    }

    public void setKshopUserId(String kshopUserId) {
        this.kshopUserId = kshopUserId;
    }

    public String getKshopUserTel() {
        return kshopUserTel;
    }

    public void setKshopUserTel(String kshopUserTel) {
        this.kshopUserTel = kshopUserTel;
    }

    public String getKshopUserEmail() {
        return kshopUserEmail;
    }

    public void setKshopUserEmail(String kshopUserEmail) {
        this.kshopUserEmail = kshopUserEmail;
    }

    public String getKshopToken() {
        return kshopToken;
    }

    public void setKshopToken(String kshopToken) {
        this.kshopToken = kshopToken;
    }

    public String getKshopUserName() {
        return kshopUserName;
    }

    public void setKshopUserName(String kshopUserName) {
        this.kshopUserName = kshopUserName;
    }

    public String getKshopAgencyId() {
        return kshopAgencyId;
    }

    public void setKshopAgencyId(String kshopAgencyId) {
        this.kshopAgencyId = kshopAgencyId;
    }

    public String getKshopUserRegDate() {
        return kshopUserRegDate;
    }

    public void setKshopUserRegDate(String kshopUserRegDate) {
        this.kshopUserRegDate = kshopUserRegDate;
    }

    public String getKshopUserNickName() {
        return kshopUserNickName;
    }

    public void setKshopUserNickName(String kshopUserNickName) {
        this.kshopUserNickName = kshopUserNickName;
    }

    public KshopAgency getAgency() {
        return agency;
    }

    public void setAgency(KshopAgency agency) {
        this.agency = agency;
    }

    public String getKshopAgencyName() {
        return agency != null? agency.getKshopAgencyName() : null;
    }

    public void setKshopAgencyName(String kshopAgencyName) {
        this.kshopAgencyName = kshopAgencyName;
    }
}

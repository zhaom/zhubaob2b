package com.zhubao.b2b.platform.entity;

import java.io.Serializable;

/**
 * User: xiaoping lu
 * Date: 13-10-23
 * Time: 上午9:55
 */
public class KShopUser implements Serializable {

    private String name;
    private String tel;
    private String email;
    private String token;
    private String userId;
    private String userName;
    private String agencyId;
    private String agencyGradeName;
    private String agencyName;
    private String userRegDate;
    private String userNickName;
    private String agencyParentId;
    private String agencyStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyGradeName() {
        return agencyGradeName;
    }

    public void setAgencyGradeName(String agencyGradeName) {
        this.agencyGradeName = agencyGradeName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getUserRegDate() {
        return userRegDate;
    }

    public void setUserRegDate(String userRegDate) {
        this.userRegDate = userRegDate;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getAgencyParentId() {
        return agencyParentId;
    }

    public void setAgencyParentId(String agencyParentId) {
        this.agencyParentId = agencyParentId;
    }

    public String getAgencyStatus() {
        return agencyStatus;
    }

    public void setAgencyStatus(String agencyStatus) {
        this.agencyStatus = agencyStatus;
    }
}

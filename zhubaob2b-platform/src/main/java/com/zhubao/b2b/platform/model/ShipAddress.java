package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "ship_address", noClassnameStored = true)
public class ShipAddress implements Serializable {

	private static final long serialVersionUID = -3766420821815684963L;
	
	@Id
	private String id;
    @Indexed
    private String customerId;
    private String contactUser;
    private String mobile;
    private String tel;
    private String email;
    private String address;
    private String postCode;
    private boolean isDefault;

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
    @JsonProperty("contact")
    public String getContactUser() {
        return contactUser;
    }
    @JsonProperty("contact")
    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }
    @JsonProperty("mobile")
    public String getMobile() {
        return mobile;
    }
    @JsonProperty("mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @JsonProperty("tel")
    public String getTel() {
        return tel;
    }
    @JsonProperty("tel")
    public void setTel(String tel) {
        this.tel = tel;
    }
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }
    @JsonProperty("addr")
    public String getAddress() {
        return address;
    }
    @JsonProperty("addr")
    public void setAddress(String address) {
        this.address = address;
    }
    @JsonProperty("postcode")
    public String getPostCode() {
        return postCode;
    }
    @JsonProperty("postcode")
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}

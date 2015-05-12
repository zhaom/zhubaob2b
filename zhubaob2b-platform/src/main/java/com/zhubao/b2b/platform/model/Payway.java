package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "payway", noClassnameStored = true)
public class Payway implements Serializable {

	private static final long serialVersionUID = -5302270425395492447L;
	
	@Id
    private String id;
    private String name;
    private String desc;
    private String type;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

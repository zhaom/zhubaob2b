package com.zhubao.b2b.platform.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-1-19
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
public class StatusInfo {

    private String status;

    private String desc;

    private Date updateTime;

    private long limitInHour;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getLimitInHour() {
        return limitInHour;
    }

    public void setLimitInHour(long limitInHour) {
        this.limitInHour = limitInHour;
    }
}

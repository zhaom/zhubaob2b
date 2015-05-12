package com.zhubao.b2b.platform.entry;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-3-4
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
public class SettleAccountCreateParams {

    private String userId;
    private String venderId;
    private long beginTime;
    private long endTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}

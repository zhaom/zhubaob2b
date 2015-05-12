package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Transient;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
@Entity(value = "settle", noClassnameStored = true)
public class SettleAccount {
    //主键，IdFactory产生
    @Id
    private String id;
    //卖家id，create时带入
    private String venderId;
    //卖家机构名称，从venderid得到，create时写入
    private String venderAgencyName;
    //结算周期开始时间
    private long beginTime;
    //结算周期结束时间
    private long endTime;
    //交易额，create时从Item累计得到
    private float tradeVolume;
    //卖家应收，create时从Item累计得到
    private float receivables;
    //交易佣金，create时从item累计得到
    private float commission;
    //服务费，create时从item累计得到
    private float serviceFee;
    //状态，见
    private int status;
    //状态描述
    private String statusDesc;
    //本单据产生时间
    private long createdTime;
    //check时间，对应实际已经结算标示
    private long checkTime;
    //明细
    @Transient
    private List<SettleAccountItem> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getVenderAgencyName() {
        return venderAgencyName;
    }

    public void setVenderAgencyName(String venderAgencyName) {
        this.venderAgencyName = venderAgencyName;
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

    public float getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(float tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public float getReceivables() {
        return receivables;
    }

    public void setReceivables(float receivables) {
        this.receivables = receivables;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public float getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(float serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public List<SettleAccountItem> getItems() {
        return items;
    }

    public void setItems(List<SettleAccountItem> items) {
        this.items = items;
    }
}

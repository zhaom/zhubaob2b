package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;
import com.zhubao.b2b.platform.entity.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(value = "order", noClassnameStored = true)
public class Order implements Serializable {

	private static final long serialVersionUID = -8907379369796851870L;
	//主键，IdFactory产生
	@Id
    private String id;
    //用户id
    @Indexed
    private String customerId;
    //卖家id
    @Indexed
    private String venderId;
    //卖家名字
    private String venderName;
    //订单当前状态
    private String status;
    //订单当前状态详细信息
    private StatusInfo statusInfo;
    //订单结价状态，WAIT_BALANCE为待结价，BALANCED为已经完成结价
    private String balanceStatus;
    //收货人信息
    private ShipAddress address;
    //付款方式信息
    private Payway payway;
    //生成时间
    private long createTime;
    //最后变更时间
    private long lastupdateTime;
    //付款时间
    private long payTime;
    //付款信息，包括付款时金价、付款分类小计信息
    private PayPrice payPrice;
    //订单商品明细
    @Transient
    private List<OrderGoods> goods;
    //订单分类统计价格信息
    @Transient
    private List<GoodsPrice> orderPrice;
    //订单总金额
    private float orderSumPrice;
    //用户详细信息
    @Transient
    private Customer customer;
    //卖家详细信息
    @Transient
    private Vender vender;
    //结价统计信息
    @Transient
    private BalanceSum balanceSum;


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

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(String balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShipAddress getAddress() {
        return address;
    }

    public void setAddress(ShipAddress address) {
        this.address = address;
    }

    public Payway getPayway() {
        return payway;
    }

    public void setPayway(Payway payway) {
        this.payway = payway;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<OrderGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<OrderGoods> goods) {
        this.goods = goods;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public StatusInfo getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(StatusInfo statusInfo) {
        this.statusInfo = statusInfo;
    }

    public long getLastupdateTime() {
        return lastupdateTime;
    }

    public void setLastupdateTime(long lastupdateTime) {
        this.lastupdateTime = lastupdateTime;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public PayPrice getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(PayPrice payPrice) {
        this.payPrice = payPrice;
    }

    public List<GoodsPrice> getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(List<GoodsPrice> orderPrice) {
        this.orderPrice = orderPrice;
    }

    public float getOrderSumPrice() {
        float sumPrice = 0.00f;
        for(GoodsPrice priceEntry : orderPrice){
            sumPrice += priceEntry.getPrice();
        }
        return sumPrice;
    }

    public void setOrderSumPrice(float orderSumPrice) {
        this.orderSumPrice = orderSumPrice;
    }

    public Vender getVender() {
        return vender;
    }

    public void setVender(Vender vender) {
        this.vender = vender;
    }

    public BalanceSum getBalanceSum() {
        return balanceSum;
    }

    public void setBalanceSum(BalanceSum balanceSum) {
        this.balanceSum = balanceSum;
    }
}

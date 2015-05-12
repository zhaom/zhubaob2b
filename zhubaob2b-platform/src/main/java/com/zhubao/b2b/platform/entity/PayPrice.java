package com.zhubao.b2b.platform.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-1-19
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public class PayPrice {

    private KShopAgencyPrice curPrice;

    private List<GoodsPrice> sumTotalPrice;

    public KShopAgencyPrice getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(KShopAgencyPrice curPrice) {
        this.curPrice = curPrice;
    }

    public List<GoodsPrice> getSumTotalPrice() {
        return sumTotalPrice;
    }

    public void setSumTotalPrice(List<GoodsPrice> sumTotalPrice) {
        this.sumTotalPrice = sumTotalPrice;
    }
}

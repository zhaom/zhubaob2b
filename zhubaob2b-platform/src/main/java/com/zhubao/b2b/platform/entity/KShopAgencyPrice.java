package com.zhubao.b2b.platform.entity;

import com.zhubao.b2b.common.utils.Constants;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class KShopAgencyPrice implements Serializable {

    private double ADD_AG_SELL;             //白银加价
    private double ADD_AU_SELL;             //黄金加价
    private double AU_SELL;                  //黄金基准价
    private double AU_BUY;                   //黄金回购加价，负值表示加价为负
    private double PT_SELL;                  //铂金基准价
    private double ADD_PT_SELL;             //铂金加价
    private double AG_SELL;                 //白银加价

    public double getADD_AG_SELL() {
        return ADD_AG_SELL;
    }

    public void setADD_AG_SELL(double ADD_AG_SELL) {
        this.ADD_AG_SELL = ADD_AG_SELL;
    }

    public double getADD_AU_SELL() {
        return ADD_AU_SELL;
    }

    public void setADD_AU_SELL(double ADD_AU_SELL) {
        this.ADD_AU_SELL = ADD_AU_SELL;
    }

    public double getAU_SELL() {
        return AU_SELL;
    }

    public void setAU_SELL(double AU_SELL) {
        this.AU_SELL = AU_SELL;
    }

    public double getAU_BUY() {
        return AU_BUY;
    }

    public void setAU_BUY(double AU_BUY) {
        this.AU_BUY = AU_BUY;
    }

    public double getPT_SELL() {
        return PT_SELL;
    }

    public void setPT_SELL(double PT_SELL) {
        this.PT_SELL = PT_SELL;
    }

    public double getADD_PT_SELL() {
        return ADD_PT_SELL;
    }

    public void setADD_PT_SELL(double ADD_PT_SELL) {
        this.ADD_PT_SELL = ADD_PT_SELL;
    }

    public double getAG_SELL() {
        return AG_SELL;
    }

    public void setAG_SELL(double AG_SELL) {
        this.AG_SELL = AG_SELL;
    }

    public double getMaterialPrice(String materialId){
        if (Constants.GOODS_MATERIAL_AG.equalsIgnoreCase(materialId)) {
            return ADD_AG_SELL + AG_SELL;
        }else if (Constants.GOODS_MATERIAL_AU.equalsIgnoreCase(materialId)) {
            return ADD_AU_SELL + AU_SELL;
        }else if (Constants.GOODS_MATERIAL_PT.equalsIgnoreCase(materialId)) {
            return ADD_PT_SELL + PT_SELL;
        }else{
            return 0d;
        }
    }
}

package com.zhubao.b2b.platform.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
public class BalanceSum implements Serializable {

    private float totalWeight;

    private float balancedWeight;

    private float unbalancedWeight;

    private float unbalancedAmount;

    public float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public float getBalancedWeight() {
        return balancedWeight;
    }

    public void setBalancedWeight(float balancedWeight) {
        this.balancedWeight = balancedWeight;
    }

    public float getUnbalancedWeight() {
        return unbalancedWeight;
    }

    public void setUnbalancedWeight(float unbalancedWeight) {
        this.unbalancedWeight = unbalancedWeight;
    }

    public float getUnbalancedAmount() {
        return unbalancedAmount;
    }

    public void setUnbalancedAmount(float unbalancedAmount) {
        this.unbalancedAmount = unbalancedAmount;
    }
}

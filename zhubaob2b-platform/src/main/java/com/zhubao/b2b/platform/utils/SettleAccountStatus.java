package com.zhubao.b2b.platform.utils;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-2-27
 * Time: 下午4:42
 * To change this template use File | Settings | File Templates.
 */
public enum SettleAccountStatus {

    INIT(0,"待确认"),
    CONFIRMED(5,"已确认"),
    CHECKED(10,"已结算"),
    DELETED(-1,"已删除");

    private int value;
    private String desc;

    SettleAccountStatus(int value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public int getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }
}

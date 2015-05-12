package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.GoodsSales;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface GoodsSalesDAO {

    public GoodsSales insert(GoodsSales goodsSales);

    public GoodsSales getById(String id);

    public List<GoodsSales> getByOrderId(String orderId);

}

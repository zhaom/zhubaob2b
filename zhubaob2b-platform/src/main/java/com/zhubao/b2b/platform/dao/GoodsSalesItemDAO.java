package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.GoodsSalesItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface GoodsSalesItemDAO {

    public GoodsSalesItem insert(GoodsSalesItem goodsSalesItem);

    public List<GoodsSalesItem> getBySalesId(String salesId);
}

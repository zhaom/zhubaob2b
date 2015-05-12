package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.SettleAccountItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
public interface SettleAccountItemDAO {

    public void insert(SettleAccountItem settleAccountItem);

    public List<SettleAccountItem> listBySettleAccountId(String settleAmountId);

    public void removeBySettleId(String id);
}

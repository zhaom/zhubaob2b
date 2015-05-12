package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.entry.BuybackQueryParameter;
import com.zhubao.b2b.platform.model.BuybackApply;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
public interface BuybackApplyDAO {

    public String create(BuybackApply buybackApply);

    public BuybackApply select(String id);

    public List<BuybackApply> listByVender(String venderId);

    public List<BuybackApply> listByCustomer(String customerId);

    public BuybackApply deleteApply(String applyId);

    public List<BuybackApply> selectPageList(BuybackQueryParameter param, int start, int range);

    public Integer count(BuybackQueryParameter queryParameter);
}

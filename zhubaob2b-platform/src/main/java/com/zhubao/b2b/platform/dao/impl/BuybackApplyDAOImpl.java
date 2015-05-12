package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.dao.BuybackApplyDAO;
import com.zhubao.b2b.platform.entry.BuybackQueryParameter;
import com.zhubao.b2b.platform.model.BuybackApply;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
public class BuybackApplyDAOImpl extends BasicDAOSupport implements BuybackApplyDAO {
    @Override
    public String create(BuybackApply buybackApply) {
        getDatastore().save(buybackApply);
        return buybackApply.getId();
    }

    @Override
    public BuybackApply select(String id) {
        return getDatastore().get(BuybackApply.class, id);
    }

    @Override
    public List<BuybackApply> listByVender(String venderId) {
        return getDatastore().find(BuybackApply.class).field("venderId").equal(venderId).field("status").notEqual(Constants.STATUS_SHOW+"").order("-createdTime").asList();
    }

    @Override
    public List<BuybackApply> listByCustomer(String customerId) {
        return getDatastore().find(BuybackApply.class).field("customerId").equal(customerId).order("-createdTime").asList();
    }

    @Override
    public BuybackApply deleteApply(String applyId) {
        BuybackApply buybackApply = getDatastore().get(BuybackApply.class, applyId);
        buybackApply.setStatus(Constants.STATUS_DELETE+"");
        getDatastore().save(buybackApply);
        return buybackApply;
    }

    @Override
    public List<BuybackApply> selectPageList(BuybackQueryParameter param, int start, int range) {
        return param.buildQuery(getDatastore()).offset(start).limit(range).order("-createdTime").asList();
    }

    @Override
    public Integer count(BuybackQueryParameter queryParameter) {
        return ((Long) getDatastore().getCount(queryParameter.buildQuery(getDatastore()))).intValue();
    }
}

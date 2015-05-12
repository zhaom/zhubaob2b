package com.zhubao.b2b.platform.entry;


import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.model.BuybackApply;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 13-12-3
 */
public class BuybackQueryParameter implements QueryParameter {

    private final static Logger logger = LoggerFactory.getLogger(BuybackQueryParameter.class);

    private String status;

    private String customerId;

    private String venderId;

    public Query<BuybackApply> buildQuery(Datastore datastore) {
        Query<BuybackApply> buybackApplyQuery = datastore.createQuery(BuybackApply.class);
        if(StringUtils.isNotEmpty(status) && !status.equalsIgnoreCase(Constants.SCOPE_ALL)){
            buybackApplyQuery.field("status").equal(status);
        }
        if(StringUtils.isNotEmpty(customerId) && !customerId.equalsIgnoreCase(Constants.SCOPE_ALL)){
            buybackApplyQuery.field("customerId").equal(customerId);
        }
        if(StringUtils.isNotEmpty(venderId) && !venderId.equalsIgnoreCase(Constants.SCOPE_ALL)){
            buybackApplyQuery.field("venderId").equal(venderId);
        }
        return buybackApplyQuery;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String getOrderBy() {
        return null;
    }
}

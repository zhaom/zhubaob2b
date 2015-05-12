package com.zhubao.b2b.platform.entry;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.model.Order;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;

/**
 * User: xiaoping lu
 * Date: 13-9-9
 * Time: 下午5:42
 */
public class OrderQueryParameter implements Serializable, QueryParameter<Order> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String status;
    private String venderId;
    private String customerId;
    private String conditionTime;
    private String keyword;

    private String orderBy;

    public Query<Order> buildQuery(Datastore datastore) {
        Query<com.zhubao.b2b.platform.model.Order> query = datastore.createQuery(com.zhubao.b2b.platform.model.Order.class);

        if (StringUtils.isNotEmpty(venderId) && !venderId.equalsIgnoreCase(Constants.SCOPE_ALL)) {
            query.field("venderId").equal(venderId);
        }
        if (StringUtils.isNotEmpty(status) && !status.equalsIgnoreCase(Constants.SCOPE_ALL)) {
            if (status.indexOf(",") < 0) {
                query.field("status").equal(status);
            } else {
                String[] statusArray = StringUtils.split(status, ",");
                query.field("status").in(Arrays.asList(statusArray));
            }
        }
        if (StringUtils.isNotEmpty(customerId) && !customerId.equalsIgnoreCase(Constants.SCOPE_ALL)) {
            query.field("customerId").equal(customerId);
        }
        if (StringUtils.isNotEmpty(conditionTime) && !conditionTime.equalsIgnoreCase(Constants.SCOPE_ALL)) {
            long currentDt = System.currentTimeMillis();
            if (conditionTime.equalsIgnoreCase("1")) {
                query.field("createTime").greaterThan(currentDt - (1000 * 60 * 60 * 24 * 7L));
            } else if (conditionTime.equalsIgnoreCase("2")) {
                query.field("createTime").greaterThan(currentDt - (1000 * 60 * 60 * 24 * 90L));
            } else {
                query.field("createTime").greaterThan(currentDt - (1000 * 60 * 60 * 24 * 365L));
            }
        }
        logger.debug("query:[{}]", query.toString());
        return query;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getConditionTime() {
        return conditionTime;
    }

    public void setConditionTime(String conditionTime) {
        this.conditionTime = conditionTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    @Override
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}

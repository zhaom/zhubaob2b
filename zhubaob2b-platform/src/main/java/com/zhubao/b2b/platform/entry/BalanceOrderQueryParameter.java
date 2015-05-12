package com.zhubao.b2b.platform.entry;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-1-3
 * Time: 下午5:25
 * To change this template use File | Settings | File Templates.
 */
public class BalanceOrderQueryParameter implements Serializable, QueryParameter<Order> {

    private final static Logger logger = LoggerFactory.getLogger(BalanceOrderQueryParameter.class);

    private OrderQueryParameter orderQueryParameter;

    private boolean balanceEnded;

    public Query<Order> buildQuery(Datastore datastore) {
        Query<Order> orderQuery = orderQueryParameter.buildQuery(datastore);
        if(balanceEnded){
            orderQuery.field("balanceStatus").equal(Constants.BALANCE_ORDER_STATUS_ENDED);
        }else {
            orderQuery.or(
                    orderQuery.criteria("balanceStatus").doesNotExist(),
                    orderQuery.criteria("balanceStatus").notEqual(Constants.BALANCE_ORDER_STATUS_ENDED)
            );
        }
        return orderQuery;
    }

    public OrderQueryParameter getOrderQueryParameter() {
        return orderQueryParameter;
    }

    public void setOrderQueryParameter(OrderQueryParameter orderQueryParameter) {
        this.orderQueryParameter = orderQueryParameter;
    }

    public boolean isBalanceEnded() {
        return balanceEnded;
    }

    public void setBalanceEnded(boolean balanceEnded) {
        this.balanceEnded = balanceEnded;
    }

    @Override
    public String getOrderBy() {
        return orderQueryParameter.getOrderBy();
    }
}

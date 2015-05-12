package com.zhubao.b2b.platform.entry;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.zhubao.b2b.platform.model.Order;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-1-3
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
public interface QueryParameter<E> {

    public <E> Query<E> buildQuery(Datastore datastore);

    public String getOrderBy();
}

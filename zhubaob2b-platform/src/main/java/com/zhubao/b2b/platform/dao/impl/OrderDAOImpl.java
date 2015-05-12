package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.zhubao.b2b.platform.dao.OrderDAO;
import com.zhubao.b2b.platform.entry.OrderQueryParameter;
import com.zhubao.b2b.platform.entry.QueryParameter;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.model.OrderGoods;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 上午11:37
 */
public class OrderDAOImpl extends BasicDAOSupport implements OrderDAO {

    private static Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);

    @Override
    public void insert(Order order) {
        getDatastore().save(order);
    }

    @Override
    public List<Order> selectListByCustomerUserId(String userId) {
        return getDatastore().find(Order.class).field("customerId").equal(userId).order("-createTime").asList();
    }

    @Override
    public List<Order> selectPageList(QueryParameter<Order> param, int start, int range) {
        Query<Order> q = param.buildQuery(getDatastore());
        if(param instanceof OrderQueryParameter){
            String keyword = ((OrderQueryParameter) param).getKeyword();
            if(StringUtils.isNotEmpty(keyword)){
                List<OrderGoods> orderGoodsList = getDatastore().find(OrderGoods.class).field("name").containsIgnoreCase(keyword).retrievedFields(true, "orderId").asList();
                Set<String> idSet = new HashSet<String>();
                idSet.add("-1");
                for(OrderGoods entry:orderGoodsList){
                    idSet.add(entry.getOrderId());
                }
                Query<Order> query = getDatastore().createQuery(Order.class);
                q.or(query.criteria(Mapper.ID_KEY).containsIgnoreCase(keyword), query.criteria(Mapper.ID_KEY).in(idSet));
            }
        }
        if(StringUtils.isNotEmpty(param.getOrderBy())){
            return q.offset(start).limit(range).order(param.getOrderBy()).asList();
        }
        return q.offset(start).limit(range).order("-createTime").asList();
    }

    @Override
    public Integer count(QueryParameter<Order> param) {
        Query<Order> orderQuery = param.buildQuery(getDatastore());
        if(param instanceof OrderQueryParameter){
            String keyword = ((OrderQueryParameter) param).getKeyword();
            if(StringUtils.isNotEmpty(keyword)){
                List<OrderGoods> orderGoodsList = getDatastore().find(OrderGoods.class).field("name").containsIgnoreCase(keyword).retrievedFields(true, "orderId").asList();
                Set<String> idSet = new HashSet<String>();
                idSet.add("-1");
                for(OrderGoods entry:orderGoodsList){
                    idSet.add(entry.getOrderId());
                }
                Query<Order> query = getDatastore().createQuery(Order.class);
                orderQuery.or(query.criteria(Mapper.ID_KEY).containsIgnoreCase(keyword), query.criteria(Mapper.ID_KEY).in(idSet));
            }
        }
        logger.debug("count order by query:[{}]", orderQuery.toString());
        return ((Long) getDatastore().getCount(orderQuery)).intValue();
    }

    @Override
    public Order select(String orderId) {
        return getDatastore().find(Order.class).field(Mapper.ID_KEY).equal(orderId).get();
    }

    @Override
    public void update(Order order) {
    }

    @Override
    public List<Order> selectListByVenderId(String venderId) {
        return getDatastore().find(Order.class).field("venderId").equal(venderId).order("-createTime").asList();
    }

    @Override
    public List<Order> selectListByVenderIdPayTime(String venderId, long beginTime, long endTime) {
        return getDatastore().find(Order.class).field("venderId").equal(venderId).field("payTime").greaterThanOrEq(beginTime).field("payTime").lessThan(endTime).order("-createTime").asList();
    }
}

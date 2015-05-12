package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.platform.entry.OrderQueryParameter;
import com.zhubao.b2b.platform.model.BalanceOrderFlow;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.model.OrderStatus;
import com.zhubao.b2b.platform.model.Payway;
import com.zhubao.common.utils.Pagination;

import java.util.List;
import java.util.Map;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 上午11:42
 */
public interface OrderService {

    @Deprecated
    public ServiceResult<String> createOrder(Order order);

    public ServiceResult<List<Order>> getOrdersByCustomerUserId(String userId);

    public ServiceResult<Pagination<Order>> getPaginationOrders(String userId, OrderQueryParameter param, int page, int pageSize);

    public ServiceResult<Order> getOrder(String userId, String orderId);

    public ServiceResult<List<String>> createOrderFromShopcart(String userId, List<String> shopcartGoodsIds);

    public ServiceResult<Order> updateOrderItemCount(String userId, String orderId, String orderGoodsId, int count);

    public ServiceResult<Order> deleteOrderItem(String userId, String orderId, String orderGoodsId);

    public ServiceResult<Order> updateOrderItemWeight(String userId, String orderId, String orderGoodsId, float weight);

    public ServiceResult<Order> modifyPayway(String userId, String orderId, String payway);

    public ServiceResult<Order> modifyShipmentAddr(String userId, String orderId, String addr);

    public ServiceResult<Map<String, Integer>> countByStatus(String userId);

    public ServiceResult<Map<String, Integer>> countByStatusVender(String venderId);

    public ServiceResult<Map<String, Integer>> countByBalancestatus(String userId);

    @Deprecated
    public ServiceResult<List<Order>> queryOrder(String userId, String status, String venderId, String qtime, String pIndex, String pCount);

    public ServiceResult<List<OrderStatus>> getOrderStatus(String userId, String orderId);

    public ServiceResult<Order> weightedOrder(String userId, String orderId);

    public ServiceResult<Order> shipOrder(String userId, String orderId);

    public ServiceResult<Order> confirmOrder(String userId, String orderId);

    public ServiceResult<Order> cancelOrder(String userId, String orderId);

    public ServiceResult<Order> updateOrderItemReq(String userId, String orderId, String orderGoodsId, String req);

    public ServiceResult<Map<String, List<Payway>>> listOrderPayway(String userId, String orderId);

    public ServiceResult<Pagination<Order>> getUnbalanceOrderByUserId(String userId, int pageIndex, int countPerPage);

    public ServiceResult<Pagination<Order>> getBalancedOrderByUserId(String userId, int pageIndex, int countPerPage);

    public ServiceResult<Order> balancePay(String userId, String payPassword, String orderId, List<String> orderGoodsIdArray, List<Float> weightArray);

    public ServiceResult<List<BalanceOrderFlow>> listBalanceOrderFlow(String userId, String orderId);

    public ServiceResult<Order> payOrder(String userId, String passwd, String orderId, String mobile, String vcode);

    public ServiceResult<List<Order>> getWaitOrdersByVender(String userId);

    /**
     * 结算中使用
     * @param userId      用户id
     * @param venderId   供应商机构id
     * @param beginTime
     * @param endTime
     * @return
     */
    public ServiceResult<List<Order>> selectListByVenderIdPayTime(String userId, String venderId, long beginTime, long endTime);
}

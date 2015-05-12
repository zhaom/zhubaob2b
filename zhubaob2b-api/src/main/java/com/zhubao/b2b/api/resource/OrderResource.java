package com.zhubao.b2b.api.resource;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.common.utils.Pagination;
import com.zhubao.b2b.platform.entry.OrderQueryParameter;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.model.OrderStatus;
import com.zhubao.b2b.platform.model.Payway;
import com.zhubao.b2b.platform.service.OrderService;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */

@Path("/api")
public class OrderResource {

    private static final Logger logger = LoggerFactory.getLogger(OrderResource.class);

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @POST
    @Path("/1/order")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@HeaderParam("uid") String userId, @FormParam("gids") String shopcartGoodsIds) {
        logger.debug("[{}] create one order from shopcart with [{}]", new Object[]{userId, shopcartGoodsIds});
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(shopcartGoodsIds))) {
            logger.error("all params required,but some one empty.[{}][{}][{}]", new Object[]{userId, shopcartGoodsIds});
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        String[] ids = StringUtils.tokenizeToStringArray(shopcartGoodsIds, ",");
        ServiceResult<List<String>> result = orderService.createOrderFromShopcart(userId, Arrays.asList(ids));
        if (!result.isSuccess()) {
            logger.error("create order failed,fatal error");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @GET
    @Path("/1/order/my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response myOrders(@HeaderParam("uid") String userId) {
        logger.debug("[{}] query order list", userId);
        if (!StringUtils.hasLength(userId)) {
            logger.error("userid is required");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<List<Order>> result = orderService.getOrdersByCustomerUserId(userId);
        if (!result.isSuccess()) {
            logger.debug("user has no order or some fatal occur!");
            return Response.status(HttpStatus.SC_NO_CONTENT).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @GET
    @Path("/1/order/list/vender_doing/{vid}_{pindex}_{pcount}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response venderOrders(@HeaderParam("uid") String userId,@PathParam("vid")String venderId,@PathParam("pindex")String pIndex, @PathParam("pcount")String pCount){
        logger.debug("[{}] query wait vender done orders", userId);
        if(!StringUtils.hasLength(userId)){
            logger.debug("uid is required");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        OrderQueryParameter orderQueryParameter = new OrderQueryParameter();
        orderQueryParameter.setConditionTime(Constants.SCOPE_ALL);
        orderQueryParameter.setStatus(Constants.ORDER_STATUS_WEIGHTING+","+Constants.ORDER_STATUS_WAIT_WEIGHT+","+Constants.ORDER_STATUS_WAIT_DELIVERY);
        orderQueryParameter.setVenderId(venderId);
        orderQueryParameter.setCustomerId(Constants.SCOPE_ALL);
        if (!(StringUtils.hasLength(pIndex))) {
            pIndex = "0";
        }
        if (!(StringUtils.hasLength(pCount))) {
            pCount = "20";
        }
        ServiceResult<Pagination<Order>> result = orderService.getPaginationOrders(userId, orderQueryParameter, Integer.parseInt(pIndex), Integer.parseInt(pCount));
        if(!result.isSuccess()){
            logger.error("query wait order by vender but some fatal occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @GET
    @Path("/1/order/list/vender_all/{vid}_{pindex}_{pcount}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response venderOrdersAll(@HeaderParam("uid") String userId,@PathParam("vid")String venderId,@PathParam("pindex")String pIndex, @PathParam("pcount")String pCount){
        logger.debug("[{}] query vender:[{}] all orders", userId, venderId);
        if(!StringUtils.hasLength(userId) || !StringUtils.hasLength(venderId)){
            logger.debug("uid and vid are required");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        OrderQueryParameter orderQueryParameter = new OrderQueryParameter();
        orderQueryParameter.setConditionTime(Constants.SCOPE_ALL);
        orderQueryParameter.setStatus(Constants.SCOPE_ALL);
        orderQueryParameter.setVenderId(venderId);
        orderQueryParameter.setCustomerId(Constants.SCOPE_ALL);
        if (!(StringUtils.hasLength(pIndex))) {
            pIndex = "0";
        }
        if (!(StringUtils.hasLength(pCount))) {
            pCount = "20";
        }
        ServiceResult<Pagination<Order>> result = orderService.getPaginationOrders(userId, orderQueryParameter, Integer.parseInt(pIndex), Integer.parseInt(pCount));

        if(!result.isSuccess()){
            logger.error("query all order by vender but some fatal occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @GET
    @Path("/1/order/detail/{oid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderDetail(@HeaderParam("uid") String userId, @PathParam("oid") String orderId) {
        logger.debug("[{}] query order detail of [{}]", userId, orderId);
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(orderId))) {
            logger.error("all params required,but some one empty.[{}][{}]", new Object[]{userId, orderId});
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Order> result = orderService.getOrder(userId, orderId);
        if (!result.isSuccess()) {
            logger.debug("not an valid id or some fatal occur!");
            return Response.status(HttpStatus.SC_NO_CONTENT).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @POST
    @Path("/1/order/{oid}/goods/item")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyItem(@HeaderParam("uid") String userId, @PathParam("oid") String orderId, @FormParam("ogid") String orderGoodsId, @FormParam("count") int count) {
        logger.debug("[{}] modify [{}] count:[{}] in order:[{}]", new Object[]{userId, orderGoodsId, count, orderId});
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(orderId) && StringUtils.hasLength(orderGoodsId) && count > 0)) {
            logger.error("userid,ogid,oid is required and count should be greater than 0");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Order> result = orderService.updateOrderItemCount(userId, orderId, orderGoodsId, count);
        if (!result.isSuccess()) {
            logger.error("update order item count,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @DELETE
    @Path("/1/order/{oid}/goods/item/{ogid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItem(@HeaderParam("uid") String userId, @PathParam("oid") String orderId, @PathParam("ogid") String orderGoodsId) {
        logger.debug("[{}] delete [{}].[{}] from order:[{}]", new Object[]{userId, orderGoodsId, orderId});
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(orderId) && StringUtils.hasLength(orderGoodsId))) {
            logger.error("all params are required,but some are empty");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Order> result = orderService.deleteOrderItem(userId, orderId, orderGoodsId);
        if (!result.isSuccess()) {
            logger.error("delete order item,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @POST
    @Path("/1/order/{oid}/goods/item/weight")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response weightItem(@HeaderParam("uid") String userId, @PathParam("oid") String orderId, @FormParam("ogid") String orderGoodsId, @FormParam("weight") float weight) {
        logger.debug("[{}] modify [{}].[{}] weight:[{}] in order:[{}]", new Object[]{userId, orderGoodsId, weight, orderId});
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(orderId) && StringUtils.hasLength(orderGoodsId) && weight > 0f)) {
            logger.error("userid,orderGoodsId is required and weight should be greater than 0");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Order> result = orderService.updateOrderItemWeight(userId, orderId, orderGoodsId, weight);
        if (!result.isSuccess()) {
            logger.error("update order item weight,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @POST
    @Path("/1/order/{oid}/goods/item/req")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recordItemReq(@HeaderParam("uid") String userId, @PathParam("oid") String orderId, @FormParam("ogid") String orderGoodsId, @FormParam("req") String req) {
        logger.debug("[{}] modify [{}].[{}] special requirement:[{}] in order:[{}]", new Object[]{userId, orderGoodsId, req, orderId});
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(orderId) && StringUtils.hasLength(orderGoodsId) && StringUtils.hasLength(req))) {
            logger.error("all params are required");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Order> result = orderService.updateOrderItemReq(userId, orderId, orderGoodsId, req);
        if (!result.isSuccess()) {
            logger.error("update order item weight,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @POST
    @Path("/1/order/{oid}/status")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(@HeaderParam("uid") String userId, @PathParam("oid") String orderId, @FormParam("status") String newStatus) {
        logger.debug("[{}] update order:[{}] to status:[{}]", new Object[]{userId, orderId, newStatus});
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(orderId) && StringUtils.hasLength(newStatus))) {
            logger.error("all params are required, but some are empty");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Order> result = null;
        if (Constants.ORDER_STATUS_WAIT_WEIGHT.equalsIgnoreCase(newStatus)) {
            logger.error("no mean request,there should no order need changed to be WAIT WEIGHT");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        if (Constants.ORDER_STATUS_WEIGHTING.equalsIgnoreCase(newStatus)) {
            logger.error("no mean request,there should no order need changed to be WEIGHTING");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        if (Constants.ORDER_STATUS_WEIGHTED.equalsIgnoreCase(newStatus)) {
            //todo:后台商家称重完成
            result = orderService.weightedOrder(userId, orderId);
        }
        if (Constants.ORDER_STATUS_WAIT_DELIVERY.equalsIgnoreCase(newStatus)) {
            //TODO:客户支付完成，待发货，风控套保请求，单独的支付接口来处理
            logger.error("no mean request,there should no order need changed to be WEIGHTING");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        if (Constants.ORDER_STATUS_WAIT_CONFIRM.equalsIgnoreCase(newStatus)) {
            //Todo:后台商家发货后，等待客户确认，发货时要生成单独的发货单
            result = orderService.shipOrder(userId, orderId);
        }
        if (Constants.ORDER_STATUS_ENDED.equalsIgnoreCase(newStatus)) {
            //TODO:客户确认收货，订单完成，
            result = orderService.confirmOrder(userId, orderId);
        }
        if (Constants.ORDER_STATUS_CANCELED.equalsIgnoreCase(newStatus)) {
            //TODO:客户取消订单，订单完成，可以由待称重、称重中、完成称重两个状态而来，后一状态下要解锁库存。
            result = orderService.cancelOrder(userId, orderId);
        }
        if (result != null && !result.isSuccess()) {
            logger.error("some fatal error occured");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @POST
    @Path("/1/order/{oid}/payway")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderPayway(@HeaderParam("uid") String userId, @PathParam("oid") String orderId, @FormParam("payway") String payway) {
        logger.debug("[{}] update order:[{}], set payway:[{}]", new Object[]{userId, orderId, payway});
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(orderId) && StringUtils.hasLength(payway))) {
            logger.error("all params are required, but some are empty");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Order> result = orderService.modifyPayway(userId, orderId, payway);
        if (!result.isSuccess()) {
            logger.error("update order payway,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }


    @POST
    @Path("/1/order/{oid}/addr")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderAddr(@HeaderParam("uid") String userId, @PathParam("oid") String orderId, @FormParam("addr") String addr) {
        logger.debug("[{}] update order:[{}], set addr:[{}]", new Object[]{userId, orderId, addr});
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(orderId) && StringUtils.hasLength(addr))) {
            logger.error("all params are required, but some are empty");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Order> result = orderService.modifyShipmentAddr(userId, orderId, addr);
        if (!result.isSuccess()) {
            logger.error("update order shipment addr,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @POST
    @Path("/2/order/{oid}/pay")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response payOrder(@HeaderParam("uid") String userId, @FormParam("passwd") String passwd, @PathParam("oid") String orderId, @FormParam("mobile") String mobile, @FormParam("vcode") String vcode) {
        logger.debug("[{}] pay order:[{}]", userId, orderId);
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(passwd) && StringUtils.hasLength(orderId) && StringUtils.hasLength(mobile) && StringUtils.hasLength(vcode))) {
            logger.error("all params are required, but some are empty");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Order> serviceResult = orderService.payOrder(userId, passwd, orderId, mobile, vcode);
        if (!serviceResult.isSuccess()) {
            logger.error("pay order,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(serviceResult.getErrorMessage()).build();
        }
        return Response.ok(serviceResult.getValue()).build();
    }

    @GET
    @Path("/1/order/status/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countOrderByStatus(@HeaderParam("uid") String userId) {
        logger.debug("count order by status,user:[{}]", userId);
        if (!(StringUtils.hasLength(userId))) {
            logger.error("userid is required, but now is empty");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Map<String, Integer>> result = orderService.countByStatus(userId);
        if (!result.isSuccess()) {
            logger.error("query order count by status,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }


    @GET
    @Path("/1/order/list/{status}_{venderid}_{time}_{pindex}_{pcount}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryOrder(@HeaderParam("uid") String userId, @PathParam("status") String status, @PathParam("venderid") String venderId, @PathParam("time") String qtime, @PathParam("pindex") String pIndex, @PathParam("pcount") String pCount) {
        logger.debug("[{}] query orders,with status:[{}],venderid:[{}],time:[{}],pindex:[{}],skip:[{}]", new Object[]{userId, status, venderId, qtime, pIndex, pCount});
        if (!(StringUtils.hasLength(userId))) {
            logger.error("userid is required, but now is empty");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        if (!StringUtils.hasLength(status)) {
            status = Constants.SCOPE_ALL;
        }
        if (!(StringUtils.hasLength(venderId))) {
            venderId = Constants.SCOPE_ALL;
        }
        if (!(StringUtils.hasLength(qtime))) {
            qtime = Constants.SCOPE_ALL;
        } else {
            if (qtime.equalsIgnoreCase("1") || qtime.equalsIgnoreCase("2") || qtime.equalsIgnoreCase("3")) {
                logger.debug("valid time condition:[{}]", qtime);
            } else {
                qtime = Constants.SCOPE_ALL;
            }
        }
        if (!(StringUtils.hasLength(pIndex))) {
            pIndex = "0";
        }
        if (!(StringUtils.hasLength(pCount))) {
            pCount = "20";
        }
        OrderQueryParameter orderQueryParameter = new OrderQueryParameter();
        orderQueryParameter.setConditionTime(qtime);
        orderQueryParameter.setStatus(status);
        orderQueryParameter.setVenderId(venderId);
        orderQueryParameter.setCustomerId(userId);

        ServiceResult<Pagination<Order>> result = orderService.getPaginationOrders(userId, orderQueryParameter, Integer.parseInt(pIndex), Integer.parseInt(pCount));
        if (!result.isSuccess()) {
            logger.error("query order list,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }


    @GET
    @Path("/1/order/{oid}/status/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listOrderStatus(@HeaderParam("uid") String userId, @PathParam("oid") String orderId) {
        logger.debug("[{}] trace order[{}] status", userId, orderId);
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(orderId))) {
            logger.error("userid and orderid are required, but now all are empty");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<List<OrderStatus>> result = orderService.getOrderStatus(userId, orderId);
        if (!result.isSuccess()) {
            logger.error("query order status list,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @GET
    @Path("/1/order/{oid}/payway/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listOrderPayway(@HeaderParam("uid") String userId, @PathParam("oid") String orderId) {
        logger.debug("[{}] request order:[{}] payway", userId, orderId);
        if (!(StringUtils.hasLength(userId) && StringUtils.hasLength(orderId))) {
            logger.error("userid and orderid are required, but now all are empty");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Map<String, List<Payway>>> result = orderService.listOrderPayway(userId, orderId);
        if (!result.isSuccess()) {
            logger.error("query order payway list,but some fatal error occur!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

}

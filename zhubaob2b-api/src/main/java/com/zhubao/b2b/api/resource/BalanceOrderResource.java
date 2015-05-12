package com.zhubao.b2b.api.resource;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.platform.model.BalanceOrderFlow;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.service.OrderService;
import com.zhubao.common.utils.Pagination;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Path("/api")
public class BalanceOrderResource {

    private static final Logger logger = LoggerFactory.getLogger(BalanceOrderResource.class);

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    @Path("/1/order/balance/unlist/{pindex}_{pcount}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnbalanceOrders(@HeaderParam("uid") String userId, @PathParam("pindex") String pIndex, @PathParam("pcount") String pCount) {
        logger.debug("[{}] query unbalance order list", userId);
        if (StringUtils.isEmpty(userId)) {
            logger.error("header param uid is required");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Pagination<Order>> result = orderService.getUnbalanceOrderByUserId(userId, Integer.parseInt(pIndex), Integer.parseInt(pCount));
        if (!result.isSuccess()) {
            logger.error("create order failed,fatal error");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @Path("/1/order/balance/list/{pindex}_{pcount}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBalancedOrders(@HeaderParam("uid") String userId, @PathParam("pindex") String pIndex, @PathParam("pcount") String pCount) {
        logger.debug("[{}] query balanced order list", userId);
        if (StringUtils.isEmpty(userId)) {
            logger.error("header param uid is required");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Pagination<Order>> result = orderService.getBalancedOrderByUserId(userId, Integer.parseInt(pIndex), Integer.parseInt(pCount));
        if (!result.isSuccess()) {
            logger.error("create order failed,fatal error");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @Path("/1/order/{oid}/balance/detail")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBalanceDetail(@HeaderParam("uid") String userId, @PathParam("oid") String orderId) {
        logger.debug("[{}] get balance order [{}] detail", userId, orderId);
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(orderId)) {
            logger.error("header param uid and path param oid are required");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<Order> result = orderService.getOrder(userId, orderId);
        if (!result.isSuccess()) {
            logger.error("create order failed,fatal error");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @Path("/1/order/{oid}/balance/pay")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response balancePay(@HeaderParam("uid") String userId, @FormParam("paypasswd") String payPassword, @PathParam("oid") String orderId, @FormParam("ogids") String orderGoodsIds, @FormParam("weights") String weights) {
        logger.debug("[{}] balance pay,order:[{}] goods:[{}],balance weights:[{}]", new Object[]{userId, orderId, orderGoodsIds, weights});
        String[] orderGoodsIdArray = null;
        Float[] weightArray = null;
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(payPassword) || StringUtils.isEmpty(orderId) || StringUtils.isEmpty(orderGoodsIds) || StringUtils.isEmpty(weights)) {
            logger.error("all params are required, but some one empty");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        } else {
            orderGoodsIdArray = StringUtils.split(orderGoodsIds, ",");
            String[] weightA = StringUtils.split(weights, ",");
            if (orderGoodsIdArray.length != weightA.length) {
                logger.error("goods id and weight values should all have equal length.");
                return Response.status(HttpStatus.SC_BAD_REQUEST).build();
            }
            for (int i = 0, j = weightA.length; i < j; i++) {
                weightArray[i] = Float.parseFloat(weightA[i]);
            }
        }
        ServiceResult<Order> result = orderService.balancePay(userId, payPassword, orderId, Arrays.asList(orderGoodsIdArray), Arrays.asList(weightArray));
        if (!result.isSuccess()) {
            logger.error("balance pay order:[{}] failed,fatal error!", orderId);
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @Path("/1/order/{oid}/balance/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderBalanceFlow(@HeaderParam("uid") String userId, @PathParam("oid") String orderId) {
        logger.debug("[{}] request order[{}] balance history flow", userId, orderId);
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(orderId)) {
            logger.error("uid and oid are all required");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        ServiceResult<List<BalanceOrderFlow>> serviceResult = orderService.listBalanceOrderFlow(userId, orderId);
        if (!serviceResult.isSuccess()) {
            logger.error("request order[{}] balance flow failed, fatal error", orderId);
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(serviceResult.getErrorMessage()).build();
        }
        return Response.ok(serviceResult.getValue()).build();
    }


}

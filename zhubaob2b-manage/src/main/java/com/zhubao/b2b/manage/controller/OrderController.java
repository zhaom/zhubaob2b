package com.zhubao.b2b.manage.controller;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.manage.templateUtils.Long2Date;
import com.zhubao.b2b.platform.entry.OrderQueryParameter;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.model.OrderStatus;
import com.zhubao.b2b.platform.model.Vender;
import com.zhubao.b2b.platform.service.OrderService;
import com.zhubao.common.utils.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/manage/order")
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderService orderService;

    @RequestMapping(value = "/list.do")
    public String list(HttpServletRequest request, Model model,
                       @RequestParam(value = "s", required = false, defaultValue = "all") String status,
                       @RequestParam(value = "ct",required = false, defaultValue = "all")String conditionTime,
                       @RequestParam(value = "k",required = false, defaultValue = "")String keyword,
                       @RequestParam(value = "o", required = false, defaultValue = "-createTime")String orderBy,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.debug("request order list with status:[{}], conditionTime:[{}], keyword:[{}], orderby:[{}]",new Object[]{status, conditionTime, keyword, orderBy});
        OrderQueryParameter param = new OrderQueryParameter();
        Vender vender = (Vender) request.getAttribute("vender");
        param.setVenderId(vender.getKshopAgencyId());
        param.setStatus(status);
        param.setConditionTime(conditionTime);
        param.setKeyword(keyword);
        param.setOrderBy(orderBy);

        Map<String, Integer> orderMap = orderService.countByStatusVender(vender.getKshopAgencyId()).getValue();

        ServiceResult<Pagination<Order>> result = orderService.getPaginationOrders(vender.getId(), param, page, pageSize);
        model.addAttribute("pagination", result.getValue());
        model.addAttribute("orderMap", orderMap);
        model.addAttribute("s", status);
        model.addAttribute("ct", conditionTime);
        model.addAttribute("k", keyword);
        model.addAttribute("o", orderBy);
        model.addAttribute("timer", new Long2Date());
        return "order/list";
    }

    @RequestMapping(value = "/detail.do")
    public String detailOrder(HttpServletRequest request, Model model,
                    @RequestParam(value = "oid", required = true)String orderId) {
        logger.debug("request order detail by id:[{}]", orderId);
        Vender vender = (Vender)request.getAttribute("vender");
        Order order = orderService.getOrder(vender.getId(), orderId).getValue();
        List<OrderStatus> statusList = orderService.getOrderStatus(vender.getId(), orderId).getValue();
        model.addAttribute("order", order);
        model.addAttribute("statusList", statusList);
        model.addAttribute("timer", new Long2Date());
        if(Constants.ORDER_STATUS_WAIT_WEIGHT.equalsIgnoreCase(order.getStatus()) || Constants.ORDER_STATUS_WEIGHTING.equalsIgnoreCase(order.getStatus())){
            return "order/weightDetail";
        }else if(Constants.ORDER_STATUS_WAIT_DELIVERY.equalsIgnoreCase(order.getStatus())){
            return "order/shopDetail";
        }
        return "order/orderDetail";
    }

    @RequestMapping(value = "/weightedOrder.do")
    public String weightedOrder(HttpServletRequest request, Model model, @RequestParam(value = "oid", required = true, defaultValue = "") String orderId) {
        Vender vender = (Vender) request.getAttribute("vender");
        orderService.weightedOrder(vender.getId(), orderId);
        model.addAttribute("message", "保存成功,订单已处于待付款状态");
        return "opt_ok";
    }

    @RequestMapping(value = "/shipOrder.do")
    public String shipOrder(HttpServletRequest request, Model model, @RequestParam(value = "oid", required = true, defaultValue = "") String orderId) {
        Vender vender = (Vender) request.getAttribute("vender");
        orderService.shipOrder(vender.getId(), orderId);
        model.addAttribute("message", "保存成功,订单已处于已发货状态");
        return "opt_ok";
    }

    @RequestMapping(value = "/listGoods.do")
    public String listGoods(Model model, @RequestParam(value = "orderId", required = true, defaultValue = "") String orderId) {
        model.addAttribute("order", orderService.getOrder(null, orderId).getValue());
        return "order/list_goods";
    }

    @RequestMapping(value = "/modifyWeight.do")
    public String modifyGoodsMaterialWeight(HttpServletRequest request, Model model,
                                            @RequestParam(value = "oid", required = true, defaultValue = "") String orderId,
                                            @RequestParam(value = "gid", required = true, defaultValue = "") String orderGoodsId,
                                            @RequestParam(value = "weight", required = true, defaultValue = "") float materialWeight) {
        logger.debug("modify weight value,oid:[{}] gid:[{}], weight:[{}]", new Object[]{orderId, orderGoodsId, materialWeight});
        Vender vender = (Vender) request.getAttribute("vender");
        orderService.updateOrderItemWeight(vender.getId(), orderId, orderGoodsId, materialWeight);
        model.addAttribute("order", orderService.getOrder(vender.getId(), orderId).getValue());
        List<OrderStatus> statusList = orderService.getOrderStatus(vender.getId(), orderId).getValue();
        model.addAttribute("statusList", statusList);
        model.addAttribute("timer", new Long2Date());
        model.addAttribute("message", "保存成功,订单已处于称重中状态");
        return "opt_ok";
    }
}

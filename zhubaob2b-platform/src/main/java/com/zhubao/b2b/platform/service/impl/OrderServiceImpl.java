package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.dao.*;
import com.zhubao.b2b.platform.entity.*;
import com.zhubao.b2b.platform.entry.BalanceOrderQueryParameter;
import com.zhubao.b2b.platform.entry.OrderQueryParameter;
import com.zhubao.b2b.platform.model.*;
import com.zhubao.b2b.platform.service.GoodsService;
import com.zhubao.b2b.platform.service.OrderService;
import com.zhubao.b2b.platform.service.ShopCartService;
import com.zhubao.b2b.platform.service.UserService;
import com.zhubao.b2b.platform.utils.AttributeSpecTranslateUtils;
import com.zhubao.b2b.platform.utils.KShopHelper;
import com.zhubao.common.utils.Pagination;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 上午11:44
 */
public class OrderServiceImpl extends BasicServiceSupport implements OrderService {

    private OrderDAO orderDAO;

    private OrderGoodsDAO orderGoodsDAO;

    private ShopCartService shopCartService;

    private ShopCartGoodsDAO shopCartGoodsDAO;

    private ShipAddressDAO shipAddressDAO;

    private OrderStatusDAO orderStatusDAO;

    private PaywayDAO paywayDAO;

    private CustPaywayDAO custPaywayDAO;

    private BalanceOrderFlowDAO balanceOrderFlowDAO;

    private DemandOrderFlowDAO demandOrderFlowDAO;

    private CustomerDAO customerDAO;

    private VenderDAO venderDAO;

    private GoodsSkuDAO goodsSkuDAO;

    private GoodsSalesDAO goodsSalesDAO;

    private GoodsSalesItemDAO goodsSalesItemDAO;

    private InvoiceDAO invoiceDAO;

    private InvoiceItemDAO invoiceItemDAO;

    private KShopHelper kshopHelper;

    private GoodsService goodsService;

    private AttributeSpecTranslateUtils attributeSpecTranslateUtils;

    private UserService userService;

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setOrderGoodsDAO(OrderGoodsDAO orderGoodsDAO) {
        this.orderGoodsDAO = orderGoodsDAO;
    }

    public void setShopCartService(ShopCartService shopCartService) {
        this.shopCartService = shopCartService;
    }

    public void setAttributeSpecTranslateUtils(AttributeSpecTranslateUtils attributeSpecTranslateUtils) {
        this.attributeSpecTranslateUtils = attributeSpecTranslateUtils;
    }

    public void setShopCartGoodsDAO(ShopCartGoodsDAO shopCartGoodsDAO) {
        this.shopCartGoodsDAO = shopCartGoodsDAO;
    }

    public void setShipAddressDAO(ShipAddressDAO shipAddressDAO) {
        this.shipAddressDAO = shipAddressDAO;
    }

    public void setOrderStatusDAO(OrderStatusDAO orderStatusDAO) {
        this.orderStatusDAO = orderStatusDAO;
    }

    public void setPaywayDAO(PaywayDAO paywayDAO) {
        this.paywayDAO = paywayDAO;
    }

    public void setKshopHelper(KShopHelper kshopHelper) {
        this.kshopHelper = kshopHelper;
    }

    public void setCustPaywayDAO(CustPaywayDAO custPaywayDAO) {
        this.custPaywayDAO = custPaywayDAO;
    }

    public void setBalanceOrderFlowDAO(BalanceOrderFlowDAO balanceOrderFlowDAO) {
        this.balanceOrderFlowDAO = balanceOrderFlowDAO;
    }

    public void setDemandOrderFlowDAO(DemandOrderFlowDAO demandOrderFlowDAO) {
        this.demandOrderFlowDAO = demandOrderFlowDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void setVenderDAO(VenderDAO venderDAO) {
        this.venderDAO = venderDAO;
    }

    public void setGoodsSkuDAO(GoodsSkuDAO goodsSkuDAO) {
        this.goodsSkuDAO = goodsSkuDAO;
    }

    public void setGoodsSalesDAO(GoodsSalesDAO goodsSalesDAO) {
        this.goodsSalesDAO = goodsSalesDAO;
    }

    public void setGoodsSalesItemDAO(GoodsSalesItemDAO goodsSalesItemDAO) {
        this.goodsSalesItemDAO = goodsSalesItemDAO;
    }

    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public void setInvoiceItemDAO(InvoiceItemDAO invoiceItemDAO) {
        this.invoiceItemDAO = invoiceItemDAO;
    }

    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Deprecated
    public ServiceResult<String> createOrder(Order order) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            orderDAO.insert(order);
            serviceResult.setSuccess(true);
            serviceResult.setValue(order.getId());
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Pagination<Order>> getPaginationOrders(String userId, OrderQueryParameter param, int page, int pageSize) {
        ServiceResult<Pagination<Order>> serviceResult = new ServiceResult<Pagination<Order>>();
        try {
            //如果是零售商查询，查询自己的订单，如果是一级批发商用户查询，查询自己agency作为供应商的订单，如果是超级用户，放开
            Customer customer = customerDAO.select(userId);
            if(customer != null){
                param.setCustomerId(userId);
            }
            Vender vender = venderDAO.select(userId);
            if(vender != null){
                param.setVenderId(vender.getKshopAgencyId());
            }
            if(customer == null && vender == null && !userService.isSuperUser(userId)){
                logger.error("buyer/vender and superuser can query order,but this userid is invalid");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }

            Pagination pagination = new Pagination(page, pageSize);
            int totalCount = orderDAO.count(param).intValue();
            pagination.setTotalRecord(totalCount);

            int start = 0;
            int range = 10;
            start = (pagination.getPage() - 1) * pagination.getRange();
            range = pagination.getRange();


            List<Order> list = orderDAO.selectPageList(param, start, range);
            for(Order entry:list){
                entry.setGoods(orderGoodsDAO.getByOrderId(entry.getId()));
                setValuedOrderPrice(entry);
                setPayOrderPrice(entry);
                setGoodsSkuSpec(entry);
                entry.setCustomer(userService.getCustomer(entry.getCustomerId()));
                entry.setVender(userService.getVender(entry.getVenderId()));
            }
            pagination.setData(list);
            serviceResult.setSuccess(true);
            serviceResult.setValue(pagination);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    private void setGoodsSkuSpec(Order entry) {
        List<OrderGoods> orderGoodsList = entry.getGoods();
        if(orderGoodsList != null){
            for(OrderGoods orderGoods: orderGoodsList){
                GoodsSku sku = orderGoods.getGoodsSku();
                if(sku != null){
                    List<String> skuAttrValueIds = sku.getSkuAttrValueIds();
                    skuAttrValueIds.remove("0");
                    sku.setSkuAttrSpecs(attributeSpecTranslateUtils.translateGoodsSkuAttrValueIdsToSkuSpecs(sku.getSkuAttrValueIds()));
                }
            }
        }
    }

    /**
     * set subTotal price info in order-goods,set total price info in order,
     * the values based on materialWeight or valuedWeight in order-goods and the price realtime get from hk
     * @param entry order with detail info
     */
    private void setValuedOrderPrice(Order entry) {
        Customer customer = entry.getCustomer();
        String token = "3057bd103d334c79a64d165193ea771d";
        if(customer != null){
            token = customer.getKshopToken();
        }
        KShopAgencyPrice agentPrice = kshopHelper.doGetPrice(token);
        List<OrderGoods> orderGoodsList = entry.getGoods();
        Map<String, GoodsPrice> priceMap = new HashMap<String, GoodsPrice>();

        for(OrderGoods orderGoods: orderGoodsList){
            List<GoodsPrice> goodsPriceList = orderGoods.getPrices();
            if(goodsPriceList != null){
                List<GoodsPrice> orderGoodsPriceList = new ArrayList<GoodsPrice>();

                for(GoodsPrice goodsPrice:goodsPriceList){
                    GoodsPrice newPrice = new GoodsPrice();
                    newPrice.setType(goodsPrice.getType());
                    newPrice.setDesc(goodsPrice.getDesc());
                    if(newPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_FIXED_PRICE) || newPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_FIXED_MANUALFEE) || newPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_FIXED_MATERIALFEE)){
                        newPrice.setPrice(goodsPrice.getPrice() * orderGoods.getAmount());
                    } else if(newPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_VALUATED_MANUALFEE)) {
                        if(orderGoods.getMaterialWeight() > 0.001f){
                            newPrice.setPrice(goodsPrice.getPrice() * orderGoods.getMaterialWeight());
                        } else {
                            newPrice.setPrice(goodsPrice.getPrice() * orderGoods.getValuedWeight() * orderGoods.getAmount());
                        }
                    } else if(newPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_VALUATED_MATERIALFEE)){
                        double price = agentPrice.getMaterialPrice(orderGoods.getMaterialId());
                        if(orderGoods.getMaterialWeight() > 0.001f){
                            newPrice.setPrice(orderGoods.getMaterialWeight() * (float)price);
                        }else {
                            newPrice.setPrice(orderGoods.getValuedWeight() * (float)price * orderGoods.getAmount());
                        }
                    }
                    newPrice.setPrice((float)(Math.round(newPrice.getPrice()*100))/100);
                    if(priceMap.containsKey(newPrice.getType())){
                        GoodsPrice orderPrice = priceMap.get(newPrice.getType());
                        orderPrice.setPrice(orderPrice.getPrice() + newPrice.getPrice());
                    }else {
                        priceMap.put(newPrice.getType(), newPrice);
                    }
                    orderGoodsPriceList.add(newPrice);
                }
                orderGoods.setOrderGoodsPrice(orderGoodsPriceList);
            }
        }
        List<GoodsPrice> listPrice = new ArrayList<GoodsPrice>();
        listPrice.addAll(priceMap.values());
        entry.setOrderPrice(listPrice);
    }

    /**
     * set subTotal price info in order-goods,set total price info in order,
     * the values based on materialWeight in order-goods and price when pay
     * @param order with detail info
     */
    private void setPayOrderPrice(Order order){
        if(order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_ENDED) || order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WAIT_CONFIRM) || order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WAIT_DELIVERY)){
            if(order.getPayPrice() != null){
                PayPrice payPrice = order.getPayPrice();
                KShopAgencyPrice agencyPrice = payPrice.getCurPrice();

                List<OrderGoods> orderGoodsList = order.getGoods();
                Map<String, GoodsPrice> priceMap = new HashMap<String, GoodsPrice>();

                for(OrderGoods orderGoods: orderGoodsList){
                    List<GoodsPrice> goodsPriceList = orderGoods.getPrices();
                    if(goodsPriceList != null){
                        List<GoodsPrice> orderGoodsPriceList = new ArrayList<GoodsPrice>();

                        for(GoodsPrice goodsPrice:goodsPriceList){
                            GoodsPrice newPrice = new GoodsPrice();
                            newPrice.setType(goodsPrice.getType());
                            newPrice.setDesc(goodsPrice.getDesc());
                            if(newPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_FIXED_PRICE) || newPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_FIXED_MANUALFEE) || newPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_FIXED_MATERIALFEE)){
                                newPrice.setPrice(goodsPrice.getPrice() * orderGoods.getAmount());
                            } else if(newPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_VALUATED_MANUALFEE)) {
                                if(orderGoods.getMaterialWeight() > 0.001f){
                                    newPrice.setPrice(goodsPrice.getPrice() * orderGoods.getMaterialWeight());
                                }
                            } else if(newPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_VALUATED_MATERIALFEE)){
                                float weight = 0.00f;
                                if(orderGoods.getMaterialWeight() > 0.001f){
                                    weight = orderGoods.getMaterialWeight();
                                }
                                double price = agencyPrice.getMaterialPrice(orderGoods.getMaterialId());
                                newPrice.setPrice(weight * (float)price);
                            }
                            newPrice.setPrice((float)(Math.round(newPrice.getPrice()*100))/100);
                            if(priceMap.containsKey(newPrice.getType())){
                                GoodsPrice orderPrice = priceMap.get(newPrice.getType());
                                orderPrice.setPrice(orderPrice.getPrice() + newPrice.getPrice());
                            }else {
                                priceMap.put(newPrice.getType(), newPrice);
                            }
                            orderGoodsPriceList.add(newPrice);
                        }
                        orderGoods.setOrderGoodsPrice(orderGoodsPriceList);
                    }
                }
                List<GoodsPrice> listPrice = new ArrayList<GoodsPrice>();
                listPrice.addAll(priceMap.values());
                payPrice.setSumTotalPrice(listPrice);
                order.setPayPrice(payPrice);
                order.setOrderPrice(listPrice);
            }
        }
    }

    @Override
    public ServiceResult<List<Order>> getOrdersByCustomerUserId(String userId) {
        ServiceResult<List<Order>> serviceResult = new ServiceResult<List<Order>>();
        try {
            List<Order> list = orderDAO.selectListByCustomerUserId(userId);
            for(Order entry:list){
                entry.setGoods(orderGoodsDAO.getByOrderId(entry.getId()));
                setValuedOrderPrice(entry);
                setPayOrderPrice(entry);
                setGoodsSkuSpec(entry);
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(list);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> getOrder(String userId, String orderId) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            Order order = orderDAO.select(orderId);
            //如果是零售商查询，查询自己的订单，如果是一级批发商用户查询，查询自己agency作为供应商的订单，如果是超级用户，放开
            Customer customer = customerDAO.select(userId);
            if(customer != null){
                if(!order.getCustomerId().equalsIgnoreCase(userId)){
                    logger.error("buyer can query order of self only");
                    serviceResult.setErrorCode(600);
                    serviceResult.setErrorMessage("operation forbidden");
                    serviceResult.setSuccess(false);
                    return serviceResult;
                }
            }
            Vender vender = venderDAO.select(userId);
            if(vender != null){
                if(!order.getVenderId().equalsIgnoreCase(vender.getKshopAgencyId())){
                    logger.error("vender can query order of self only");
                    serviceResult.setErrorCode(600);
                    serviceResult.setErrorMessage("operation forbidden");
                    serviceResult.setSuccess(false);
                    return serviceResult;
                }
            }
            if(customer == null && vender == null && !userService.isSuperUser(userId)){
                logger.error("this userid is invalid");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            if (order != null) {
                order.setGoods(orderGoodsDAO.getByOrderId(orderId));
                setValuedOrderPrice(order);
                setPayOrderPrice(order);
                order.setCustomer(userService.getCustomer(order.getCustomerId()));
                order.setVender(userService.getVender(order.getVenderId()));
                setGoodsSkuSpec(order);
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(order);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<String>> createOrderFromShopcart(String userId, List<String> shopcartGoodsIds) {
        ServiceResult<List<String>> serviceResult = new ServiceResult<List<String>>();
        List<String> listOrderId = new ArrayList<String>();
        try {
            ShopCart shopCart = shopCartService.getShopCart(userId);
            Customer customer = customerDAO.select(userId);
            List<ShopCartGoods> shopCartGoodsList = shopCartGoodsDAO.selectListByShopCartId(shopCart.getId());
            Set<String> goodsSkuSet = new HashSet<String>();
            Map<String, List<ShopCartGoods>> venderMap = new HashMap<String, List<ShopCartGoods>>();
            for (ShopCartGoods entry : shopCartGoodsList) {
                for (String id : shopcartGoodsIds) {
                    if (id.equalsIgnoreCase(entry.getId())) {
                        goodsSkuSet.add(entry.getSkuId());
                    }
                }
                List<ShopCartGoods> goodsList = venderMap.get(entry.getVenderId());
                if (goodsList == null) {
                    goodsList = new ArrayList<ShopCartGoods>();
                    goodsList.add(entry);
                } else {
                    goodsList.add(entry);
                }
                venderMap.put(entry.getVenderId(), goodsList);
            }

            for (String venderid : venderMap.keySet()) {
                boolean needWeight = false;
                Order order = new Order();
                StatusInfo statusInfo = new StatusInfo();
                order.setCreateTime(new Date().getTime());
                order.setLastupdateTime(new Date().getTime());
                order.setCustomerId(userId);
                order.setId(IdFactory.generateId());
               // order.setPrice(0.00f);
                order.setStatus(Constants.ORDER_STATUS_WAIT_WEIGHT);
                order.setBalanceStatus(Constants.BALANCE_ORDER_STATUS_INIT);
                statusInfo.setStatus(Constants.ORDER_STATUS_WAIT_WEIGHT);
                statusInfo.setDesc(Constants.ORDER_TRANSFER_LOG_SUBMIT);
                statusInfo.setUpdateTime(new Date());
                statusInfo.setLimitInHour(3L);
                order.setVenderId(venderid);
                List<ShopCartGoods> goodsList = venderMap.get(venderid);
                logger.debug("create order with [{}] goods", goodsList.size());
                for (ShopCartGoods goods : goodsList) {
                    Goods detailGoods = goodsService.getGoods(goods.getGoodsId());
                    List<GoodsSku> goodsSkuList = detailGoods.getSkus();
                    for (GoodsSku goodsSku : goodsSkuList) {
                        if (goodsSkuSet.contains(goodsSku.getId())) {
                            OrderGoods orderGoods = new OrderGoods();
                            orderGoods.setId(IdFactory.generateId());
                            orderGoods.setGoodsId(detailGoods.getId());
                            orderGoods.setCode(detailGoods.getCode());
                            orderGoods.setAttrValueIds(detailGoods.getAttrValueIds());
                            orderGoods.setMaterialId(detailGoods.getMaterialId());
                            orderGoods.setName(detailGoods.getName());
                            orderGoods.setOrderId(order.getId());
                            orderGoods.setPrices(detailGoods.getPrices());
                            orderGoods.setStyleId(detailGoods.getStyleId());
                            orderGoods.setUseIds(detailGoods.getUseIds());
                            orderGoods.setCoverImg(goods.getImg());
                            orderGoods.setGoodsSkuId(goodsSku.getId());
                            orderGoods.setGoodsSku(goodsSku);
                            orderGoods.setAmount(goods.getAmount());
                            orderGoods.setValuedWeight(goods.getMaterialWeight());
                            orderGoodsDAO.insert(orderGoods);
                            if(goods.getIsFixedPrice() == 0){
                                needWeight = true;
                            }
                        }
                    }

                    order.setVenderName(detailGoods.getVender().getKshopAgencyName());
                }
                if(!needWeight){
                    order.setStatus(Constants.ORDER_STATUS_WEIGHTED);
                    order.setBalanceStatus(Constants.BALANCE_ORDER_STATUS_ENDED);
                    statusInfo.setStatus(Constants.ORDER_STATUS_WEIGHTED);
                    statusInfo.setDesc(Constants.ORDER_TRANSFER_LOG_WEIGHTED);
                }
                order.setStatusInfo(statusInfo);
                orderDAO.insert(order);
                listOrderId.add(order.getId());

                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setId(IdFactory.generateId());
                orderStatus.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                orderStatus.setDesc(Constants.ORDER_TRANSFER_LOG_SUBMIT);
                if(!needWeight){
                    orderStatus.setNewstatus(Constants.ORDER_STATUS_WEIGHTED);
                }else {
                    orderStatus.setNewstatus(Constants.ORDER_STATUS_WAIT_WEIGHT);
                }
                orderStatus.setOldstatus("");
                orderStatus.setOrderId(order.getId());
                orderStatus.setUser(customer.getKshopAgencyName() + " " + customer.getKshopUserName());
                orderStatus.setUsercate("customer");
                orderStatusDAO.insert(orderStatus);
            }
            for(String id : shopcartGoodsIds){
                shopCartGoodsDAO.delete(shopCart.getId(), id);
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(listOrderId);
        } catch (Exception e) {
            logger.error("create order from shopcart fatal error:", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> updateOrderItemCount(String userId, String orderId, String orderGoodsId, int count) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            Order order = orderDAO.select(orderId);
            if (!(order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WAIT_WEIGHT) || order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTING))) {
                logger.error("order goodsItem should not be change");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            if(order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTING)) {
                Vender vender = venderDAO.select(userId);
                if(vender == null || !vender.getKshopAgencyId().equalsIgnoreCase(order.getVenderId())){
                    logger.error("only vender can modify order now");
                    serviceResult.setErrorCode(600);
                    serviceResult.setErrorMessage("operation forbidden");
                    serviceResult.setSuccess(false);
                    return serviceResult;
                }
            }
            if(order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WAIT_WEIGHT)){
                if(!userId.equalsIgnoreCase(order.getCustomerId())){
                    logger.error("only creater can modify order now");
                    serviceResult.setErrorCode(600);
                    serviceResult.setErrorMessage("operation forbidden");
                    serviceResult.setSuccess(false);
                    return serviceResult;
                }
            }
            OrderGoods orderGoods = orderGoodsDAO.getById(orderGoodsId);
            orderGoods.setAmount(count);
            orderGoodsDAO.insert(orderGoods);
            serviceResult = getOrder(userId, orderId);
        } catch (Exception e) {
            logger.error("fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
            serviceResult.setSuccess(false);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> deleteOrderItem(String userId, String orderId, String orderGoodsId) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            Order order = getOrder(userId,orderId).getValue();
            if (!(order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTING) || order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WAIT_WEIGHT) || order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTED))) {
                logger.error("order item delete forbidden");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            if(order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTING)) {
                Vender vender = venderDAO.select(userId);
                if(vender == null || !vender.getKshopAgencyId().equalsIgnoreCase(order.getVenderId())){
                    logger.error("only vender can modify order now");
                    serviceResult.setErrorCode(600);
                    serviceResult.setErrorMessage("operation forbidden");
                    serviceResult.setSuccess(false);
                    return serviceResult;
                }
            }
            if(order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WAIT_WEIGHT) || order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTED)){
                if(!userId.equalsIgnoreCase(order.getCustomerId())){
                    logger.error("only creater can modify order now");
                    serviceResult.setErrorCode(600);
                    serviceResult.setErrorMessage("operation forbidden");
                    serviceResult.setSuccess(false);
                    return serviceResult;
                }
            }
            List<OrderGoods> orderGoods = order.getGoods();
            if (order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTED)) {
                for (OrderGoods entry : orderGoods) {
                    goodsSkuDAO.updateCurCountAndFreezeCount(entry.getGoodsId(), entry.getGoodsSkuId(), entry.getAmount(), -1);
                }
            }
            if (orderGoods == null || (orderGoods.size() < 2 && orderGoods.get(0).getId().equalsIgnoreCase(orderGoodsId))) {
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setId(IdFactory.generateId());
                orderStatus.setDesc(Constants.ORDER_TRANSFER_LOG_CANCELED);
                orderStatus.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                orderStatus.setNewstatus(Constants.ORDER_STATUS_CANCELED);
                orderStatus.setOldstatus(order.getStatus());
                orderStatus.setOrderId(orderId);
                Customer customer = customerDAO.select(userId);
                orderStatus.setUser(customer.getKshopAgencyName()+" "+customer.getKshopUserName());
                orderStatus.setUsercate("customer");
                orderStatusDAO.insert(orderStatus);

                order.setStatus(Constants.ORDER_STATUS_CANCELED);
                StatusInfo statusInfo = new StatusInfo();
                statusInfo.setStatus(Constants.ORDER_STATUS_CANCELED);
                statusInfo.setDesc(Constants.ORDER_TRANSFER_LOG_CANCELED);
                statusInfo.setLimitInHour(-1L);
                statusInfo.setUpdateTime(new Date());
                order.setStatusInfo(statusInfo);
                orderDAO.insert(order);

            } else {
                OrderGoods target = null;
                for (OrderGoods entry : orderGoods) {
                    if (entry.getId().equalsIgnoreCase(orderGoodsId)) {
                        orderGoodsDAO.deleteById(orderGoodsId);
                        target = entry;
                        break;
                    }
                }
                if (target != null) {
                    orderGoods.remove(target);
                }
            }

            serviceResult.setSuccess(true);
            serviceResult.setValue(order);
        } catch (Exception e) {
            logger.error("fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
            serviceResult.setSuccess(false);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> modifyShipmentAddr(String userId, String orderId, String addr) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            Order order = getOrder(userId,orderId).getValue();
            ShipAddress shipAddress = shipAddressDAO.select(userId, addr);
            if (shipAddress != null) {
                order.setAddress(shipAddress);
                orderDAO.insert(order);
                serviceResult.setSuccess(true);
                serviceResult.setValue(order);
            }
        } catch (Exception e) {
            logger.error("fatal error occur!", e);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
            serviceResult.setSuccess(false);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Map<String, Integer>> countByStatus(String userId) {
        ServiceResult<Map<String, Integer>> serviceResult = new ServiceResult<Map<String, Integer>>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            List<Order> list = orderDAO.selectListByCustomerUserId(userId);
            for (Order entry : list) {
                String status = entry.getStatus();
                if (map.containsKey(status)) {
                    int value = map.get(status);
                    map.put(status, value + 1);
                } else {
                    map.put(status, 1);
                }
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(map);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
            serviceResult.setSuccess(false);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Map<String, Integer>> countByStatusVender(String venderId) {
        ServiceResult<Map<String, Integer>> serviceResult = new ServiceResult<Map<String, Integer>>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            List<Order> list = orderDAO.selectListByVenderId(venderId);
            for (Order entry : list) {
                String status = entry.getStatus();
                if (map.containsKey(status)) {
                    int value = map.get(status);
                    map.put(status, value + 1);
                } else {
                    map.put(status, 1);
                }
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(map);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
            serviceResult.setSuccess(false);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Map<String, Integer>> countByBalancestatus(String userId) {
        ServiceResult<Map<String, Integer>> serviceResult = new ServiceResult<Map<String, Integer>>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            List<Order> list = orderDAO.selectListByCustomerUserId(userId);
            for (Order entry : list) {
                if(entry.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_ENDED)){
                    String balancestatus = entry.getBalanceStatus();
                    if (map.containsKey(balancestatus)) {
                        Integer value = map.get(balancestatus);
                        map.put(balancestatus, value + 1);
                    } else {
                        map.put(balancestatus, 1);
                    }
                }
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(map);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
            serviceResult.setSuccess(false);
        }
        return serviceResult;
    }

    @Override
    @Deprecated
    public ServiceResult<List<Order>> queryOrder(String userId, String status, String venderId, String qtime, String pIndex, String pCount) {
        return null;
    }

    @Override
    public ServiceResult<List<OrderStatus>> getOrderStatus(String userId, String orderId) {
        ServiceResult<List<OrderStatus>> serviceResult = new ServiceResult<List<OrderStatus>>();
        try {
            List<OrderStatus> list = orderStatusDAO.listByOrderId(orderId);
            serviceResult.setSuccess(true);
            serviceResult.setValue(list);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> weightedOrder(String userId, String orderId) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            Order detailOrder = getOrder(userId,orderId).getValue();
            GoodsSales goodsSales = new GoodsSales();
            goodsSales.setCreatedDate(new Date().getTime());
            goodsSales.setId(IdFactory.generateId());
            goodsSales.setOrderId(orderId);
            goodsSales.setStatus(Constants.STATUS_SHOW + "");
            goodsSales.setType(Constants.GOODS_SALES_TYPE_SALE);
            goodsSales.setVenderId(detailOrder.getVenderId());
            goodsSalesDAO.insert(goodsSales);
            List<OrderGoods> orderGoodsList = detailOrder.getGoods();
            for (OrderGoods entry : orderGoodsList) {
                goodsSkuDAO.updateCurCountAndFreezeCount(entry.getGoodsId(), entry.getGoodsSkuId(), entry.getAmount(), 1);
                GoodsSalesItem goodsSalesItem = new GoodsSalesItem();
                goodsSalesItem.setId(IdFactory.generateId());
                goodsSalesItem.setGoodsId(entry.getGoodsId());
                goodsSalesItem.setGoodsSkuId(entry.getGoodsSkuId());
                goodsSalesItem.setSalesId(goodsSales.getId());
                goodsSalesItem.setAmount(entry.getAmount());
                goodsSalesItemDAO.insert(goodsSalesItem);
            }
            detailOrder.setStatus(Constants.ORDER_STATUS_WEIGHTED);
            StatusInfo statusInfo = detailOrder.getStatusInfo();
            if(statusInfo == null){
                statusInfo = new StatusInfo();
                statusInfo.setLimitInHour(3);
            }

            statusInfo.setUpdateTime(new Date());
            statusInfo.setStatus(Constants.ORDER_STATUS_WEIGHTED);
            statusInfo.setDesc(Constants.ORDER_TRANSFER_LOG_WEIGHTED);
            detailOrder.setStatusInfo(statusInfo);
            orderDAO.insert(detailOrder);

            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setId(IdFactory.generateId());
            orderStatus.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            orderStatus.setDesc(Constants.ORDER_TRANSFER_LOG_WEIGHTED);
            orderStatus.setNewstatus(Constants.ORDER_STATUS_WEIGHTED);
            orderStatus.setOldstatus(Constants.ORDER_STATUS_WEIGHTING);
            orderStatus.setOrderId(detailOrder.getId());
            orderStatus.setUser(userId);
            orderStatus.setUsercate("vender");
            orderStatusDAO.insert(orderStatus);
            serviceResult.setSuccess(true);
            serviceResult.setValue(detailOrder);
        } catch (Exception e) {
            logger.error("some fatal error occur.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> shipOrder(String userId, String orderId) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            Order detailOrder = getOrder(userId, orderId).getValue();
            Invoice invoice = new Invoice();
            invoice.setId(IdFactory.generateId());
            invoice.setAddr(detailOrder.getAddress());
            invoice.setCreatedDate(new Date().getTime());
            invoice.setCustomerId(detailOrder.getCustomerId());
            invoice.setOrderId(orderId);
            invoice.setStatus(Constants.STATUS_SHOW + "");
            invoice.setVenderId(detailOrder.getVenderId());
            invoice.setVenderName(detailOrder.getVenderName());
            invoiceDAO.insert(invoice);

            List<OrderGoods> orderGoodsList = detailOrder.getGoods();
            for (OrderGoods entry : orderGoodsList) {
                goodsSkuDAO.updateSellCountAndFreezeCount(entry.getGoodsId(), entry.getGoodsSkuId(), entry.getAmount());

                InvoiceItem invoiceItem = new InvoiceItem();
                invoiceItem.setId(IdFactory.generateId());
                invoiceItem.setInvoiceId(invoice.getId());
                invoiceItem.setAmount(entry.getAmount());
                invoiceItem.setGoodsId(entry.getGoodsId());
                invoiceItem.setGoodsSkuId(entry.getGoodsSkuId());
                invoiceItem.setName(entry.getName());
                invoiceItemDAO.insert(invoiceItem);
            }
            detailOrder.setStatus(Constants.ORDER_STATUS_WAIT_CONFIRM);
            StatusInfo statusInfo = detailOrder.getStatusInfo();
            if(statusInfo == null){
                statusInfo = new StatusInfo();
                statusInfo.setLimitInHour(3);
            }
            statusInfo.setStatus(Constants.ORDER_STATUS_WAIT_CONFIRM);
            statusInfo.setDesc(Constants.ORDER_TRANSFER_LOG_SHIPPED);
            statusInfo.setUpdateTime(new Date());
            detailOrder.setStatusInfo(statusInfo);
            orderDAO.insert(detailOrder);

            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setId(IdFactory.generateId());
            orderStatus.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            orderStatus.setDesc(Constants.ORDER_TRANSFER_LOG_SHIPPED);
            orderStatus.setNewstatus(Constants.ORDER_STATUS_WAIT_CONFIRM);
            orderStatus.setOldstatus(Constants.ORDER_STATUS_WAIT_DELIVERY);
            orderStatus.setOrderId(detailOrder.getId());
            orderStatus.setUser(userId);
            orderStatus.setUsercate("vender");
            orderStatusDAO.insert(orderStatus);

            serviceResult.setSuccess(true);
            serviceResult.setValue(detailOrder);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
            serviceResult.setSuccess(false);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> confirmOrder(String userId, String orderId) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {

            Order detailOrder = getOrder(userId, orderId).getValue();
            detailOrder.setStatus(Constants.ORDER_STATUS_ENDED);
            StatusInfo statusInfo = detailOrder.getStatusInfo();
            if(statusInfo == null){
                statusInfo = new StatusInfo();
                statusInfo.setLimitInHour(3);
            }
            statusInfo.setStatus(Constants.ORDER_STATUS_ENDED);
            statusInfo.setUpdateTime(new Date());
            statusInfo.setDesc(Constants.ORDER_TRANSFER_LOG_CONFIRMED);
            statusInfo.setLimitInHour(-1L);
            detailOrder.setStatusInfo(statusInfo);


            List<OrderGoods> orderGoods = detailOrder.getGoods();
            boolean balanced = true;
            for (OrderGoods goodsEntry : orderGoods) {
                if (goodsEntry.getIsFixedPrice() ==0 && goodsEntry.getMaterialWeight() > 0 && goodsEntry.getMaterialWeight() > goodsEntry.getBalanceWeight()) {
                    balanced = false;
                    break;
                }
            }
            if (balanced) {
                detailOrder.setBalanceStatus(Constants.BALANCE_ORDER_STATUS_ENDED);
            }
            orderDAO.insert(detailOrder);


            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setId(IdFactory.generateId());
            orderStatus.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            orderStatus.setDesc(Constants.ORDER_TRANSFER_LOG_CONFIRMED);
            orderStatus.setNewstatus(Constants.ORDER_STATUS_ENDED);
            orderStatus.setOldstatus(Constants.ORDER_STATUS_WAIT_CONFIRM);
            orderStatus.setOrderId(detailOrder.getId());
            orderStatus.setUser(detailOrder.getCustomer().getKshopAgencyName()+" "+detailOrder.getCustomer().getKshopUserName());
            orderStatus.setUsercate("customer");
            orderStatusDAO.insert(orderStatus);

            serviceResult.setValue(detailOrder);
            serviceResult.setSuccess(true);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> cancelOrder(String userId, String orderId) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            Order detailOrder = getOrder(userId, orderId).getValue();
            if (!(detailOrder.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WAIT_WEIGHT) || detailOrder.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTING) || detailOrder.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTED))) {
                logger.error("order should not be canceled");
                serviceResult.setErrorCode(610);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
            }
            if (detailOrder.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTED)) {
                List<OrderGoods> orderGoodsList = detailOrder.getGoods();
                for (OrderGoods entry : orderGoodsList) {
                    goodsSkuDAO.updateCurCountAndFreezeCount(entry.getGoodsId(), entry.getGoodsSkuId(), entry.getAmount(), -1);
                }
            }

            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setId(IdFactory.generateId());
            orderStatus.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            orderStatus.setDesc(Constants.ORDER_TRANSFER_LOG_CANCELED);
            orderStatus.setNewstatus(Constants.ORDER_STATUS_CANCELED);
            orderStatus.setOldstatus(detailOrder.getStatus());
            orderStatus.setOrderId(detailOrder.getId());
            orderStatus.setUser(userId);
            orderStatus.setUsercate("customer");
            orderStatusDAO.insert(orderStatus);

            detailOrder.setStatus(Constants.ORDER_STATUS_CANCELED);
            StatusInfo statusInfo = new StatusInfo();
            statusInfo.setLimitInHour(-1L);
            statusInfo.setDesc(Constants.ORDER_TRANSFER_LOG_CANCELED);
            statusInfo.setUpdateTime(new Date());
            statusInfo.setStatus(Constants.ORDER_STATUS_CANCELED);
            detailOrder.setStatusInfo(statusInfo);

            orderDAO.insert(detailOrder);

            serviceResult.setValue(detailOrder);
            serviceResult.setSuccess(true);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
            serviceResult.setSuccess(false);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> updateOrderItemReq(String userId, String orderId, String orderGoodsId, String req) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        Order order = getOrder(userId, orderId).getValue();
        if(!order.getCustomerId().equalsIgnoreCase(userId)){
            logger.error("only creator can modify order now");
            serviceResult.setErrorCode(600);
            serviceResult.setErrorMessage("operation forbidden");
            serviceResult.setSuccess(false);
            return serviceResult;
        }
        List<OrderGoods> orderGoods = order.getGoods();
        for (OrderGoods entry : orderGoods) {
            if (entry.getId().equalsIgnoreCase(orderGoodsId)) {
                entry.setSpecNeed(req);
                orderGoodsDAO.insert(entry);
                serviceResult.setSuccess(true);
                serviceResult.setValue(order);
                return serviceResult;
            }
        }
        serviceResult.setErrorCode(610);
        serviceResult.setSuccess(false);
        serviceResult.setErrorMessage("no valid record to modify.");
        return serviceResult;
    }

    @Override
    public ServiceResult<Map<String, List<Payway>>> listOrderPayway(String userId, String orderId) {
        ServiceResult<Map<String, List<Payway>>> serviceResult = new ServiceResult<Map<String, List<Payway>>>();
        Map<String, List<Payway>> listMap = new HashMap<String, List<Payway>>();
        listMap.put("valid", new ArrayList<Payway>());
        listMap.put("invalid", new ArrayList<Payway>());
        try {
            Customer customer = customerDAO.select(userId);
            KShopAgencyAccount account = kshopHelper.doGetAccount(customer.getKshopAgencyId(), customer.getKshopToken());
            double validAmount = account.getThawy();
            double orderAmount = getOrderAmount(userId, orderId);
            List<Payway> paywayList = custPaywayDAO.getPaywayByCustId(userId);
            if(paywayList == null || paywayList.size()<1) {
                paywayList = paywayDAO.selectList(Constants.STATUS_SHOW+"");
            }
            for (Payway entry : paywayList) {
                if (entry.getType().equalsIgnoreCase(Constants.ORDER_PAYWAY_TYPE_ONLINE)) {
                    List<Payway> list = listMap.get("valid");
                    list.add(entry);
                } else if (entry.getType().equalsIgnoreCase(Constants.ORDER_PAYWAY_TYPE_PREPAY)) {
                    if (validAmount < orderAmount) {
                        List<Payway> list = listMap.get("invalid");
                        list.add(entry);
                    } else {
                        List<Payway> list = listMap.get("valid");
                        list.add(entry);
                    }
                } else if (entry.getType().equalsIgnoreCase(Constants.ORDER_PAYWAY_TYPE_HEDGING)) {
                    if (validAmount < (orderAmount * 1.2f)) {
                        List<Payway> list = listMap.get("invalid");
                        list.add(entry);
                    } else {
                        List<Payway> list = listMap.get("valid");
                        list.add(entry);
                    }
                }
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(listMap);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    private double getOrderAmount(String userId, String orderId) {
        Order order = getOrder(userId, orderId).getValue();
        Customer customer = customerDAO.select(order.getCustomerId());
        KShopAgencyPrice agencyPrice = kshopHelper.doGetPrice(customer.getKshopToken());
        List<OrderGoods> orderGoodses = order.getGoods();

        double amount = 0;

        for (OrderGoods entry : orderGoodses) {
            List<GoodsPrice> prices = entry.getPrices();
            for (GoodsPrice goodsPrice : prices) {
                if (goodsPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_FIXED_PRICE)) {
                    amount += goodsPrice.getPrice() * entry.getAmount();
                    break;
                }
                if (goodsPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_FIXED_MANUALFEE)) {
                    amount += goodsPrice.getPrice() * entry.getAmount();
                }
                if (goodsPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_FIXED_MATERIALFEE)) {
                    amount += goodsPrice.getPrice() * entry.getAmount();
                }
                if (goodsPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_VALUATED_MANUALFEE)) {
                    amount += goodsPrice.getPrice() * entry.getMaterialWeight();
                }
                if (goodsPrice.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_VALUATED_MATERIALFEE)) {
                    amount += agencyPrice.getMaterialPrice(entry.getMaterialId()) * entry.getMaterialWeight();
                }
            }
        }
        return amount;
    }

    @Override
    public ServiceResult<Order> modifyPayway(String userId, String orderId, String payway) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            Order order = getOrder(userId, orderId).getValue();
            if(!order.getCustomerId().equalsIgnoreCase(userId)){
                logger.error("only creator can modify order now");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            Payway payway1 = paywayDAO.select(payway);
            order.setPayway(payway1);
            orderDAO.insert(order);
            serviceResult.setSuccess(true);
            serviceResult.setValue(order);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> updateOrderItemWeight(String userId, String orderId, String orderGoodsId, float weight) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            Order order = getOrder(userId, orderId).getValue();
            Vender vender = venderDAO.select(userId);
            if(vender == null || !vender.getKshopAgencyId().equalsIgnoreCase(order.getVenderId())){
                logger.error("only vender can modify order now");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            order.setStatus(Constants.ORDER_STATUS_WEIGHTING);
            //statusInfo keep unchanged here
            List<OrderGoods> orderGoods = order.getGoods();
            for (OrderGoods entry : orderGoods) {
                if (entry.getId().equalsIgnoreCase(orderGoodsId)) {
                    entry.setMaterialWeight(weight);
                    orderGoodsDAO.insert(entry);
                    orderDAO.insert(order);
                    serviceResult.setSuccess(true);
                    serviceResult.setValue(order);
                    return serviceResult;
                }
            }
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage("no valid record to modify.");
        }

        return serviceResult;
    }

    @Override
    public ServiceResult<Pagination<Order>> getUnbalanceOrderByUserId(String userId, int pageIndex, int countPerPage) {
        ServiceResult<Pagination<Order>> serviceResult = new ServiceResult<Pagination<Order>>();
        try {
            //如果是零售商查询，查询自己的订单，如果是一级批发商用户查询，查询自己agency作为供应商的订单，如果是超级用户，所有订单
            OrderQueryParameter orderQueryParameter = new OrderQueryParameter();
            Customer customer = customerDAO.select(userId);
            if(customer != null){
                orderQueryParameter.setCustomerId(userId);
            }else {
                Vender vender = venderDAO.select(userId);
                if(vender != null){
                    orderQueryParameter.setVenderId(vender.getKshopAgencyId());
                }else {
                    if(!userService.isSuperUser(userId)){
                        logger.error("invalid user");
                        serviceResult.setErrorCode(600);
                        serviceResult.setErrorMessage("operation forbidden");
                        serviceResult.setSuccess(false);
                        return serviceResult;
                    }
                }
            }
            orderQueryParameter.setStatus(Constants.ORDER_STATUS_ENDED);
            BalanceOrderQueryParameter balanceOrderQueryParameter = new BalanceOrderQueryParameter();
            balanceOrderQueryParameter.setOrderQueryParameter(orderQueryParameter);
            balanceOrderQueryParameter.setBalanceEnded(false);


            Pagination pagination = new Pagination(pageIndex, countPerPage);
            int totalCount = orderDAO.count(balanceOrderQueryParameter).intValue();
            pagination.setTotalRecord(totalCount);
            int start = 0;
            int range = 10;
            start = (pagination.getPage() - 1) * pagination.getRange();
            range = pagination.getRange();

            List<Order> orderList = orderDAO.selectPageList(balanceOrderQueryParameter, start, range);
            List<Order> orderDetailList = new ArrayList<Order>();
            for (Order entry : orderList) {
                Order detailOrder = getOrder(userId, entry.getId()).getValue();
                List<OrderGoods> orderGoods = detailOrder.getGoods();
                boolean balanced = true;
                for (OrderGoods goodsEntry : orderGoods) {
                    if (goodsEntry.getMaterialWeight() > 0 && goodsEntry.getMaterialWeight() > goodsEntry.getBalanceWeight()) {
                        balanced = false;
                        break;
                    }
                }
                if (balanced) {
                    detailOrder.setBalanceStatus(Constants.BALANCE_ORDER_STATUS_ENDED);
                    orderDAO.insert(detailOrder);
                } else {
                    detailOrder.setBalanceStatus(Constants.BALANCE_ORDER_STATUS_INIT);
                    buildBalanceSum(detailOrder);
                    orderDAO.insert(detailOrder);
                }
                orderDetailList.add(detailOrder);
            }

            pagination.setData(orderDetailList);

            serviceResult.setSuccess(true);
            serviceResult.setValue(pagination);
        } catch (Exception e) {
            logger.error("fatal error occur.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }



    @Override
    public ServiceResult<Pagination<Order>> getBalancedOrderByUserId(String userId, int pageIndex, int countPerPage) {
        ServiceResult<Pagination<Order>> serviceResult = new ServiceResult<Pagination<Order>>();
        try {
            //如果是零售商查询，查询自己的订单，如果是一级批发商用户查询，查询自己agency作为供应商的订单，如果是超级用户，所有订单
            OrderQueryParameter orderQueryParameter = new OrderQueryParameter();
            Customer customer = customerDAO.select(userId);
            if(customer != null){
                orderQueryParameter.setCustomerId(userId);
            }else {
                Vender vender = venderDAO.select(userId);
                if(vender != null){
                    orderQueryParameter.setVenderId(vender.getKshopAgencyId());
                }else {
                    if(!userService.isSuperUser(userId)){
                        logger.error("invalid user");
                        serviceResult.setErrorCode(600);
                        serviceResult.setErrorMessage("operation forbidden");
                        serviceResult.setSuccess(false);
                        return serviceResult;
                    }
                }
            }
            orderQueryParameter.setStatus(Constants.ORDER_STATUS_ENDED);
            BalanceOrderQueryParameter balanceOrderQueryParameter = new BalanceOrderQueryParameter();
            balanceOrderQueryParameter.setOrderQueryParameter(orderQueryParameter);
            balanceOrderQueryParameter.setBalanceEnded(true);


            Pagination pagination = new Pagination(pageIndex, countPerPage);
            int totalCount = orderDAO.count(balanceOrderQueryParameter).intValue();
            pagination.setTotalRecord(totalCount);
            int start = 0;
            int range = 10;
            start = (pagination.getPage() - 1) * pagination.getRange();
            range = pagination.getRange();

            List<Order> orderList = orderDAO.selectPageList(balanceOrderQueryParameter, start, range);
            for (Order entry : orderList) {
                entry.setGoods(orderGoodsDAO.getByOrderId(entry.getId()));
                entry.setCustomer(userService.getCustomer(entry.getCustomerId()));
                entry.setVender(userService.getVender(entry.getVenderId()));
                buildBalanceSum(entry);
            }
            pagination.setData(orderList);
            serviceResult.setSuccess(true);
            serviceResult.setValue(pagination);
        } catch (Exception e) {
            logger.error("fatal error occur.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
            serviceResult.setSuccess(false);
        }
        return serviceResult;
    }


    private void buildBalanceSum(Order entry) {
        List<OrderGoods> orderGoodsList = entry.getGoods();
        KShopAgencyPrice agencyPrice = kshopHelper.doGetPrice(entry.getCustomer().getKshopToken());
        BalanceSum balanceSum = new BalanceSum();
        for(OrderGoods goodsEntry : orderGoodsList){
            if(goodsEntry.getIsFixedPrice() == 0){
                if(entry.getBalanceStatus().equalsIgnoreCase(Constants.BALANCE_ORDER_STATUS_ENDED)){
                    balanceSum.setTotalWeight(balanceSum.getTotalWeight() + goodsEntry.getMaterialWeight());
                    balanceSum.setBalancedWeight(balanceSum.getBalancedWeight() + goodsEntry.getMaterialWeight());
                } else {
                    balanceSum.setTotalWeight(balanceSum.getTotalWeight() + goodsEntry.getMaterialWeight());
                    balanceSum.setBalancedWeight(balanceSum.getBalancedWeight() + goodsEntry.getBalanceWeight());
                    balanceSum.setUnbalancedAmount((goodsEntry.getMaterialWeight() - goodsEntry.getBalanceWeight()) * (float)agencyPrice.getMaterialPrice(goodsEntry.getMaterialId()));
                }
            }
        }
        balanceSum.setUnbalancedWeight(balanceSum.getTotalWeight() - balanceSum.getBalancedWeight());
        entry.setBalanceSum(balanceSum);
    }


    @Override
    public ServiceResult<Order> balancePay(String userId, String payPassword, String orderId, List<String> orderGoodsIdArray, List<Float> weightArray) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            Order detailOrder = getOrder(userId, orderId).getValue();
            if(!detailOrder.getCustomerId().equalsIgnoreCase(userId)){
                logger.error("only creator can modify order now");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            List<OrderGoods> list = detailOrder.getGoods();

            List<BalanceOrderItem> balanceOrderItemList = new ArrayList<BalanceOrderItem>();
            for (OrderGoods entry : list) {
                for (int i = 0, iBound = orderGoodsIdArray.size(); i < iBound; i++) {
                    if (entry.getId().equalsIgnoreCase(orderGoodsIdArray.get(i))) {
                        if ((entry.getMaterialWeight() - entry.getBalanceWeight()) < weightArray.get(i)) {
                            logger.error("weight is greater than unbalanced weight");
                            serviceResult.setErrorCode(600);
                            serviceResult.setSuccess(false);
                            serviceResult.setErrorMessage("weight should not be greater than unbalanced weight");
                        } else {
                            BalanceOrderItem balanceOrderItem = new BalanceOrderItem();
                            balanceOrderItem.setName(entry.getName());
                            balanceOrderItem.setBalanceWeight(weightArray.get(i));
                            balanceOrderItem.setOrderItemId(entry.getId());
                            balanceOrderItem.setGoodsId(entry.getGoodsId());
                            balanceOrderItem.setGoodsSkuId(entry.getGoodsSkuId());
                            balanceOrderItem.setMaterialId(entry.getMaterialId());
                            balanceOrderItemList.add(balanceOrderItem);

                        }
                    }
                }
            }
            List<BalanceOrderFlow> balanceOrderFlowList = balanceOrderFlowDAO.listByOrderId(orderId);
            /**
            if (balanceOrderFlowList != null && balanceOrderFlowList.get(0).getCreatedDate() > (new Date().getTime() - 1000 * 60 * 5)) {
                logger.error("repeat balance order pay in 5 minutes");
                serviceResult.setErrorCode(602);
                serviceResult.setSuccess(false);
                serviceResult.setErrorMessage("repeat balance order pay in 5 minutes");
            }
             **/
            BalanceOrderFlow balanceOrderFlow = new BalanceOrderFlow();
            balanceOrderFlow.setStatus(Constants.BALANCE_ORDER_STATUS_INIT);
            balanceOrderFlow.setCreatedDate(new Date().getTime());
            balanceOrderFlow.setId(IdFactory.generateId());
            balanceOrderFlow.setOrderId(orderId);
            balanceOrderFlow.setItems(balanceOrderItemList);
            balanceOrderFlowDAO.insert(balanceOrderFlow);

            Customer customer = customerDAO.select(userId);
            serviceResult = kshopHelper.doPostBalanceDemand(detailOrder, balanceOrderFlow, customer);
            if (serviceResult.isSuccess()) {
                balanceOrderFlow.setStatus(Constants.BALANCE_ORDER_STATUS_ENDED);
                balanceOrderFlowDAO.insert(balanceOrderFlow);
                boolean balanced = true;
                for (OrderGoods entry : list) {
                    for (BalanceOrderItem item : balanceOrderItemList) {
                        if (entry.getId().equalsIgnoreCase(item.getOrderItemId())) {
                            entry.setBalanceWeight(entry.getBalanceWeight() + item.getBalanceWeight());
                            orderGoodsDAO.insert(entry);
                            if(entry.getMaterialWeight() > entry.getBalanceWeight()){
                                balanced = false;
                            }
                        }
                    }
                }
                if(balanced){
                    detailOrder.setBalanceStatus(Constants.BALANCE_ORDER_STATUS_ENDED);
                    orderDAO.insert(detailOrder);
                }
            }
        } catch (Exception e) {
            logger.error("fatal error occur.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<BalanceOrderFlow>> listBalanceOrderFlow(String userId, String orderId) {
        ServiceResult<List<BalanceOrderFlow>> serviceResult = new ServiceResult<List<BalanceOrderFlow>>();
        try {
            List<BalanceOrderFlow> list = balanceOrderFlowDAO.listByOrderId(orderId);
            serviceResult.setSuccess(true);
            serviceResult.setValue(list);
        } catch (Exception e) {
            logger.error("fatal error occur.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Order> payOrder(String userId, String passwd, String orderId, String mobile, String vcode) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            //TODO:valid vcode with mobile and userid
            //TODO:valid pay password
            Order detailOrder = getOrder(userId,orderId).getValue();
            if(!detailOrder.getCustomerId().equalsIgnoreCase(userId)){
                logger.error("only creator can pay order");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            Payway payway = detailOrder.getPayway();

            if (payway == null) {
                serviceResult.setErrorCode(604);
                serviceResult.setSuccess(false);
                serviceResult.setErrorMessage("payway not available,please set first.");
            } else if (payway.getType().equalsIgnoreCase(Constants.ORDER_PAYWAY_TYPE_ONLINE)) {
                serviceResult.setErrorCode(605);
                serviceResult.setSuccess(false);
                serviceResult.setErrorMessage("platform support no online payway now,and online payway should not call this service");
            } else if (payway.getType().equalsIgnoreCase(Constants.ORDER_PAYWAY_TYPE_PREPAY)) {
                List<BalanceOrderFlow> balanceOrderFlowList = balanceOrderFlowDAO.listByOrderId(orderId);
                /**
                if (balanceOrderFlowList != null && balanceOrderFlowList.size() > 0 && balanceOrderFlowList.get(0).getCreatedDate() > (new Date().getTime() - 1000 * 60 * 5)) {
                    logger.error("repeat pay in 5 minutes");
                    serviceResult.setErrorCode(602);
                    serviceResult.setErrorMessage("repeat pay in 5 minutes");
                    serviceResult.setSuccess(false);
                }
                 **/
                BalanceOrderFlow balanceOrderFlow = new BalanceOrderFlow();
                balanceOrderFlow.setId(IdFactory.generateId());
                balanceOrderFlow.setStatus(Constants.BALANCE_ORDER_STATUS_INIT);
                balanceOrderFlow.setOrderId(detailOrder.getId());
                balanceOrderFlow.setCreatedDate(new Date().getTime());
                List<BalanceOrderItem> balanceOrderItemList = new ArrayList<BalanceOrderItem>();
                for (OrderGoods entry : detailOrder.getGoods()) {
                    BalanceOrderItem item = new BalanceOrderItem();
                    item.setOrderItemId(entry.getId());
                    item.setGoodsId(entry.getGoodsId());
                    item.setGoodsSkuId(entry.getGoodsSkuId());
                    item.setMaterialId(entry.getMaterialId());
                    item.setName(entry.getName());
                    item.setBalanceWeight(entry.getMaterialWeight());
                    balanceOrderItemList.add(item);
                }
                balanceOrderFlow.setItems(balanceOrderItemList);
                balanceOrderFlowDAO.insert(balanceOrderFlow);
                Customer customer = customerDAO.select(userId);
                serviceResult = kshopHelper.doPostBalanceOrder(detailOrder, balanceOrderFlow, customer);
                //serviceResult.setSuccess(true);
                if (serviceResult.isSuccess()) {
                    balanceOrderFlow.setStatus(Constants.BALANCE_ORDER_STATUS_ENDED);
                    balanceOrderFlowDAO.insert(balanceOrderFlow);
                    detailOrder.setStatus(Constants.ORDER_STATUS_WAIT_DELIVERY);
                    StatusInfo statusInfo = detailOrder.getStatusInfo();
                    if(statusInfo == null){
                        statusInfo = new StatusInfo();
                    }
                    statusInfo.setStatus(Constants.ORDER_STATUS_WAIT_DELIVERY);
                    statusInfo.setUpdateTime(new Date());
                    statusInfo.setLimitInHour(3);
                    statusInfo.setDesc(Constants.ORDER_TRANSFER_LOG_PAYED);
                    detailOrder.setStatusInfo(statusInfo);
                    orderDAO.insert(detailOrder);
                    serviceResult.setSuccess(true);
                    serviceResult.setValue(detailOrder);
                }
            } else if (payway.getType().equalsIgnoreCase(Constants.ORDER_PAYWAY_TYPE_HEDGING)) {
                //todo:风控
                List<DemandOrderFlow> demandOrderFlowList = demandOrderFlowDAO.getByOrderId(orderId);
                /**
                if (demandOrderFlowList != null && demandOrderFlowList.size() > 0 && demandOrderFlowList.get(0).getCreatedDate() > (new Date().getTime() - 1000 * 60 * 5)) {
                    logger.error("repeat pay in 5 minutes");
                    serviceResult.setErrorCode(602);
                    serviceResult.setErrorMessage("repeat pay in 5 minutes");
                    serviceResult.setSuccess(false);
                }
                 **/
                DemandOrderFlow demandOrderFlow = new DemandOrderFlow();
                demandOrderFlow.setId(IdFactory.generateId());
                demandOrderFlow.setOrderId(orderId);
                demandOrderFlow.setStatus(Constants.BALANCE_ORDER_STATUS_INIT);
                demandOrderFlow.setCreatedDate(new Date().getTime());
                List<DemandOrderItem> demandOrderItemList = new ArrayList<DemandOrderItem>();
                for (OrderGoods entry : detailOrder.getGoods()) {
                    DemandOrderItem item = new DemandOrderItem();
                    item.setOrderItemId(entry.getId());
                    item.setGoodsId(entry.getGoodsId());
                    item.setGoodsSkuId(entry.getGoodsSkuId());
                    item.setName(entry.getName());
                    item.setMaterialId(entry.getMaterialId());
                    item.setHedgingWeight(entry.getMaterialWeight());
                    demandOrderItemList.add(item);
                }
                demandOrderFlow.setItems(demandOrderItemList);
                demandOrderFlowDAO.insert(demandOrderFlow);
                Customer customer = customerDAO.select(userId);
                serviceResult = kshopHelper.doPostDemandOrder(detailOrder, demandOrderFlow, customer);
                //serviceResult.setSuccess(true);
                if (serviceResult.isSuccess()) {
                    demandOrderFlow.setStatus(Constants.BALANCE_ORDER_STATUS_ENDED);
                    demandOrderFlowDAO.insert(demandOrderFlow);
                    detailOrder.setStatus(Constants.ORDER_STATUS_WAIT_DELIVERY);
                    StatusInfo statusInfo = detailOrder.getStatusInfo();
                    if(statusInfo == null){
                        statusInfo = new StatusInfo();
                    }
                    statusInfo.setStatus(Constants.ORDER_STATUS_WAIT_DELIVERY);
                    statusInfo.setUpdateTime(new Date());
                    statusInfo.setLimitInHour(3);
                    statusInfo.setDesc(Constants.ORDER_TRANSFER_LOG_PAYED);
                    detailOrder.setStatusInfo(statusInfo);
                    orderDAO.insert(detailOrder);
                }
            } else {
                serviceResult.setErrorCode(603);
                serviceResult.setSuccess(false);
                serviceResult.setErrorMessage("payway not available,please contact adminbtb@zhubao.com");

            }

        } catch (Exception e) {
            logger.error("fatal error occur.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
            serviceResult.setSuccess(false);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Order>> getWaitOrdersByVender(String userId) {
        ServiceResult<List<Order>> serviceResult = new ServiceResult<List<Order>>();
        List<Order> resultList = new ArrayList<Order>();
        try {
            Vender vender = venderDAO.select(userId);
            List<Order> list = orderDAO.selectListByVenderId(vender.getKshopAgencyId());
            for(Order entry:list){
                if(entry.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WAIT_DELIVERY) || entry.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WAIT_WEIGHT) || entry.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTING)){
                    entry.setGoods(orderGoodsDAO.getByOrderId(entry.getId()));
                    entry.setCustomer(userService.getCustomer(entry.getCustomerId()));
                    entry.setVender(userService.getVender(entry.getVenderId()));
                    resultList.add(entry);
                }
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(resultList);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Order>> selectListByVenderIdPayTime(String userId, String venderId, long beginTime, long endTime) {
        ServiceResult<List<Order>> serviceResult = new ServiceResult<List<Order>>();
        try {
            List<Order> list = orderDAO.selectListByVenderIdPayTime(venderId, beginTime, endTime);
            for(Order entry:list){
                entry.setGoods(orderGoodsDAO.getByOrderId(entry.getId()));
                setValuedOrderPrice(entry);
                setPayOrderPrice(entry);
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(list);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            serviceResult.setErrorCode(610);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

}

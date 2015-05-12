package com.zhubao.b2b.platform.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.zhubao.b2b.common.service.AbstractHttpRPCService;
import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.entity.AgencyAccountItem;
import com.zhubao.b2b.platform.entity.BalanceOrderItem;
import com.zhubao.b2b.platform.entity.GoodsPrice;
import com.zhubao.b2b.platform.entity.KShopAgencyAccount;
import com.zhubao.b2b.platform.entity.KShopAgencyPrice;
import com.zhubao.b2b.platform.entity.KShopUser;
import com.zhubao.b2b.platform.entity.PayPrice;
import com.zhubao.b2b.platform.model.BalanceOrderFlow;
import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.model.DemandOrderFlow;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.model.OrderGoods;

/**
 * User: xiaoping lu
 * Date: 13-10-23
 * Time: 上午10:47
 */
public class KShopHelper extends AbstractHttpRPCService {

    private final static String kshop_login_url_pattern = "{domain}/txy/api/login.do?userName={userName}&password={password}";
    private final static String kshop_get_user_url_pattern = "{domain}/txy/api/userInfo.do?userId={userId}&token={token}";
    private final static String kshop_get_account_url_pattern = "{domain}/txy/api/account.do?agencyId={agencyId}&token={token}";
    private final static String kshop_get_price_url_pattern = "{domain}/txy/api/dbPrice.do?token={token}";
    private final static String kshop_get_agency_list_url_pattern = "{domain}/txy/api/account.do?agencyId={agencyId}&token={token}";
    private final static String kshop_post_demand_order_url = "{domain}/txy/api/demandOrders.do";
    private final static String kshop_post_balance_order_url = "{domain}/txy/api/resultsPriceOrder.do";
    private final static String kshop_post_balance_demand_url = "{domain}/txy/api/resultsPriceDemandOrders.do";

    private final static Map<String, Map<String, Object>> cacheValues = new HashMap<String, Map<String, Object>>();

    static {
        KShopAgencyPrice price = new KShopAgencyPrice();
        price.setADD_AG_SELL(1d);
        price.setADD_AU_SELL(5d);
        price.setADD_PT_SELL(5d);
        price.setAG_SELL(15d);
        price.setAU_SELL(280d);
        price.setPT_SELL(350d);
        Map<String, Object> priceMap = new HashMap<String, Object>();
        priceMap.put("lastTime", 1394841510509L);
        priceMap.put("priceValue", price);
        cacheValues.put("price", priceMap);
    }


    private String domain;

    private final long cacheTimeInMilli;

    public KShopHelper(int maxConn, String domain, long cacheTimeInMilli) {
        super(maxConn);
        this.domain = domain;
        this.cacheTimeInMilli = cacheTimeInMilli;
    }

    public KShopUser doLogin(String userName, String password) {
        /**  模拟数据
        KShopUser user = new KShopUser();
        user.setToken("xxxxxxxxxxxyyyyyyyyyy");
        user.setUserId("200");
        user.setUserNickName("txy经销商管理员");
        user.setUserName("txymanager");
        user.setAgencyId("523");
        return user;
        ***/
        String pass = DigestUtils.md5Hex(password);
        String loginUrl = translateLoginUrl(userName, pass);
        KShopUser user = null;

        logger.debug("[{}] [RPC-doLogin] loginUrl = {}", new Object[]{System.currentTimeMillis(), loginUrl});

        HttpRPCResult result = invokeGet(loginUrl, HttpStatus.SC_OK);
        if (result.getStatusCode() != HttpStatus.SC_OK) {
            logger.error("[RPC-doLogin] failed! failed! statusCode: {}; message: {}", result.getStatusCode(), result.getMessage());
        } else {
            String jsonStr = new String(result.getPayload());
            user = new KShopUser();
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            user.setToken(jsonObject.getString("token"));
            user.setUserId(jsonObject.getString("userId"));
            user.setUserName(jsonObject.getString("userName"));
            user.setAgencyId(jsonObject.getString("agencyId"));
        }
        return user;
    }

    public KShopUser doGetUserInfo(String userId, String token) {
        /**  模拟数据
        KShopUser user = new KShopUser();
        user.setToken(token);
        user.setUserId("200");
        user.setUserNickName("txy经销商管理员");
        user.setUserName("txymanager");
        user.setAgencyId("523");
        user.setEmail("txy@txy.com");
        user.setTel("13556780987");
        user.setAgencyGradeName("yy");
        user.setAgencyName("天鑫经销商成都店");
        user.setUserRegDate("2010-10-10");
        user.setAgencyParentId("1");
        user.setAgencyStatus("");
        return user;
         **/
        String getUrl = translateGetUserUrl(userId, token);
        KShopUser user = null;

        if (logger.isDebugEnabled()) {
            logger.debug("[{}] [RPC-doGetUserInfo] getUrl = {}", new Object[]{System.currentTimeMillis(), getUrl});
        }

        HttpRPCResult result = invokeGet(getUrl, HttpStatus.SC_OK);
        if (result.getStatusCode() != HttpStatus.SC_OK) {
            if (logger.isErrorEnabled()) {
                logger.error("[RPC-doGetUserInfo] failed! statusCode: {}; message: {}", result.getStatusCode(), result.getMessage());
            }
        } else {
            String jsonStr = null;
            try {
                jsonStr = new String(result.getPayload(),"utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("charset error", e);
            }
            user = new KShopUser();
            //jsonStr = "{\"USER_EMAIL\":\"maomao@txygroup.com\",\"USER_ID\":502,\"USER_TEL\":\"13688171828\",\"USER_NAME\":\"MAOMAO\",\"AGENCY_GRADE_NAME\":\"一级代理商\",\"AGENCY_ID\":504,\"AGENCY_NAME\":\"天鑫洋机构事业部\",\"USER_REGIST_DATE\":\"2012-10-9 12:02:34\",\"USER_NICK_NAME\":\"毛茅\"}";
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            user.setEmail(jsonObject.getString("USER_EMAIL"));
            user.setUserId(jsonObject.getString("USER_ID"));
            user.setTel(jsonObject.getString("USER_TEL"));
            user.setUserName(jsonObject.getString("USER_NAME"));
            user.setAgencyGradeName(jsonObject.getString("AGENCY_GRADE_NAME"));
            user.setAgencyId(jsonObject.getString("AGENCY_ID"));
            user.setAgencyName(jsonObject.getString("AGENCY_NAME"));
            user.setUserRegDate(jsonObject.getString("USER_REGIST_DATE"));
            user.setUserNickName(jsonObject.getString("USER_NICK_NAME"));
            if(jsonObject.containsKey("AGENCY_PARENT_ID")) {
                user.setAgencyParentId(jsonObject.getString("AGENCY_PARENT_ID"));
            }
            if(jsonObject.containsKey("AGENCY_STATUS")){
                user.setAgencyStatus(jsonObject.getString("AGENCY_STATUS"));
            }
        }
        return user;
    }

    public KShopAgencyAccount doGetAccount(String agencyId, String token) {
        String getUrl = translateGetAccountUrl(agencyId, token);
        KShopAgencyAccount agencyAccount = null;

        if (logger.isDebugEnabled()) {
            logger.debug("[{}] [RPC-doGetAccount] getUrl = {}", new Object[]{System.currentTimeMillis(), getUrl});
        }

        HttpRPCResult result = invokeGet(getUrl, HttpStatus.SC_OK);
        if (result.getStatusCode() != HttpStatus.SC_OK) {
            if (logger.isErrorEnabled()) {
                logger.error("[RPC-doGetAccount] failed! statusCode: {}; message: {}", result.getStatusCode(), result.getMessage());
            }
        } else {
            String jsonStr = new String(result.getPayload());
            agencyAccount = new KShopAgencyAccount();
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            agencyAccount.setAgencyId(agencyId);
            agencyAccount.setTotal(jsonObject.getDouble("total"));
            agencyAccount.setFrozen(jsonObject.getDouble("frozen"));
            agencyAccount.setThawy(jsonObject.getDouble("thawy"));
        }
        return agencyAccount;
    }

    public KShopAgencyPrice doGetPrice(String token) {
        /** 模拟数据
        KShopAgencyPrice agencyPrice = new KShopAgencyPrice();
        agencyPrice.setAG_SELL(240.5);
        agencyPrice.setAU_SELL(300.6);
        agencyPrice.setPT_SELL(150.8);
        agencyPrice.setADD_AG_SELL(256.9);
        agencyPrice.setADD_AU_SELL(278.5);
        agencyPrice.setADD_PT_SELL(333.2);
        agencyPrice.setAU_BUY(233.5);
        return agencyPrice;
       **/
        long curTime = System.currentTimeMillis();
        Map<String, Object> priceCache = cacheValues.get("price");
        if (priceCache != null) {
            long lastTime = (Long) priceCache.get("lastTime");
            if (curTime < lastTime + this.cacheTimeInMilli) {
                return (KShopAgencyPrice) priceCache.get("priceValue");
            }
        }
        String getUrl = translateGetPriceUrl(token);
        KShopAgencyPrice agencyPrice = null;

        if (logger.isDebugEnabled()) {
            logger.debug("[{}] [RPC-doGetPrice] getUrl = {}", new Object[]{System.currentTimeMillis(), getUrl});
        }

        HttpRPCResult result = invokeGet(getUrl, HttpStatus.SC_OK);
        if (result.getStatusCode() != HttpStatus.SC_OK) {
            if (logger.isErrorEnabled()) {
                logger.error("[RPC-doGetPrice] failed! statusCode: {}; message: {}", result.getStatusCode(), result.getMessage());
            }
        } else {
            String jsonStr = new String(result.getPayload());
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            agencyPrice = new KShopAgencyPrice();
            agencyPrice.setAG_SELL(jsonObject.getDouble("AG_SELL"));
            agencyPrice.setAU_SELL(jsonObject.getDouble("AU_SELL"));
            agencyPrice.setPT_SELL(jsonObject.getDouble("PT_SELL"));
            agencyPrice.setADD_AG_SELL(jsonObject.getDouble("ADD_AG_SELL"));
            agencyPrice.setADD_AU_SELL(jsonObject.getDouble("ADD_AU_SELL"));
            agencyPrice.setADD_PT_SELL(jsonObject.getDouble("ADD_PT_SELL"));
            agencyPrice.setAU_BUY(jsonObject.getDouble("AU_BUY"));

            curTime = System.currentTimeMillis();
            Map<String, Object> priceMap = new HashMap<String, Object>();
            priceMap.put("lastTime", curTime);
            priceMap.put("priceValue", agencyPrice);
            cacheValues.put("price", priceMap);
        }
        return agencyPrice;
    }

    //TODO: continue
    public List<AgencyAccountItem> doGetAgencyList(String userId, String token) {
        String getUrl = translateGetAgencyListUrl(userId, token);
        List<AgencyAccountItem> list = null;

        if (logger.isDebugEnabled()) {
            logger.debug("[{}] [RPC-doGetAgencyList] getUrl = {}", new Object[]{System.currentTimeMillis(), getUrl});
        }

        HttpRPCResult result = invokeGet(getUrl, HttpStatus.SC_OK);
        if (result.getStatusCode() != HttpStatus.SC_OK) {
            if (logger.isErrorEnabled()) {
                logger.error("[RPC-doGetAgencyList] failed! statusCode: {}; message: {}", result.getStatusCode(), result.getMessage());
            }
        } else {
            String jsonStr = new String(result.getPayload());
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            list = new ArrayList<AgencyAccountItem>();
        }
        return list;
    }

    public ServiceResult<Order> doPostDemandOrder(final Order order, final DemandOrderFlow demandOrderFlow, final Customer customer) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            final String postUrl = translatePostDemandOrderUrl();
            final long stamp = System.currentTimeMillis();
            logger.debug("[{}] [RPC-doPostDemandOrder] postUrl = {}", new Object[]{stamp, postUrl});
            logger.info("[XYZ-kshophelper] connection in pool : {}", super.getConnectionInPool());
            String ret = execute(new Callback() {
                @Override
                public String doIt() {
                    try {
                        PostMethod postMethod = new PostMethod(postUrl);
                        postMethod.addRequestHeader("Connection", "Keep-Alive");
                        postMethod.addRequestHeader("Accept-Charset", "UTF-8");
                        String jsonStr = getPostContent(order, demandOrderFlow);
                        logger.debug("post demand order with json:[{}]",jsonStr);
                        postMethod.addParameter(new NameValuePair("json", jsonStr));
                        postMethod.addParameter(new NameValuePair("token", customer.getKshopToken()));
                        HttpClient client = new HttpClient();
                        int status = client.executeMethod(postMethod);
                        if (status == 200) {
                            logger.debug("[RPC-doPostDemandOrder] response:[{}]", postMethod.getResponseBodyAsString());
                            return postMethod.getResponseBodyAsString();
                        } else {
                            logger.error("[RPC-doPostDemandOrder] error, http status:[{}]", status);
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error("[RPC-doPostDemandOrder] exception", e);
                        return null;
                    }
                }

                private String getPostContent(Order order, DemandOrderFlow flow) {
                    KShopAgencyPrice agencyPrice = doGetPrice(order.getCustomerId());
                    Map<String, Object> mapValue = new HashMap<String, Object>();
                    List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();

                    Map<String, Object> mapZB = new HashMap<String, Object>();
                    mapZB.put("type", "zb");
                    mapZB.put("totalprice", 0f);

                    mapValue.put("ordersn", order.getId());
                    mapValue.put("agencyId", customer.getKshopAgencyId());
                    mapValue.put("vagencyId", order.getVenderId());
                    mapValue.put("userId", customer.getKshopUserId());
                    mapValue.put("pmsStr","1.523.");

                    for (OrderGoods entry : order.getGoods()) {
                        List<GoodsPrice> entryPriceList = entry.getPrices();
                        for (GoodsPrice price : entryPriceList) {
                            if (!price.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_VALUATED_MATERIALFEE)) {
                                float cur = (Float) mapZB.get("totalprice");
                                if (price.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_VALUATED_MANUALFEE)) {
                                    mapZB.put("totalprice", cur + entry.getMaterialWeight() * price.getPrice());
                                } else {
                                    mapZB.put("totalprice", cur + price.getPrice());
                                }
                            } else {
                                Map<String, Object> mapGoods = new HashMap<String, Object>();
                                if (entry.getMaterialId().equalsIgnoreCase(Constants.GOODS_MATERIAL_AU)) {
                                    mapGoods.put("type", "au");
                                    mapGoods.put("weight", entry.getMaterialWeight());
                                    mapGoods.put("price", agencyPrice.getAU_SELL());
                                    mapGoods.put("addprice", agencyPrice.getADD_AU_SELL());
                                    mapGoods.put("goodscode", entry.getId());
                                } else if (entry.getMaterialId().equalsIgnoreCase(Constants.GOODS_MATERIAL_AG)) {
                                    mapGoods.put("type", "ag");
                                    mapGoods.put("weight", entry.getMaterialWeight());
                                    mapGoods.put("price", agencyPrice.getAG_SELL());
                                    mapGoods.put("addprice", agencyPrice.getADD_AG_SELL());
                                    mapGoods.put("goodscode", entry.getId());
                                } else if (entry.getMaterialId().equalsIgnoreCase(Constants.GOODS_MATERIAL_PT)) {
                                    mapGoods.put("type", "pt");
                                    mapGoods.put("weight", entry.getMaterialWeight());
                                    mapGoods.put("price", agencyPrice.getPT_SELL());
                                    mapGoods.put("addprice", agencyPrice.getADD_PT_SELL());
                                    mapGoods.put("goodscode", entry.getId());
                                } else {
                                    logger.debug("goods should not be 'valuatedmaterialfee'");
                                }
                                itemList.add(mapGoods);
                            }
                        }
                    }

                    itemList.add(mapZB);
                    mapValue.put("items", itemList);

                    PayPrice payPrice = new PayPrice();
                    payPrice.setCurPrice(agencyPrice);
                    order.setPayPrice(payPrice);
                    order.setPayTime(new Date().getTime());
                    //return {'ordersn':'','agencyId':'','userId':'','vagencyId':’’,'items':[{'type':'au','weight':10.00,'price':234.00,’addprice’:5.00,'goodscode':''},{'type':'pt','weight':10.00,'price':234.00,’addprice’:5.00,'goodscode':''},{'type':'ag','weight':10.00,'price':234.00,’addprice’:5.00,'goodscode':''},{'type':'zb','totalprice':234.00}]}
                    return JSONObject.fromObject(mapValue).toString();
                }
            });
            if (ret != null) {
                //TODO:parse return value to
                //JSONObject jsonObject = JSONObject.fromObject(ret);
               // String demandOrderId = jsonObject.getString("orderid");
                demandOrderFlow.setKshopOrderId(ret);
                serviceResult.setSuccess(true);
                serviceResult.setValue(order);
            }
        } catch (Exception e) {
            logger.error("kshophelper doPostDemandOrder exception ", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    public ServiceResult<Order> doPostBalanceOrder(final Order order, final BalanceOrderFlow balanceOrderFlow, final Customer customer) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            final String postUrl = translatePostBalanceOrderUrl();
            final long stamp = System.currentTimeMillis();
            logger.debug("[{}] [RPC-doPostBalanceOrder] postUrl = {}", new Object[]{stamp, postUrl});
            logger.info("[XYZ-kshophelper] connection in pool : {}", super.getConnectionInPool());
            String ret = execute(new Callback() {
                @Override
                public String doIt() {
                    try {
                        PostMethod postMethod = new PostMethod(postUrl);
                        postMethod.addRequestHeader("Connection", "Keep-Alive");
                        postMethod.addRequestHeader("Accept-Charset", "UTF-8");
                        String jsonStr = getPostContent(order, balanceOrderFlow);
                        logger.debug("post balance order with json:[{}]", jsonStr);
                        postMethod.addParameter(new NameValuePair("json", jsonStr));
                        postMethod.addParameter(new NameValuePair("token", customer.getKshopToken()));
                        HttpClient client = new HttpClient();
                        int status = client.executeMethod(postMethod);
                        if (status == 200) {
                            logger.debug("[RPC-doPostBalanceOrder] response:[{}]", postMethod.getResponseBodyAsString());
                            return postMethod.getResponseBodyAsString();
                        } else {
                            logger.error("[RPC-doPostBalanceOrder] error, http status:[{}]", status);
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error("[RPC-doPostBalanceOrder] exception", e);
                        return null;
                    }
                }

                private String getPostContent(Order order, BalanceOrderFlow flow) {
                    KShopAgencyPrice agencyPrice = doGetPrice(order.getCustomerId());
                    Map<String, Object> mapValue = new HashMap<String, Object>();
                    List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
                    Map<String, Object> mapAU = new HashMap<String, Object>();
                    mapAU.put("type", "au");
                    mapAU.put("weight", 0f);
                    mapAU.put("totalprice", 0f);
                    Map<String, Object> mapAG = new HashMap<String, Object>();
                    mapAG.put("type", "ag");
                    mapAG.put("weight", 0f);
                    mapAG.put("totalprice", 0f);
                    Map<String, Object> mapPT = new HashMap<String, Object>();
                    mapPT.put("type", "ag");
                    mapPT.put("weight", 0f);
                    mapPT.put("totalprice", 0f);
                    Map<String, Object> mapZB = new HashMap<String, Object>();
                    mapZB.put("type", "zb");
                    mapZB.put("totalprice", 0f);
                    mapValue.put("ordersn", order.getId());
                    mapValue.put("agencyId", customer.getKshopAgencyId());
                    mapValue.put("vagencyId", order.getVenderId());
                    mapValue.put("userId", customer.getKshopUserId());
                    List<BalanceOrderItem> balanceOrderItemList = flow.getItems();
                    for (BalanceOrderItem entry : balanceOrderItemList) {
                        if (entry.getMaterialId().equalsIgnoreCase(Constants.GOODS_MATERIAL_AU)) {
                            entry.setAgencyPrice((float) (agencyPrice.getAU_SELL() + agencyPrice.getADD_AU_SELL()));
                            mapAU.put("weight", entry.getBalanceWeight() + (Float) mapAU.get("weight"));
                        } else if (entry.getMaterialId().equalsIgnoreCase(Constants.GOODS_MATERIAL_AG)) {
                            entry.setAgencyPrice((float) (agencyPrice.getAG_SELL() + agencyPrice.getADD_AG_SELL()));
                            mapAG.put("weight", entry.getBalanceWeight() + (Float) mapAG.get("weight"));
                        } else if (entry.getMaterialId().equalsIgnoreCase(Constants.GOODS_MATERIAL_PT)) {
                            entry.setAgencyPrice((float) (agencyPrice.getPT_SELL() + agencyPrice.getADD_PT_SELL()));
                            mapPT.put("weight", entry.getBalanceWeight() + (Float) mapPT.get("weight"));
                        } else {
                            // mapZB.put("totalprice", entry.getAgencyPrice() + (Float) mapZB.get("totalprice"));
                        }
                    }
                    mapAG.put("totalprice", (agencyPrice.getAG_SELL() + agencyPrice.getADD_AG_SELL()) * (Float) mapAG.get("weight"));
                    itemList.add(mapAG);
                    mapAU.put("totalprice", (agencyPrice.getAU_SELL() + agencyPrice.getADD_AU_SELL()) * (Float) mapAU.get("weight"));
                    itemList.add(mapAU);
                    mapPT.put("totalprice", (agencyPrice.getPT_SELL() + agencyPrice.getADD_PT_SELL()) * (Float) mapPT.get("weight"));
                    itemList.add(mapPT);
                    if (order.getPayway().getType().equalsIgnoreCase(Constants.ORDER_PAYWAY_TYPE_PREPAY) && order.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_WEIGHTED)) {
                        for (OrderGoods entry : order.getGoods()) {
                            List<GoodsPrice> entryPriceList = entry.getPrices();
                            for (GoodsPrice price : entryPriceList) {
                                if (!price.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_VALUATED_MATERIALFEE)) {
                                    float cur = (Float) mapZB.get("totalprice");
                                    if (price.getType().equalsIgnoreCase(Constants.GOODS_PRICE_TYPE_VALUATED_MANUALFEE)) {
                                        mapZB.put("totalprice", cur + entry.getMaterialWeight() * price.getPrice());
                                    } else {
                                        mapZB.put("totalprice", cur + price.getPrice());
                                    }
                                }
                            }
                        }
                    }
                    itemList.add(mapZB);
                    mapValue.put("items", itemList);
                    PayPrice payPrice = new PayPrice();
                    payPrice.setCurPrice(agencyPrice);
                    order.setPayPrice(payPrice);
                    order.setPayTime(new Date().getTime());
                    return JSONObject.fromObject(mapValue).toString();
                }
            });
            if (ret != null) {
                JSONObject jsonObject = JSONObject.fromObject(ret);
                String balanceOrderId = jsonObject.getString("orderid");
                balanceOrderFlow.setKshopOrderId(balanceOrderId);
                serviceResult.setSuccess(true);
                serviceResult.setValue(order);
            }
        } catch (Exception e) {
            logger.error("kshophelper doPostBalanceOrder exception ", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    public ServiceResult<Order> doPostBalanceDemand(final Order order, final BalanceOrderFlow balanceOrderFlow, final Customer customer) {
        ServiceResult<Order> serviceResult = new ServiceResult<Order>();
        try {
            final String postUrl = translatePostBalanceDemandUrl();
            final long stamp = System.currentTimeMillis();
            logger.debug("[{}] [RPC-doPostBalanceDemand] postUrl = {}", new Object[]{stamp, postUrl});
            logger.info("[XYZ-kshophelper] connection in pool : {}", super.getConnectionInPool());
            String ret = execute(new Callback() {
                @Override
                public String doIt() {
                    try {
                        PostMethod postMethod = new PostMethod(postUrl);
                        postMethod.addRequestHeader("Connection", "Keep-Alive");
                        postMethod.addRequestHeader("Accept-Charset", "UTF-8");
                        String jsonStr = getPostContent(order, balanceOrderFlow);
                        logger.debug("post balance demand with json:[{}]", jsonStr);
                        postMethod.addParameter(new NameValuePair("json", jsonStr));
                        postMethod.addParameter(new NameValuePair("token", customer.getKshopToken()));
                        HttpClient client = new HttpClient();
                        int status = client.executeMethod(postMethod);
                        if (status == 200) {
                            logger.debug("[RPC-doPostBalanceDemand] response:[{}]", postMethod.getResponseBodyAsString());
                            return postMethod.getResponseBodyAsString();
                        } else {
                            logger.error("[RPC-doPostBalanceDemand] error, http status:[{}]", status);
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error("[RPC-doPostBalanceDemand] exception", e);
                        return null;
                    }
                }

                private String getPostContent(Order order, BalanceOrderFlow flow) {
                    KShopAgencyPrice agencyPrice = doGetPrice(order.getCustomerId());
                    Map<String, Object> mapValue = new HashMap<String, Object>();
                    List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();

                    Map<String, Object> mapZB = new HashMap<String, Object>();
                    mapZB.put("type", "zb");
                    mapZB.put("totalprice", 0f);

                    mapValue.put("ordersn", order.getId());
                    mapValue.put("agencyId", customer.getKshopAgencyId());
                    mapValue.put("vagencyId", order.getVenderId());
                    mapValue.put("userId", customer.getKshopUserId());
                    mapValue.put("pmsStr","1.523.");

                    for (BalanceOrderItem entry : flow.getItems()) {

                        Map<String, Object> mapGoods = new HashMap<String, Object>();
                        if (entry.getMaterialId().equalsIgnoreCase(Constants.GOODS_MATERIAL_AU)) {
                            mapGoods.put("type", "au");
                            mapGoods.put("weight", entry.getBalanceWeight());
                            mapGoods.put("price", agencyPrice.getAU_SELL());
                            mapGoods.put("addprice", agencyPrice.getADD_AU_SELL());
                            mapGoods.put("goodscode", entry.getOrderItemId());
                        } else if (entry.getMaterialId().equalsIgnoreCase(Constants.GOODS_MATERIAL_AG)) {
                            mapGoods.put("type", "ag");
                            mapGoods.put("weight", entry.getBalanceWeight());
                            mapGoods.put("price", agencyPrice.getAG_SELL());
                            mapGoods.put("addprice", agencyPrice.getADD_AG_SELL());
                            mapGoods.put("goodscode", entry.getOrderItemId());
                        } else if (entry.getMaterialId().equalsIgnoreCase(Constants.GOODS_MATERIAL_PT)) {
                            mapGoods.put("type", "pt");
                            mapGoods.put("weight", entry.getBalanceWeight());
                            mapGoods.put("price", agencyPrice.getPT_SELL());
                            mapGoods.put("addprice", agencyPrice.getADD_PT_SELL());
                            mapGoods.put("goodscode", entry.getOrderItemId());
                        } else {
                            logger.debug("goods should not be 'valuatedmaterialfee'");
                        }
                        itemList.add(mapGoods);

                    }

                    itemList.add(mapZB);
                    mapValue.put("items", itemList);
                    //return {'ordersn':'','agencyId':'','userId':'','vagencyId':'','items':[{'type':'au','weight':10.00,'price':234.00,’addprice’:5.00,'goodscode':''},{'type':'pt','weight':10.00,'price':234.00,’addprice’:5.00,'goodscode':''},{'type':'ag','weight':10.00,'price':234.00,’addprice’:5.00,'goodscode':''},{'type':'zb','totalprice':234.00}]}
                    return JSONObject.fromObject(mapValue).toString();
                }
            });
            if (ret != null) {
                //JSONObject jsonObject = JSONObject.fromObject(ret);
                //String orderId = jsonObject.getString("orderid");
                balanceOrderFlow.setKshopOrderId(ret);
                serviceResult.setSuccess(true);
                serviceResult.setValue(order);
            }
        } catch (Exception e) {
            logger.error("kshophelper doPostBalanceDemand exception ", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }


    private String translateLoginUrl(String userName, String password) {
        String url = StringUtils.replace(kshop_login_url_pattern, "{domain}", domain);
        url = StringUtils.replace(url, "{userName}", userName);
        url = StringUtils.replace(url, "{password}", password);
        return url;
    }

    private String translateGetUserUrl(String userId, String token) {
        String url = StringUtils.replace(kshop_get_user_url_pattern, "{domain}", domain);
        url = StringUtils.replace(url, "{userId}", userId);
        url = StringUtils.replace(url, "{token}", token);
        return url;
    }

    private String translateGetAccountUrl(String agencyId, String token) {
        String url = StringUtils.replace(kshop_get_account_url_pattern, "{domain}", domain);
        url = StringUtils.replace(url, "{agencyId}", agencyId);
        url = StringUtils.replace(url, "{token}", token);
        return url;
    }

    private String translateGetPriceUrl(String token) {
        String url = StringUtils.replace(kshop_get_price_url_pattern, "{domain}", domain);
        url = StringUtils.replace(url, "{token}", token);
        return url;
    }

    private String translateGetAgencyListUrl(String agencyId, String token) {
        String url = StringUtils.replace(kshop_get_agency_list_url_pattern, "{domain}", domain);
        url = StringUtils.replace(url, "{agencyId}", agencyId);
        url = StringUtils.replace(url, "{token}", token);
        return url;
    }

    private String translatePostDemandOrderUrl() {
        String url = StringUtils.replace(kshop_post_demand_order_url, "{domain}", domain);
        return url;
    }

    private String translatePostBalanceOrderUrl() {
        String url = StringUtils.replace(kshop_post_balance_order_url, "{domain}", domain);
        return url;
    }

    private String translatePostBalanceDemandUrl() {
        String url = StringUtils.replace(kshop_post_balance_demand_url, "{domain}", domain);
        return url;
    }

    private interface Callback {
        String doIt();

    }

    private String execute(Callback callback) {
        return callback.doIt();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}

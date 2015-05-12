package com.zhubao.b2b.manage;

import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.model.Payway;
import com.zhubao.b2b.platform.service.PaywayService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-7
 * Time: 上午10:40
 * To change this template use File | Settings | File Templates.
 */
public class InitPayway {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PaywayService paywayService = (PaywayService) context.getBean("paywayService");

        Payway payway = new Payway();
        payway.setId("zhubaob2b_prepay_kshop");
        payway.setName("预付款直接支付方式");
        payway.setDesc("采购商在珠宝网b2b平台预存一定的金额，订购商品结账时直接从预付款中扣款");
        payway.setType(Constants.ORDER_PAYWAY_TYPE_PREPAY);
        payway.setStatus(Constants.STATUS_SHOW+"");
        paywayService.createPayway(payway);

        payway.setId("zhubaob2b_hedging_kshop");
        payway.setName("预付款风控套保方式");
        payway.setDesc("采购商在珠宝网b2b平台预存一定的金额，订购商品结账时冻结一部分预付款，在采购商主动要求结价时才实际扣款");
        payway.setType(Constants.ORDER_PAYWAY_TYPE_HEDGING);
        payway.setStatus(Constants.STATUS_SHOW+"");
        paywayService.createPayway(payway);

        payway.setId("zhubaob2b_online_alipay");
        payway.setName("在线支付宝支付");
        payway.setDesc("采购商订购商品结账时在线通过支付宝实时支付");
        payway.setType(Constants.ORDER_PAYWAY_TYPE_ONLINE);
        payway.setStatus(Constants.STATUS_SHOW+"");
        paywayService.createPayway(payway);
    }
}

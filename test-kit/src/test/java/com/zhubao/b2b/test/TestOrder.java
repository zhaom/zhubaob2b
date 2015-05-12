package com.zhubao.b2b.test;

import com.zhubao.b2b.test.api.OrderResourceClient;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TestOrder {

    public static void main(String[] args){

        System.out.println( System.currentTimeMillis());
        OrderResourceClient orderClient = new OrderResourceClient();



        //orderClient.createOrder("52833a747bb0230e804001cab97a8e0d,528342a17bb0230e2817c501bdbb13ba");
        orderClient.myOrders();

       // orderClient.modifyItem("5279e5de500f22b103353bd80a2a0238","5279e5de500f22b103353bd80a2a0239",22);
       // orderClient.deleteItem("5279e5de500f22b103353bd80a2a0238","5279e5de500f22b103353bd80a2a0239");
      // orderClient.updateOrder("5279e5de500f22b103353bd80a2a0238","WEIGHTED");
        //orderClient.updateOrderPayway("527dd03e0fcfc3d96af56a607ab6b319","zhubaob2b_prepay_kshop");
        orderClient.orderDetail("5295c5d0adeba216fce774822bb42617");
        //orderClient.payOrder("527dd03e0fcfc3d96af56a607ab6b319","123456","13333333333","654321");
        //orderClient.queryOrder("all","all","2","0","20");
       // orderClient.venderOrders();

    }
}

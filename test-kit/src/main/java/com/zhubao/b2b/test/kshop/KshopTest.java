package com.zhubao.b2b.test.kshop;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-11
 * Time: 下午1:08
 * To change this template use File | Settings | File Templates.
 */
public class KshopTest {

    public void testLogin(String userName, String passwd){
        try{
            String pass = DigestUtils.md5Hex(passwd);
            GetMethod getMethod = new GetMethod("http://kshop.okgold.com:9980/txy/api/login.do?userName="+userName+"&password="+pass);
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            if (status == 200) {
                System.out.println(getMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testDemand(){
        try {
            PostMethod postMethod = new PostMethod("http://kshop.okgold.com:9980/txy/api/demandOrders.do");
            postMethod.addRequestHeader("Connection", "Keep-Alive");
            postMethod.addRequestHeader("Accept-Charset", "UTF-8");

            //postMethod.addParameter(new NameValuePair("json", "{'ordersn':'123456','agencyId':'523','userId':'582','vagencyId':'1','pmsStr':'1.523.','items':[{'type':'au','weight':10.00,'price':234.00,'addprice':5.00,'goodscode':'1'},{'type':'pt','weight':10.00,'price':234.00,'addprice':5.00,'goodscode':'2'},{'type':'ag','weight':10.00,'price':234.00,'addprice':5.00,'goodscode':'3'},{'type':'zb','totalprice':234.00}]}"));
            postMethod.addParameter(new NameValuePair("json","{'ordersn':'5309ad70d296ce635c044f9bb5d5483b','agencyId':'504','userId':'502','vagencyId':'523','pmsStr':'1.523.','items':[{'type':'au','weight':10.00,'price':234,'addprice':5.00,'goodscode':'5309ad70d296ce635c044f9bb5d5483c'},{'type':'zb','totalprice':1539}]}"));
            //{"ordersn":"5309ad70d296ce635c044f9bb5d5483b","items":[{"goodscode":"5309ad70d296ce635c044f9bb5d5483c","price":280,"weight":0,"addprice":5,"type":"au"},{"totalprice":1539,"type":"zb"}],"userId":"502","agencyId":"504","vagencyId":"523"}
            postMethod.addParameter(new NameValuePair("token", "3057bd103d334c79a64d165193ea771d"));
            HttpClient client = new HttpClient();
            int status = client.executeMethod(postMethod);
            if (status == 200) {
                System.out.println(postMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //{"zb":840,"pt":"XQOD13111200000172","ag":"XQOD13111200000171","au":"XQOD13111200000170"}
    public void testDemandBalance(){
        try {
            PostMethod postMethod = new PostMethod("http://kshop.okgold.com:9980/txy/api/resultsPriceDemandOrders.do");
            postMethod.addRequestHeader("Connection", "Keep-Alive");
            postMethod.addRequestHeader("Accept-Charset", "UTF-8");

            postMethod.addParameter(new NameValuePair("json", "{'ordersn':'123456','agencyId':'523','userId':'582','vagencyId':'1','pmsStr':'1.523.','items':[{'type':'au','weight':10.00,'price':234.00,'addprice':5.00,'goodscode':'1'},{'type':'pt','weight':10.00,'price':234.00,'addprice':5.00,'goodscode':'2'},{'type':'ag','weight':10.00,'price':234.00,'addprice':5.00,'goodscode':'3'},{'type':'zb','totalprice':234.00}]}"));

            postMethod.addParameter(new NameValuePair("token", "3057bd103d334c79a64d165193ea771d"));
            HttpClient client = new HttpClient();
            int status = client.executeMethod(postMethod);
            if (status == 200) {
                System.out.println(postMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //{"pt":"JJOD13111210198354","ag":"JJOD13111210550589","au":"JJOD13111210066721"}
    //{"errorpt":"10003","errorau":"10003","errorag":"10003"}
    public void testBalance(){
        try {
            PostMethod postMethod = new PostMethod("http://10.39.1.111:8088/txy/api/resultsPriceOrder.do");
            postMethod.addRequestHeader("Connection", "Keep-Alive");
            postMethod.addRequestHeader("Accept-Charset", "UTF-8");

            //postMethod.addParameter(new NameValuePair("json", "{'ordersn':'123457','agencyId':'523','userId':'582','vagencyId':'1','items':[{'type':'au','weight':10.00,'totalprice':234.00},{'type':'pt','weight':10.00,'totalprice':234.00},{'type':'ag','weight':10.00,'totalprice':234.00},{'type':'zb','totalprice':234.00}]}"));
            postMethod.addParameter(new NameValuePair("json","{'ordersn':'5309ad70d296ce635c044f9bb5d5483b','agencyId':'504','userId':'502','vagencyId':'523','items':[{'type':'au','weight':10.00,'totalprice':234.00},{'type':'pt','weight':10.00,'totalprice':234.00},{'type':'ag','weight':10.00,'totalprice':234.00},{'type':'zb','totalprice':234.00}]}"));
            //{"ordersn":"5309ad70d296ce635c044f9bb5d5483b","items":[{"goodscode":"5309ad70d296ce635c044f9bb5d5483c","price":280,"weight":0,"addprice":5,"type":"au"},{"totalprice":1539,"type":"zb"}],"userId":"502","agencyId":"504","vagencyId":"523"}
            postMethod.addParameter(new NameValuePair("token", "3057bd103d334c79a64d165193ea771d"));
            HttpClient client = new HttpClient();
            int status = client.executeMethod(postMethod);
            if (status == 200) {
                System.out.println(postMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //{"orderId":958}
    public void testAccountInfo(){
        try {
        GetMethod getMethod = new GetMethod("http://kshop.okgold.com/txy/api/accountUserInfo.do?agencyId=523&token=3057bd103d334c79a64d165193ea771d");
        getMethod.addRequestHeader("Connection", "Keep-Alive");
        getMethod.addRequestHeader("Accept-Charset", "UTF-8");
        HttpClient client = new HttpClient();
        int status = 0;

            status = client.executeMethod(getMethod);

        if (status == 200) {
            System.out.println(getMethod.getResponseBodyAsString());
        } else {
            System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);

        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //[{"payFundsId":955,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:35:45","orderSn":"JJOD13111210066721","payNote":"原料名称：黄金   结价重量:10.00"},{"payFundsId":958,"payFundsNum":936,"payFundsTime":"2013-11-12 16:35:45","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":957,"payFundsNum":1322.43,"payFundsTime":"2013-11-12 16:35:45","orderSn":"JJOD13111210066721","payNote":"原料名称：黄金   结价重量:10.00   当前需求订单的保证金：145091元   按照当前黄金价计算未结价的保证金(1.2)：143768.57元"},{"payFundsId":945,"payFundsNum":234,"payFundsTime":"2013-11-12 16:35:44","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":948,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:35:44","orderSn":"XQOD13111200000202","payNote":"保证金:478.00    "},{"payFundsId":947,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:35:44","orderSn":"XQOD13111200000201","payNote":"保证金:478.00    "},{"payFundsId":946,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:35:44","orderSn":"XQOD13111200000200","payNote":"保证金:478.00    "},{"payFundsId":951,"payFundsNum":1298.15,"payFundsTime":"2013-11-12 16:35:44","orderSn":"JJOD13111210198354","payNote":"原料名称：白银   结价重量:10.00   当前需求订单的保证金：152479.21元   按照当前黄金价计算未结价的保证金(1.2)：151181.06元"},{"payFundsId":952,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:35:44","orderSn":"JJOD13111210550589","payNote":"原料名称：铂金   结价重量:10.00"},{"payFundsId":954,"payFundsNum":1310.06,"payFundsTime":"2013-11-12 16:35:44","orderSn":"JJOD13111210550589","payNote":"原料名称：铂金   结价重量:10.00   当前需求订单的保证金：148791.06元   按照当前黄金价计算未结价的保证金(1.2)：147481.00元"},{"payFundsId":949,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:35:44","orderSn":"JJOD13111210198354","payNote":"原料名称：白银   结价重量:10.00"},{"payFundsId":938,"payFundsNum":1348.09,"payFundsTime":"2013-11-12 16:24:28","orderSn":"JJOD13111210944338","payNote":"原料名称：铂金   结价重量:10.00   当前需求订单的保证金：155127.06元   按照当前黄金价计算未结价的保证金(1.2)：153778.97元"},{"payFundsId":944,"payFundsNum":1373.3,"payFundsTime":"2013-11-12 16:24:28","orderSn":"JJOD13111210302470","payNote":"原料名称：白银   结价重量:10.00   当前需求订单的保证金：147638.51元   按照当前黄金价计算未结价的保证金(1.2)：146265.21元"},{"payFundsId":942,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:24:28","orderSn":"JJOD13111210302470","payNote":"原料名称：白银   结价重量:10.00"},{"payFundsId":941,"payFundsNum":1360.46,"payFundsTime":"2013-11-12 16:24:28","orderSn":"JJOD13111210665824","payNote":"原料名称：黄金   结价重量:10.00   当前需求订单的保证金：151388.97元   按照当前黄金价计算未结价的保证金(1.2)：150028.51元"},{"payFundsId":939,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:24:28","orderSn":"JJOD13111210665824","payNote":"原料名称：黄金   结价重量:10.00"},{"payFundsId":936,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:24:28","orderSn":"JJOD13111210944338","payNote":"原料名称：铂金   结价重量:10.00"},{"payFundsId":932,"payFundsNum":234,"payFundsTime":"2013-11-12 16:24:27","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":933,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:24:27","orderSn":"XQOD13111200000197","payNote":"保证金:478.00    "},{"payFundsId":934,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:24:27","orderSn":"XQOD13111200000198","payNote":"保证金:478.00    "},{"payFundsId":935,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:24:27","orderSn":"XQOD13111200000199","payNote":"保证金:478.00    "},{"payFundsId":926,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:23:25","orderSn":"JJOD13111210205771","payNote":"原料名称：铂金   结价重量:10.00"},{"payFundsId":928,"payFundsNum":1413.91,"payFundsTime":"2013-11-12 16:23:25","orderSn":"JJOD13111210205771","payNote":"原料名称：铂金   结价重量:10.00   当前需求订单的保证金：154144.23元   按照当前黄金价计算未结价的保证金(1.2)：152730.32元"},{"payFundsId":929,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:23:25","orderSn":"JJOD13111210725492","payNote":"原料名称：黄金   结价重量:10.00"},{"payFundsId":931,"payFundsNum":1427.26,"payFundsTime":"2013-11-12 16:23:25","orderSn":"JJOD13111210725492","payNote":"原料名称：黄金   结价重量:10.00   当前需求订单的保证金：150340.32元   按照当前黄金价计算未结价的保证金(1.2)：148913.06元"},{"payFundsId":925,"payFundsNum":1401.06,"payFundsTime":"2013-11-12 16:19:48","orderSn":"JJOD13111210604832","payNote":"原料名称：白银   结价重量:10.00   当前需求订单的保证金：157935.29元   按照当前黄金价计算未结价的保证金(1.2)：156534.23元"},{"payFundsId":923,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:19:48","orderSn":"JJOD13111210604832","payNote":"原料名称：白银   结价重量:10.00"},{"payFundsId":918,"payFundsNum":234,"payFundsTime":"2013-11-12 16:18:13","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":920,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:18:13","orderSn":"XQOD13111200000195","payNote":"保证金:478.00    "},{"payFundsId":922,"payFundsNum":936,"payFundsTime":"2013-11-12 16:18:13","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":919,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:18:13","orderSn":"XQOD13111200000194","payNote":"保证金:478.00    "},{"payFundsId":921,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:18:13","orderSn":"XQOD13111200000196","payNote":"保证金:478.00    "},{"payFundsId":908,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:17:21","orderSn":"JJOD13111210912842","payNote":"原料名称：白银   结价重量:10.00"},{"payFundsId":913,"payFundsNum":1470.59,"payFundsTime":"2013-11-12 16:17:21","orderSn":"JJOD13111210850076","payNote":"原料名称：铂金   结价重量:10.00   当前需求订单的保证金：157066.36元   按照当前黄金价计算未结价的保证金(1.2)：155595.77元"},{"payFundsId":910,"payFundsNum":1457.23,"payFundsTime":"2013-11-12 16:17:21","orderSn":"JJOD13111210912842","payNote":"原料名称：白银   结价重量:10.00   当前需求订单的保证金：160913.59元   按照当前黄金价计算未结价的保证金(1.2)：159456.36元"},{"payFundsId":917,"payFundsNum":936,"payFundsTime":"2013-11-12 16:17:21","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":916,"payFundsNum":1484.48,"payFundsTime":"2013-11-12 16:17:21","orderSn":"JJOD13111210415104","payNote":"原料名称：黄金   结价重量:10.00   当前需求订单的保证金：153205.77元   按照当前黄金价计算未结价的保证金(1.2)：151721.29元"},{"payFundsId":914,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:17:21","orderSn":"JJOD13111210415104","payNote":"原料名称：黄金   结价重量:10.00"},{"payFundsId":911,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:17:21","orderSn":"JJOD13111210850076","payNote":"原料名称：铂金   结价重量:10.00"},{"payFundsId":907,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:17:20","orderSn":"XQOD13111200000193","payNote":"保证金:478.00    "},{"payFundsId":905,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:17:20","orderSn":"XQOD13111200000191","payNote":"保证金:478.00    "},{"payFundsId":906,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:17:20","orderSn":"XQOD13111200000192","payNote":"保证金:478.00    "},{"payFundsId":904,"payFundsNum":234,"payFundsTime":"2013-11-12 16:17:20","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":900,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:09:12","orderSn":"JJOD13111210850852","payNote":"原料名称：黄金   结价重量:10.00"},{"payFundsId":902,"payFundsNum":1545.17,"payFundsTime":"2013-11-12 16:09:12","orderSn":"JJOD13111210850852","payNote":"原料名称：黄金   结价重量:10.00   当前需求订单的保证金：156244.76元   按照当前黄金价计算未结价的保证金(1.2)：154699.59元"},{"payFundsId":903,"payFundsNum":936,"payFundsTime":"2013-11-12 16:09:12","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":899,"payFundsNum":1530.72,"payFundsTime":"2013-11-12 16:09:11","orderSn":"JJOD13111210198132","payNote":"原料名称：白银   结价重量:10.00   当前需求订单的保证金：160165.48元   按照当前黄金价计算未结价的保证金(1.2)：158634.76元"},{"payFundsId":897,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:09:11","orderSn":"JJOD13111210198132","payNote":"原料名称：白银   结价重量:10.00"},{"payFundsId":896,"payFundsNum":1516.8,"payFundsTime":"2013-11-12 16:09:09","orderSn":"JJOD13111210628726","payNote":"原料名称：铂金   结价重量:10.00   当前需求订单的保证金：164072.28元   按照当前黄金价计算未结价的保证金(1.2)：162555.48元"},{"payFundsId":894,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:09:09","orderSn":"JJOD13111210628726","payNote":"原料名称：铂金   结价重量:10.00"},{"payFundsId":892,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:08:58","orderSn":"XQOD13111200000189","payNote":"保证金:478.00    "},{"payFundsId":893,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:08:58","orderSn":"XQOD13111200000190","payNote":"保证金:478.00    "},{"payFundsId":891,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:08:57","orderSn":"XQOD13111200000188","payNote":"保证金:478.00    "},{"payFundsId":890,"payFundsNum":234,"payFundsTime":"2013-11-12 16:08:57","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":887,"payFundsNum":11950,"payFundsTime":"2013-11-12 16:06:31","orderSn":"JJOD13111210084442","payNote":"原料名称：白银   结价重量:10.00原料名称：白银   结价重量:10.00原料名称：白银   结价重量:10.00原料名称：白银   结价重量:10.00原料名称：白银   结价重量:10.00"},{"payFundsId":889,"payFundsNum":7749.03,"payFundsTime":"2013-11-12 16:06:31","orderSn":"JJOD13111210084442","payNote":"原料名称：白银   结价重量:10.00原料名称：白银   结价重量:10.00原料名称：白银   结价重量:10.00原料名称：白银   结价重量:10.00原料名称：白银   结价重量:10.00   当前需求订单的保证金：165607.31元   按照当前黄金价计算未结价的保证金(1.2)：157858.28元"},{"payFundsId":886,"payFundsNum":1536.82,"payFundsTime":"2013-11-12 16:06:23","orderSn":"JJOD13111210283657","payNote":"原料名称：黄金   结价重量:10.00   当前需求订单的保证金：179094.13元   按照当前黄金价计算未结价的保证金(1.2)：177557.31元"},{"payFundsId":884,"payFundsNum":2390,"payFundsTime":"2013-11-12 16:06:23","orderSn":"JJOD13111210283657","payNote":"原料名称：黄金   结价重量:10.00"},{"payFundsId":883,"payFundsNum":3023.87,"payFundsTime":"2013-11-12 16:06:08","orderSn":"JJOD13111210096442","payNote":"原料名称：铂金   结价重量:10.00原料名称：铂金   结价重量:10.00   当前需求订单的保证金：184508元   按照当前黄金价计算未结价的保证金(1.2)：181484.13元"},{"payFundsId":881,"payFundsNum":4780,"payFundsTime":"2013-11-12 16:05:38","orderSn":"JJOD13111210096442","payNote":"原料名称：铂金   结价重量:10.00原料名称：铂金   结价重量:10.00"},{"payFundsId":878,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:03:45","orderSn":"XQOD13111200000185","payNote":"保证金:478.00    "},{"payFundsId":880,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:03:45","orderSn":"XQOD13111200000187","payNote":"保证金:478.00    "},{"payFundsId":879,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:03:45","orderSn":"XQOD13111200000186","payNote":"保证金:478.00    "},{"payFundsId":877,"payFundsNum":234,"payFundsTime":"2013-11-12 16:03:45","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":876,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:01:27","orderSn":"XQOD13111200000184","payNote":"保证金:478.00    "},{"payFundsId":874,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:01:26","orderSn":"XQOD13111200000182","payNote":"保证金:478.00    "},{"payFundsId":873,"payFundsNum":234,"payFundsTime":"2013-11-12 16:01:26","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":875,"payFundsNum":2868,"payFundsTime":"2013-11-12 16:01:26","orderSn":"XQOD13111200000183","payNote":"保证金:478.00    "},{"payFundsId":870,"payFundsNum":2868,"payFundsTime":"2013-11-12 15:51:37","orderSn":"XQOD13111200000179","payNote":"保证金:478.00    "},{"payFundsId":871,"payFundsNum":2868,"payFundsTime":"2013-11-12 15:51:37","orderSn":"XQOD13111200000180","payNote":"保证金:478.00    "},{"payFundsId":872,"payFundsNum":2868,"payFundsTime":"2013-11-12 15:51:37","orderSn":"XQOD13111200000181","payNote":"保证金:478.00    "},{"payFundsId":869,"payFundsNum":234,"payFundsTime":"2013-11-12 15:51:36","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":867,"payFundsNum":2868,"payFundsTime":"2013-11-12 15:51:32","orderSn":"XQOD13111200000177","payNote":"保证金:478.00    "},{"payFundsId":868,"payFundsNum":2868,"payFundsTime":"2013-11-12 15:51:32","orderSn":"XQOD13111200000178","payNote":"保证金:478.00    "},{"payFundsId":866,"payFundsNum":2868,"payFundsTime":"2013-11-12 15:51:31","orderSn":"XQOD13111200000176","payNote":"保证金:478.00    "},{"payFundsId":865,"payFundsNum":234,"payFundsTime":"2013-11-12 15:51:31","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":864,"payFundsNum":2868,"payFundsTime":"2013-11-12 15:51:22","orderSn":"XQOD13111200000175","payNote":"保证金:478.00    "},{"payFundsId":863,"payFundsNum":2868,"payFundsTime":"2013-11-12 15:51:22","orderSn":"XQOD13111200000174","payNote":"保证金:478.00    "},{"payFundsId":861,"payFundsNum":234,"payFundsTime":"2013-11-12 15:51:21","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":862,"payFundsNum":2868,"payFundsTime":"2013-11-12 15:51:21","orderSn":"XQOD13111200000173","payNote":"保证金:478.00    "},{"payFundsId":843,"payFundsNum":2868,"payFundsTime":"2013-11-12 11:52:02","orderSn":"XQOD13111200000172","payNote":"保证金:478.00    "},{"payFundsId":841,"payFundsNum":2868,"payFundsTime":"2013-11-12 11:52:01","orderSn":"XQOD13111200000170","payNote":"保证金:478.00    "},{"payFundsId":842,"payFundsNum":2868,"payFundsTime":"2013-11-12 11:52:01","orderSn":"XQOD13111200000171","payNote":"保证金:478.00    "},{"payFundsId":840,"payFundsNum":234,"payFundsTime":"2013-11-12 11:52:00","orderSn":"ZB","payNote":"珠宝结价"},{"payFundsId":838,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:54:00","orderSn":"XQOD13111200000169","payNote":"保证金:478.00    "},{"payFundsId":836,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:53:59","orderSn":"XQOD13111200000167","payNote":"保证金:478.00    "},{"payFundsId":837,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:53:59","orderSn":"XQOD13111200000168","payNote":"保证金:478.00    "},{"payFundsId":835,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:52:27","orderSn":"XQOD13111200000166","payNote":"保证金:478.00    "},{"payFundsId":834,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:51:55","orderSn":"XQOD13111200000165","payNote":"保证金:478.00    "},{"payFundsId":833,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:51:38","orderSn":"XQOD13111200000164","payNote":"保证金:478.00    "},{"payFundsId":832,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:50:03","orderSn":"XQOD13111200000163","payNote":"保证金:478.00    "},{"payFundsId":830,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:50:03","orderSn":"XQOD13111200000161","payNote":"保证金:478.00    "},{"payFundsId":831,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:50:03","orderSn":"XQOD13111200000162","payNote":"保证金:478.00    "},{"payFundsId":829,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:42:50","orderSn":"XQOD13111200000160","payNote":"保证金:478.00    "},{"payFundsId":828,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:41:57","orderSn":"XQOD13111200000159","payNote":"保证金:478.00    "},{"payFundsId":827,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:40:39","orderSn":"XQOD13111200000158","payNote":"保证金:478.00    "},{"payFundsId":826,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:40:08","orderSn":"XQOD13111200000157","payNote":"保证金:478.00    "},{"payFundsId":825,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:39:40","orderSn":"XQOD13111200000156","payNote":"保证金:478.00    "},{"payFundsId":823,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:35:18","orderSn":"XQOD13111200000154","payNote":"保证金:478.00    "},{"payFundsId":824,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:35:18","orderSn":"XQOD13111200000155","payNote":"保证金:478.00    "},{"payFundsId":822,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:35:03","orderSn":"XQOD13111200000153","payNote":"保证金:478.00    "},{"payFundsId":821,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:27:32","orderSn":"XQOD13111200000152","payNote":"保证金:478.00    "},{"payFundsId":820,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:27:31","orderSn":"XQOD13111200000151","payNote":"保证金:478.00    "},{"payFundsId":819,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:27:30","orderSn":"XQOD13111200000150","payNote":"保证金:478.00    "},{"payFundsId":818,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:26:45","orderSn":"XQOD13111200000149","payNote":"保证金:478.00    "},{"payFundsId":817,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:26:44","orderSn":"XQOD13111200000148","payNote":"保证金:478.00    "},{"payFundsId":816,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:26:42","orderSn":"XQOD13111200000147","payNote":"保证金:478.00    "},{"payFundsId":809,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:12:36","orderSn":"XQOD13111200000140","payNote":"保证金:478.00    "},{"payFundsId":808,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:12:31","orderSn":"XQOD13111200000139","payNote":"保证金:478.00    "},{"payFundsId":807,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:12:17","orderSn":"XQOD13111200000138","payNote":"保证金:478.00    "},{"payFundsId":806,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:11:43","orderSn":"XQOD13111200000137","payNote":"保证金:478.00    "},{"payFundsId":805,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:11:24","orderSn":"XQOD13111200000136","payNote":"保证金:478.00    "},{"payFundsId":804,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:10:54","orderSn":"XQOD13111200000135","payNote":"保证金:478.00    "},{"payFundsId":803,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:10:28","orderSn":"XQOD13111200000134","payNote":"保证金:478.00    "},{"payFundsId":802,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:09:52","orderSn":"XQOD13111200000133","payNote":"保证金:478.00    "},{"payFundsId":801,"payFundsNum":2868,"payFundsTime":"2013-11-12 10:09:02","orderSn":"XQOD13111200000132","payNote":"保证金:478.00    "},{"payFundsId":782,"payFundsNum":2868,"payFundsTime":"2013-11-11 17:23:22","orderSn":"XQOD13111100000131","payNote":"保证金:478.00    "},{"payFundsId":781,"payFundsNum":2868,"payFundsTime":"2013-11-11 17:11:45","orderSn":"XQOD13111100000130","payNote":"保证金:478.00    "},{"payFundsId":780,"payFundsNum":2868,"payFundsTime":"2013-11-11 17:11:44","orderSn":"XQOD13111100000129","payNote":"保证金:478.00    "},{"payFundsId":779,"payFundsNum":2868,"payFundsTime":"2013-11-11 17:11:43","orderSn":"XQOD13111100000128","payNote":"保证金:478.00    "},{"payFundsId":778,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:36:42","orderSn":"XQOD13111100000127","payNote":"保证金:478.00    "},{"payFundsId":777,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:36:41","orderSn":"XQOD13111100000126","payNote":"保证金:478.00    "},{"payFundsId":776,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:36:40","orderSn":"XQOD13111100000125","payNote":"保证金:478.00    "},{"payFundsId":775,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:36:36","orderSn":"XQOD13111100000124","payNote":"保证金:478.00    "},{"payFundsId":774,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:35:29","orderSn":"XQOD13111100000123","payNote":"保证金:478.00    "},{"payFundsId":773,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:33:10","orderSn":"XQOD13111100000122","payNote":"保证金:478.00    "},{"payFundsId":772,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:21:58","orderSn":"XQOD13111100000121","payNote":"保证金:478.00    "},{"payFundsId":771,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:21:58","orderSn":"XQOD13111100000120","payNote":"保证金:478.00    "},{"payFundsId":770,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:21:57","orderSn":"XQOD13111100000119","payNote":"保证金:478.00    "},{"payFundsId":769,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:21:24","orderSn":"XQOD13111100000118","payNote":"保证金:478.00    "},{"payFundsId":768,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:21:23","orderSn":"XQOD13111100000117","payNote":"保证金:478.00    "},{"payFundsId":767,"payFundsNum":2868,"payFundsTime":"2013-11-11 16:21:15","orderSn":"XQOD13111100000116","payNote":"保证金:478.00    "},{"payFundsId":761,"payFundsNum":100000,"payFundsTime":"2013-11-11 15:05:29","orderSn":" ","payNote":"申请充值"}]

    public static void main(String[] args){
        KshopTest test = new KshopTest();
        test.testLogin("maomao","mm");
        //test.testDemand();
        //test.testDemandBalance();
        //test.testBalance();
        //test.testAccountInfo();
        //KShopHelper kShopHelper = new KShopHelper(10, "kshop.okgold.com:9980", 1000);

    }
}

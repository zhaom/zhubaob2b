package com.zhubao.b2b.test.api;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class OrderResourceClient  {

    public void createOrder(String ids) {
        PostMethod postMethod = new PostMethod("http://localhost:8080/api/1/order");
        postMethod.addRequestHeader("uid", "5296219594eb45ce3732ee2447f2d003");
        postMethod.addRequestHeader("Connection", "Keep-Alive");
        postMethod.addRequestHeader("Accept-Charset", "UTF-8");

        postMethod.addParameter(new NameValuePair("gids", ids));

        HttpClient client = new HttpClient();
        try {
            int status = client.executeMethod(postMethod);
            System.out.println("status-->"+status);
            System.out.println("response-->"+postMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void myOrders(){
        GetMethod getMethod = new GetMethod("http://localhost:8080/api/1/order/my");
        getMethod.addRequestHeader("uid", "5296219594eb45ce3732ee2447f2d003");
        HttpClient client = new HttpClient();
        try {
            int status = client.executeMethod(getMethod);
            System.out.println("status-->"+status);
            System.out.println("response-->"+getMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void orderDetail(String orderId){
        GetMethod getMethod = new GetMethod("http://localhost:8080/api/1/order/detail/"+orderId);
        getMethod.addRequestHeader("uid", "5296219594eb45ce3732ee2447f2d003");
        HttpClient client = new HttpClient();
        try {
            int status = client.executeMethod(getMethod);
            System.out.println("status-->"+status);
            System.out.println("response-->"+getMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifyItem(String orderId, String orderGoodsId, int count){

       PostMethod postMethod = new PostMethod("http://localhost:8080/api/1/order/"+orderId+"/goods/item");
       postMethod.addRequestHeader("uid", "5296219594eb45ce3732ee2447f2d003");
       postMethod.addRequestHeader("Connection", "Keep-Alive");
       postMethod.addRequestHeader("Accept-Charset", "UTF-8");

       postMethod.addParameter(new NameValuePair("ogid", orderGoodsId));
       postMethod.addParameter(new NameValuePair("count", ""+count));

       HttpClient client = new HttpClient();
       try {
           int status = client.executeMethod(postMethod);
           System.out.println("status-->"+status);
           System.out.println("response-->"+postMethod.getResponseBodyAsString());
       } catch (IOException e) {
            e.printStackTrace();
       }
    }

    public void deleteItem(String orderId, String orderGoodsId){
        DeleteMethod deleteMethod = new DeleteMethod("http://localhost:8080/api/1/order/"+orderId+"/goods/item/"+orderGoodsId);
        deleteMethod.addRequestHeader("uid", "1");
        HttpClient client = new HttpClient();
        try {
            int status = client.executeMethod(deleteMethod);
            System.out.println("status-->"+status);
            System.out.println("response-->"+deleteMethod.getResponseBodyAsString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateOrder(String orderId, String status){
        PostMethod postMethod = new PostMethod("http://localhost:8080/api/1/order/"+orderId+"/status");
        postMethod.addRequestHeader("uid", "5296219594eb45ce3732ee2447f2d003");
        postMethod.addRequestHeader("Connection", "Keep-Alive");
        postMethod.addRequestHeader("Accept-Charset", "UTF-8");

        postMethod.addParameter(new NameValuePair("status", status));

        HttpClient client = new HttpClient();
        try {
            int st = client.executeMethod(postMethod);
            System.out.println("status-->"+st);
            System.out.println("response-->"+postMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateOrderPayway(String orderId, String paywayId){
        PostMethod postMethod = new PostMethod("http://localhost:8080/api/1/order/"+orderId+"/payway");
        postMethod.addRequestHeader("uid", "1");
        postMethod.addRequestHeader("Connection", "Keep-Alive");
        postMethod.addRequestHeader("Accept-Charset", "UTF-8");

        postMethod.addParameter(new NameValuePair("payway", paywayId));

        HttpClient client = new HttpClient();
        try {
            int st = client.executeMethod(postMethod);
            System.out.println("status-->"+st);
            System.out.println("response-->"+postMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void payOrder(String orderId, String passwd, String mobile, String vcode){
        PostMethod postMethod = new PostMethod("http://localhost:8080/api/2/order/"+orderId+"/pay");
        postMethod.addRequestHeader("uid", "5296219594eb45ce3732ee2447f2d003");
        postMethod.addRequestHeader("Connection", "Keep-Alive");
        postMethod.addRequestHeader("Accept-Charset", "UTF-8");

        postMethod.addParameter(new NameValuePair("passwd", passwd));
        postMethod.addParameter(new NameValuePair("mobile", mobile));
        postMethod.addParameter(new NameValuePair("vcode", vcode));

        HttpClient client = new HttpClient();
        try {
            int st = client.executeMethod(postMethod);
            System.out.println("status-->"+st);
            System.out.println("response-->"+postMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queryOrder(String status, String venderid,String qtime, String pIndex, String pCount){
        GetMethod getMethod = new GetMethod("http://localhost:8080/api/1/order/list/"+status+"_"+venderid+"_"+qtime+"_"+pIndex+"_"+pCount);
        getMethod.addRequestHeader("uid", "5296219594eb45ce3732ee2447f2d003");
        HttpClient client = new HttpClient();
        try {
            int st = client.executeMethod(getMethod);
            System.out.println("status-->"+st);
            System.out.println("response-->"+getMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void venderOrders(){
        GetMethod getMethod = new GetMethod("http://localhost:8080/api/1/order/list/vender_doing/5267850370761ffc1e61503f19948ebd_0_20");
        getMethod.addRequestHeader("uid", "5267850370761ffc1e61503f19948ebd");
        HttpClient client = new HttpClient();
        try {
            int st = client.executeMethod(getMethod);
            System.out.println("status-->"+st);
            System.out.println("response-->"+getMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

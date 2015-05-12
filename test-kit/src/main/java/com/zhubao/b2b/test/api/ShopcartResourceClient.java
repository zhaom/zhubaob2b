package com.zhubao.b2b.test.api;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-1-26
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
public class ShopcartResourceClient {

    public void addGoodsToShopCart(String uid, String goodsId, String skuId, int amount) {
        PostMethod postMethod = new PostMethod("http://localhost:8080/api/1/order/shopcart/add-goods");
        postMethod.addRequestHeader("uid", uid);
        postMethod.addRequestHeader("Connection", "Keep-Alive");
        postMethod.addRequestHeader("Accept-Charset", "UTF-8");

        postMethod.addParameter(new NameValuePair("goodsId", goodsId));
        postMethod.addParameter(new NameValuePair("skuId", skuId));
        postMethod.addParameter(new NameValuePair("amount", String.valueOf(amount)));

        HttpClient client = new HttpClient();
        try {
            int status = client.executeMethod(postMethod);
            System.out.println("status-->"+status);
            System.out.println("response-->"+postMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void modifyShopCartGoodsAmount(String uid, String shopcartgoodsId, int amount){
        PostMethod postMethod = new PostMethod("http://localhost:8080/api/1/order/shopcart/modify-goods-amount");
        postMethod.addRequestHeader("uid", uid);
        postMethod.addRequestHeader("Connection", "Keep-Alive");
        postMethod.addRequestHeader("Accept-Charset", "UTF-8");

        postMethod.addParameter(new NameValuePair("shopCartGoodsId", shopcartgoodsId));
        postMethod.addParameter(new NameValuePair("amount", String.valueOf(amount)));

        HttpClient client = new HttpClient();
        try {
            int status = client.executeMethod(postMethod);
            System.out.println("status-->"+status);
            System.out.println("response-->"+postMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getShopCart(String uid){
        GetMethod getMethod = new GetMethod("http://localhost:8080/api/1/order/shopcart/item");
        getMethod.addRequestHeader("uid", uid);
        HttpClient client = new HttpClient();
        try {
            int status = client.executeMethod(getMethod);
            System.out.println("status-->"+status);
            System.out.println("response-->"+getMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

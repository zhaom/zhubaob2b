package com.zhubao.b2b.test.api;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
public class GoodsResourceClient {

    public void listPaginationGoods(int page, int pageSize, String order, String orderBy,String ...params) {
        GetMethod getMethod = new GetMethod("http://localhost:8080/api/0/goods/list?order=desc&orderby=sellCount");
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

    public static void main(String[] args){
        GoodsResourceClient client = new GoodsResourceClient();
        client.listPaginationGoods(1,10,"","");
    }
}

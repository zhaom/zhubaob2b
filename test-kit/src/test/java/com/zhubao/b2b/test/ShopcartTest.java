package com.zhubao.b2b.test;

import com.zhubao.b2b.test.api.ShopcartResourceClient;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-1-26
 * Time: 下午6:15
 * To change this template use File | Settings | File Templates.
 */
public class ShopcartTest
{

    public static void main(String[] args){
        ShopcartResourceClient shopcartResourceClient = new ShopcartResourceClient();

       // shopcartResourceClient.addGoodsToShopCart("5296219594eb45ce3732ee2447f2d003", "529618e094eb45ce34dd769e09945bd6", "529618e094eb45ce34dd769e09945bd7", 2);
       //shopcartResourceClient.addGoodsToShopCart("5296219594eb45ce3732ee2447f2d003", "529618e094eb45ce34dd769e09945bd6", "529618e094eb45ce34dd769e09945bd8", 3);
        shopcartResourceClient.modifyShopCartGoodsAmount("5296219594eb45ce3732ee2447f2d003", "52e4c24dfee149a02f5888359752124c", 30);
        shopcartResourceClient.getShopCart("5296219594eb45ce3732ee2447f2d003");
    }

}

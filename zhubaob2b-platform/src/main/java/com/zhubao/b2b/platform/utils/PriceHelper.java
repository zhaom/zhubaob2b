package com.zhubao.b2b.platform.utils;

import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.entity.GoodsPrice;
import com.zhubao.b2b.platform.entity.KShopAgencyPrice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PriceHelper {

    private KShopHelper kShopHelper;

    public void setkShopHelper(KShopHelper kShopHelper) {
        this.kShopHelper = kShopHelper;
    }

    public float calculateTotalPrice(List<GoodsPrice> prices, String material, float weight, float price, int isFixedPrice, int amount) {
        float totalPrice;

        if (isFixedPrice == 1) {
            totalPrice = price * amount;
        } else {
            totalPrice = getItemPrice(prices, material, weight) * amount;
        }

        return (float)(Math.round(totalPrice * 100)) / 100;
    }

    private float getItemPrice(List<GoodsPrice> prices, String material, float weight) {
        float itemPrice = 0f;

        if (prices != null) {
            float rtPrice = getRtSellPrice(material);

            for (GoodsPrice price : prices) {
                if (Constants.GOODS_PRICE_TYPE_VALUATED_MANUALFEE.equalsIgnoreCase(price.getType()) || Constants.GOODS_PRICE_TYPE_VALUATED_MATERIALFEE.equalsIgnoreCase(price.getType())) {
                    itemPrice += rtPrice * weight;
                } else if (Constants.GOODS_PRICE_TYPE_FIXED_MATERIALFEE.equalsIgnoreCase(price.getType()) || Constants.GOODS_PRICE_TYPE_FIXED_MANUALFEE.equalsIgnoreCase(price.getType())) {
                    itemPrice += price.getPrice();
                }
            }
        }

        return itemPrice;
    }

    private float getRtSellPrice(String material) {
        double rtSellPrice = 0d;

        KShopAgencyPrice agencyPrice = kShopHelper.doGetPrice("3057bd103d334c79a64d165193ea771d");
        if (agencyPrice != null) {
            if (Constants.GOODS_MATERIAL_AG.equalsIgnoreCase(material))
                rtSellPrice = agencyPrice.getAG_SELL() + agencyPrice.getADD_AG_SELL();
            else if (Constants.GOODS_MATERIAL_AU.equalsIgnoreCase(material))
                rtSellPrice = agencyPrice.getAU_SELL() + agencyPrice.getADD_AU_SELL();
            else if (Constants.GOODS_MATERIAL_PT.equalsIgnoreCase(material))
                rtSellPrice = agencyPrice.getPT_SELL() + agencyPrice.getADD_PT_SELL();
        }

        return (float)(Math.round(rtSellPrice * 100)) / 100;
    }


    public static void main(String[] args){
        KShopHelper kShop = new KShopHelper(32,"http://localhost", 5*60*1000L);
        PriceHelper priceHelper = new PriceHelper();
        priceHelper.setkShopHelper(kShop);

        List<GoodsPrice> goodsPriceList = new ArrayList<GoodsPrice>();
        float r = priceHelper.calculateTotalPrice(goodsPriceList, "AU", 25.55f, 250.55f, 1, 25 );
        System.out.println("result-->"+r);

        GoodsPrice goodsPrice = new GoodsPrice();
        goodsPrice.setType(Constants.GOODS_PRICE_TYPE_VALUATED_MANUALFEE);
        goodsPrice.setPrice(5f);
        goodsPriceList.add(goodsPrice);

        goodsPrice = new GoodsPrice();
        goodsPrice.setType(Constants.GOODS_PRICE_TYPE_VALUATED_MATERIALFEE);
        goodsPrice.setPrice(255f);
        goodsPriceList.add(goodsPrice);

        goodsPrice = new GoodsPrice();
        goodsPrice.setType(Constants.GOODS_PRICE_TYPE_FIXED_MATERIALFEE);
        goodsPrice.setPrice(2567f);
        goodsPriceList.add(goodsPrice);

        r = priceHelper.calculateTotalPrice(goodsPriceList, "AU", 255.55f, 325.55f, 0, 5);

        System.out.println("result-->" + r);

    }

}

package com.zhubao.b2b.manage;

import com.zhubao.b2b.platform.model.GoodsStyle;
import com.zhubao.b2b.platform.service.GoodsStyleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: xiaoping lu
 * Date: 13-9-27
 * Time: 上午11:06
 */
public class InitGoodsStyle {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GoodsStyleService goodsStyleService = (GoodsStyleService) context.getBean("goodsStyleService");

        GoodsStyle style = new GoodsStyle();
        style.setId("necklace");
        style.setName("项链/吊坠");
        style.setOrderNum(9);
        goodsStyleService.createStyle(style);

        style = new GoodsStyle();
        style.setId("lian");
        style.setName("手镯/手串/手链/脚链");
        style.setOrderNum(8);
        goodsStyleService.createStyle(style);

        style = new GoodsStyle();
        style.setId("jian");
        style.setName("挂件/摆件/把件");
        style.setOrderNum(5);
        goodsStyleService.createStyle(style);

        style = new GoodsStyle();
        style.setId("ring");
        style.setName("戒指");
        style.setOrderNum(10);
        goodsStyleService.createStyle(style);

        style = new GoodsStyle();
        style.setId("earring");
        style.setName("耳饰");
        style.setOrderNum(7);
        goodsStyleService.createStyle(style);

        style = new GoodsStyle();
        style.setId("headdress");
        style.setName("头饰");
        style.setOrderNum(6);
        goodsStyleService.createStyle(style);

        style = new GoodsStyle();
        style.setId("fitting");
        style.setName("饰品配件");
        style.setOrderNum(4);
        goodsStyleService.createStyle(style);

        style = new GoodsStyle();
        style.setId("other");
        style.setName("其他");
        style.setOrderNum(3);
        goodsStyleService.createStyle(style);
    }
}

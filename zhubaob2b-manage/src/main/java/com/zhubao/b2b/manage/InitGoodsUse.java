package com.zhubao.b2b.manage;

import com.zhubao.b2b.platform.model.GoodsUse;
import com.zhubao.b2b.platform.service.GoodsUseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: xiaoping lu
 * Date: 13-9-27
 * Time: 上午10:40
 */
public class InitGoodsUse {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GoodsUseService goodsUseService = (GoodsUseService) context.getBean("goodsUseService");

        GoodsUse use = new GoodsUse();
        use.setId("constellation");
        use.setName("星座系列");
        use.setOrderNum(8);
        goodsUseService.createUse(use);

        use.setId("marriage");
        use.setName("婚嫁系列");
        use.setOrderNum(10);
        goodsUseService.createUse(use);

        use.setId("investment");
        use.setName("投资系列");
        use.setOrderNum(9);
        goodsUseService.createUse(use);

        use.setId("animals");
        use.setName("十二生肖系列");
        use.setOrderNum(7);
        goodsUseService.createUse(use);
    }
}

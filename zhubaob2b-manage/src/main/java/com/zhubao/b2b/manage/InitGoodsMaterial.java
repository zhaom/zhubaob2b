package com.zhubao.b2b.manage;

import com.zhubao.b2b.platform.model.GoodsMaterial;
import com.zhubao.b2b.platform.service.GoodsMaterialService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: xiaoping lu
 * Date: 13-9-27
 * Time: 上午9:29
 */
public class InitGoodsMaterial {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GoodsMaterialService goodsMaterialService = (GoodsMaterialService) context.getBean("goodsMaterialService");

        GoodsMaterial material = new GoodsMaterial();
        material.setId("AG");
        material.setName("白银");
        material.setAlias("silver");
        material.setOrderNum(8);
        goodsMaterialService.createMaterial(material);

        material = new GoodsMaterial();
        material.setId("AU");
        material.setName("黄金");
        material.setAlias("gold");
        material.setOrderNum(10);
        goodsMaterialService.createMaterial(material);

        material = new GoodsMaterial();
        material.setId("PT");
        material.setName("铂金");
        material.setAlias("platinum");
        material.setOrderNum(9);
        goodsMaterialService.createMaterial(material);
    }
}

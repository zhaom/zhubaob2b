package com.zhubao.b2b.manage;

import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.platform.model.GoodsSkuAttribute;
import com.zhubao.b2b.platform.model.GoodsSkuAttributeValue;
import com.zhubao.b2b.platform.service.GoodsSkuAttributeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

/**
 * User: xiaoping lu
 * Date: 13-10-22
 * Time: 下午3:25
 */
public class InitGoodsSkuAttribute2 {

     /*
       手链/手镯
     */

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GoodsSkuAttributeService goodsSkuAttributeService = (GoodsSkuAttributeService) context.getBean("goodsSkuAttributeService");
        goodsSkuAttributeService.createSkuAttribute(attr1());
    }

    private static GoodsSkuAttribute attr1() {
        GoodsSkuAttribute attr = new GoodsSkuAttribute();
        attr.setId(IdFactory.generateId());
        attr.setStyleId("lian");
        attr.setName("手镯圈号");
        attr.setValues(new ArrayList<GoodsSkuAttributeValue>());

        GoodsSkuAttributeValue attrValue = new GoodsSkuAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("45cm");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsSkuAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("50cm");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsSkuAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("55cm");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        return attr;
    }

}

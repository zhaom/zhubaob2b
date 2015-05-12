package com.zhubao.b2b.manage;

import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.platform.model.GoodsAttribute;
import com.zhubao.b2b.platform.model.GoodsAttributeValue;
import com.zhubao.b2b.platform.service.GoodsAttributeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

/**
 * User: xiaoping lu
 * Date: 13-10-22
 * Time: 下午3:00
 */
public class InitGoodsAttribute2 {

    /**
     * 黄金：戒指
     */

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GoodsAttributeService goodsAttributeService = (GoodsAttributeService) context.getBean("goodsAttributeService");
        goodsAttributeService.createAttribute(attr1());
        goodsAttributeService.createAttribute(attr2());
        goodsAttributeService.createAttribute(attr3());
    }

    private static GoodsAttribute attr1() {
        GoodsAttribute attr = new GoodsAttribute();
        attr.setId(IdFactory.generateId());
        attr.setMaterialId("AU");
        attr.setStyleId("ring");
        attr.setName("成色");
        attr.setValues(new ArrayList<GoodsAttributeValue>());

        GoodsAttributeValue attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("990");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("995");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("999");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("9999");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        return attr;
    }

    private static GoodsAttribute attr2() {
        GoodsAttribute attr = new GoodsAttribute();
        attr.setId(IdFactory.generateId());
        attr.setMaterialId("AU");
        attr.setStyleId("ring");
        attr.setName("成色");
        attr.setValues(new ArrayList<GoodsAttributeValue>());

        GoodsAttributeValue attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("活扣");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("死扣");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        return attr;
    }

    private static GoodsAttribute attr3() {
        GoodsAttribute attr = new GoodsAttribute();
        attr.setId(IdFactory.generateId());
        attr.setMaterialId("AU");
        attr.setStyleId("ring");
        attr.setName("称重商品重量");
        attr.setValues(new ArrayList<GoodsAttributeValue>());

        GoodsAttributeValue attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("2g");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("5g");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        return attr;
    }

}

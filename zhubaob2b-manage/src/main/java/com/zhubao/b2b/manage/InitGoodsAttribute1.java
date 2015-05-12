package com.zhubao.b2b.manage;

import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.platform.model.GoodsAttribute;
import com.zhubao.b2b.platform.model.GoodsAttributeValue;
import com.zhubao.b2b.platform.service.GoodsAttributeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

/**
 * User: kin
 * Date: 13-9-27
 * Time: 下午2:34
 */
public class InitGoodsAttribute1 {

    /**
     * 黄金：项链
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
        attr.setStyleId("necklace");
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
        attr.setStyleId("necklace");
        attr.setName("样式");
        attr.setValues(new ArrayList<GoodsAttributeValue>());

        GoodsAttributeValue attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("水波纹");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("满天星");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("绞丝纹");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        return attr;
    }

    private static GoodsAttribute attr3() {
        GoodsAttribute attr = new GoodsAttribute();
        attr.setId(IdFactory.generateId());
        attr.setMaterialId("AU");
        attr.setStyleId("necklace");
        attr.setName("称重商品重量");
        attr.setValues(new ArrayList<GoodsAttributeValue>());

        GoodsAttributeValue attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("1g");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("2g");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("3g");
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
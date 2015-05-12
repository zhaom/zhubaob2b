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
 * Time: 下午3:15
 */
public class InitGoodsSkuAttribute1 {

    /*
       戒指
     */

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GoodsSkuAttributeService goodsSkuAttributeService = (GoodsSkuAttributeService) context.getBean("goodsSkuAttributeService");
        goodsSkuAttributeService.createSkuAttribute(attr1());
        goodsSkuAttributeService.createSkuAttribute(attr2());
    }

    private static GoodsSkuAttribute attr1() {
        GoodsSkuAttribute attr = new GoodsSkuAttribute();
        attr.setId(IdFactory.generateId());
        attr.setStyleId("ring");
        attr.setName("镶嵌宝石");
        attr.setValues(new ArrayList<GoodsSkuAttributeValue>());

        GoodsSkuAttributeValue attrValue = new GoodsSkuAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("镶嵌红宝石");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsSkuAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("镶嵌蓝宝石");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        return attr;
    }

    private static GoodsSkuAttribute attr2() {
        GoodsSkuAttribute attr = new GoodsSkuAttribute();
        attr.setId(IdFactory.generateId());
        attr.setStyleId("ring");
        attr.setName("戒指圈号");
        attr.setValues(new ArrayList<GoodsSkuAttributeValue>());

        GoodsSkuAttributeValue attrValue = new GoodsSkuAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("12#");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsSkuAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("13#");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        attrValue = new GoodsSkuAttributeValue();
        attrValue.setId(IdFactory.generateId());
        attrValue.setValue("14#");
        attrValue.setAttributeId(attr.getId());
        attr.getValues().add(attrValue);

        return attr;
    }

}

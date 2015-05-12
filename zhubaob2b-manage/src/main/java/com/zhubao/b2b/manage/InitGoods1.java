package com.zhubao.b2b.manage;

import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.platform.model.Goods;
import com.zhubao.b2b.platform.model.GoodsSku;
import com.zhubao.b2b.platform.service.GoodsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: xiaoping lu
 * Date: 13-10-23
 * Time: 下午4:22
 */
public class InitGoods1 {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GoodsService goodsService = (GoodsService) context.getBean("goodsService");

        goodsService.createGoods(goods1());
        goodsService.createGoods(goods2());
        goodsService.createGoods(goods3());
    }

    private static Goods goods1() {
        Goods goods = new Goods();
        goods.setId(IdFactory.generateId());
        goods.setCode("12538");
        goods.setName("ROVEE韩版时尚百搭纯银八字扣天然珍珠项链毛衣链长链");
        goods.setImg("http://img10.360buyimg.com/n5/g14/M00/15/0F/rBEhVlJc6bEIAAAAAACt_MkNV_oAAEMpgPDKawAAK4U555.jpg");
        goods.setOnShelfTime(System.currentTimeMillis());
        goods.setOffShelfTime(System.currentTimeMillis());
        goods.setMaterialId("5260abfc70761ffcbae5cb77f05f3c50");
        goods.setStyleId("5260ac9470761ffcc1b22649d21c52f6");
        goods.setVenderId("5267850370761ffc1e61503f19948ebd");
        goods.setUseIds(Arrays.asList(new String[]{"5260b0bb70761ffcfbfc528ebf343dad", "5260b0bb70761ffcfbfc528ebf343daf"}));
        goods.setAttrValueIds(Arrays.asList(new String[]{"5266224570761ffca143a492c7c635e2", "5266224570761ffca143a492c7c635e7", "5266224570761ffca143a492c7c635ea"}));
        goods.setSkus(new ArrayList<GoodsSku>());

        GoodsSku sku = new GoodsSku();
        sku.setId(IdFactory.generateId());
        sku.setGoodsId(goods.getId());
        sku.setImg("http://img11.360buyimg.com/n5/g14/M02/12/0D/rBEhVVJEE3QIAAAAAAKGerfuLwEAADmSACWGfQAAoaS141.jpg");
        sku.setCurCount(1000);
        sku.setFreezeCount(50);
        sku.setSellCount(300);
        sku.setSkuAttrValueIds(Arrays.asList(new String[]{"", ""}));
        goods.getSkus().add(sku);

        return goods;
    }

    private static Goods goods2() {
        Goods goods = new Goods();
        goods.setId(IdFactory.generateId());
        goods.setCode("22538");
        goods.setName("阿帕契 招财守财3A级天然冰种黑曜石貔貅情侣吊坠");
        goods.setImg("http://img11.360buyimg.com/n5/g14/M02/12/0D/rBEhV1JEE3cIAAAAAAH9zCwo7mwAADmSADVmVAAAf3k413.jpg");
        goods.setOnShelfTime(System.currentTimeMillis());
        goods.setOffShelfTime(System.currentTimeMillis());
        goods.setMaterialId("5260abfc70761ffcbae5cb77f05f3c50");
        goods.setStyleId("5260ac9470761ffcc1b22649d21c52f6");
        goods.setVenderId("5267850370761ffc1e61503f19948ebd");
        goods.setUseIds(Arrays.asList(new String[]{"5260b0bb70761ffcfbfc528ebf343dad", "5260b0bb70761ffcfbfc528ebf343dae"}));
        goods.setAttrValueIds(Arrays.asList(new String[]{"5266224570761ffca143a492c7c635e2", "5266224570761ffca143a492c7c635e7", "5266224570761ffca143a492c7c635ea"}));
        goods.setSkus(new ArrayList<GoodsSku>());

        GoodsSku sku = new GoodsSku();
        sku.setId(IdFactory.generateId());
        sku.setGoodsId(goods.getId());
        sku.setImg("http://img11.360buyimg.com/n5/g14/M02/12/0D/rBEhV1JEE3cIAAAAAAH9zCwo7mwAADmSADVmVAAAf3k413.jpg");
        sku.setCurCount(1000);
        sku.setFreezeCount(50);
        sku.setSellCount(300);
        sku.setSkuAttrValueIds(Arrays.asList(new String[]{"", ""}));
        goods.getSkus().add(sku);

        return goods;
    }

    private static Goods goods3() {
        Goods goods = new Goods();
        goods.setId(IdFactory.generateId());
        goods.setCode("32538");
        goods.setName("晶语晶缘 天然巴西碧玺项链 糖果色 多蓝绿红 女款天然水晶项");
        goods.setImg("http://img10.360buyimg.com/n5/g14/M02/0D/1A/rBEhVlIiuHcIAAAAAAGZQsLoXNcAACs9AJEmwYAAZla968.jpg");
        goods.setOnShelfTime(System.currentTimeMillis());
        goods.setOffShelfTime(System.currentTimeMillis());
        goods.setMaterialId("5260abfc70761ffcbae5cb77f05f3c50");
        goods.setStyleId("5260ac9470761ffcc1b22649d21c52f6");
        goods.setVenderId("5267850370761ffc1e61503f19948ebd");
        goods.setUseIds(Arrays.asList(new String[]{"5260b0bb70761ffcfbfc528ebf343dad", "5260b0bb70761ffcfbfc528ebf343daf"}));
        goods.setAttrValueIds(Arrays.asList(new String[]{"5266224570761ffca143a492c7c635e2", "5266224570761ffca143a492c7c635e7", "5266224570761ffca143a492c7c635ea"}));
        goods.setSkus(new ArrayList<GoodsSku>());

        GoodsSku sku = new GoodsSku();
        sku.setId(IdFactory.generateId());
        sku.setGoodsId(goods.getId());
        sku.setImg("http://img10.360buyimg.com/n5/g14/M02/0D/1A/rBEhVlIiuHcIAAAAAAGZQsLoXNcAACs9AJEmwYAAZla968.jpg");
        sku.setCurCount(1000);
        sku.setFreezeCount(50);
        sku.setSellCount(300);
        sku.setSkuAttrValueIds(Arrays.asList(new String[]{"", ""}));
        goods.getSkus().add(sku);

        return goods;
    }
}

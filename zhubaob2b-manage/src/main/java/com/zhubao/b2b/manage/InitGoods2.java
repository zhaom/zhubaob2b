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
 * Time: 下午4:58
 */
public class InitGoods2 {
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
        goods.setCode("5000");
        goods.setName("芳菲 开光天然冰种黑曜石貔貅戒指 男士戒指 指环 扳指 大气男款 辟邪招财");
        goods.setImg("http://img10.360buyimg.com/n5/g15/M01/02/1C/rBEhWlHLmCwIAAAAAALhqTg2sQEAAAkSABJ1ZAAAuHB910.jpg");
        goods.setOnShelfTime(System.currentTimeMillis());
        goods.setOffShelfTime(System.currentTimeMillis());
        goods.setMaterialId("5260abfc70761ffcbae5cb77f05f3c50");
        goods.setStyleId("5260ac9570761ffcc1b22649d21c52f9");
        goods.setVenderId("5267863f70761ffc7c6dcb5092c52787");
        goods.setUseIds(Arrays.asList(new String[]{"5260b0bb70761ffcfbfc528ebf343dad", "5260b0bb70761ffcfbfc528ebf343daf"}));
        goods.setAttrValueIds(Arrays.asList(new String[]{"5266240170761ffc2704e6efe732f4b4", "5266240170761ffc2704e6efe732f4b5", "5266240170761ffc2704e6efe732f4ba"}));
        goods.setSkus(new ArrayList<GoodsSku>());

        GoodsSku sku = new GoodsSku();
        sku.setId(IdFactory.generateId());
        sku.setGoodsId(goods.getId());
        sku.setImg("http://img11.360buyimg.com/n5/g14/M02/12/0D/rBEhVVJEE3QIAAAAAAKGerfuLwEAADmSACWGfQAAoaS141.jpg");
        sku.setCurCount(3000);
        sku.setFreezeCount(70);
        sku.setSellCount(800);
        sku.setSkuAttrValueIds(Arrays.asList(new String[]{"5266276e70761ffcf365cd178c5da02a", "5266276e70761ffcf365cd178c5da02d"}));
        goods.getSkus().add(sku);

        return goods;
    }

    private static Goods goods2() {
        Goods goods = new Goods();
        goods.setId(IdFactory.generateId());
        goods.setCode("5000");
        goods.setName("易燃火山 925银天然黄水晶戒指 女 韩版 时尚戒指饰品");
        goods.setImg("http://img13.360buyimg.com/n5/g10/M00/18/07/rBEQWFF167kIAAAAAAcd-sYKn-8AAEu5AJqV5oABx4S206.jpg");
        goods.setOnShelfTime(System.currentTimeMillis());
        goods.setOffShelfTime(System.currentTimeMillis());
        goods.setMaterialId("5260abfc70761ffcbae5cb77f05f3c50");
        goods.setStyleId("5260ac9570761ffcc1b22649d21c52f9");
        goods.setVenderId("5267863f70761ffc7c6dcb5092c52787");
        goods.setUseIds(Arrays.asList(new String[]{"5260b0bb70761ffcfbfc528ebf343dad", "5260b0bb70761ffcfbfc528ebf343daf"}));
        goods.setAttrValueIds(Arrays.asList(new String[]{"5266240170761ffc2704e6efe732f4b4", "5266240170761ffc2704e6efe732f4b5", "5266240170761ffc2704e6efe732f4ba"}));
        goods.setSkus(new ArrayList<GoodsSku>());

        GoodsSku sku = new GoodsSku();
        sku.setId(IdFactory.generateId());
        sku.setGoodsId(goods.getId());
        sku.setImg("http://img13.360buyimg.com/n5/g10/M00/18/07/rBEQWFF167kIAAAAAAcd-sYKn-8AAEu5AJqV5oABx4S206.jpg");
        sku.setCurCount(3500);
        sku.setFreezeCount(90);
        sku.setSellCount(890);
        sku.setSkuAttrValueIds(Arrays.asList(new String[]{"5266276e70761ffcf365cd178c5da029", "5266276e70761ffcf365cd178c5da02d"}));
        goods.getSkus().add(sku);

        return goods;
    }

    private static Goods goods3() {
        Goods goods = new Goods();
        goods.setId(IdFactory.generateId());
        goods.setCode("5001");
        goods.setName("缘生记 水晶红玛瑙戒指/扳指/指环 男女通用款 增强信心和能量 坚贞爱情");
        goods.setImg("http://img11.360buyimg.com/n5/g6/M04/04/0E/rBEGC1DBlyoIAAAAAAC6FmihltQAAA3yQCgzvIAALou100.jpg");
        goods.setOnShelfTime(System.currentTimeMillis());
        goods.setOffShelfTime(System.currentTimeMillis());
        goods.setMaterialId("5260abfc70761ffcbae5cb77f05f3c50");
        goods.setStyleId("5260ac9570761ffcc1b22649d21c52f9");
        goods.setVenderId("5267863f70761ffc7c6dcb5092c52787");
        goods.setUseIds(Arrays.asList(new String[]{"5260b0bb70761ffcfbfc528ebf343dad", "5260b0bb70761ffcfbfc528ebf343daf"}));
        goods.setAttrValueIds(Arrays.asList(new String[]{"5266240170761ffc2704e6efe732f4b4", "5266240170761ffc2704e6efe732f4b5", "5266240170761ffc2704e6efe732f4ba"}));
        goods.setSkus(new ArrayList<GoodsSku>());

        GoodsSku sku = new GoodsSku();
        sku.setId(IdFactory.generateId());
        sku.setGoodsId(goods.getId());
        sku.setImg("http://img11.360buyimg.com/n5/g6/M04/04/0E/rBEGC1DBlyoIAAAAAAC6FmihltQAAA3yQCgzvIAALou100.jpg");
        sku.setCurCount(3006);
        sku.setFreezeCount(71);
        sku.setSellCount(870);
        sku.setSkuAttrValueIds(Arrays.asList(new String[]{"5266276e70761ffcf365cd178c5da02a", "5266276e70761ffcf365cd178c5da02e"}));
        goods.getSkus().add(sku);

        return goods;
    }
}

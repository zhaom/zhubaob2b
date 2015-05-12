package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.platform.model.GoodsSkuAttribute;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午4:08
 */
public interface GoodsSkuAttributeService {

    public void createSkuAttribute(GoodsSkuAttribute attribute);

    public void updateSkuAttribute(GoodsSkuAttribute attribute);

    public void deleteSkuAttribute(String attributeId);

    public GoodsSkuAttribute getSkuAttribute(String attributeId);

    public List<GoodsSkuAttribute> getSkuAttributes(String styleId);

}

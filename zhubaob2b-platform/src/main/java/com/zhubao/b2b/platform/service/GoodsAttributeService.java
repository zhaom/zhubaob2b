package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.platform.model.GoodsAttribute;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-23
 * Time: 下午2:35
 */

public interface GoodsAttributeService {

    public void createAttribute(GoodsAttribute attribute);

    public void updateAttribute(GoodsAttribute attribute);

    public void deleteAttribute(String attributeId);

    public GoodsAttribute getAttribute(String attributeId);

    public List<GoodsAttribute> getAttributes(String materialId, String styleId);

}

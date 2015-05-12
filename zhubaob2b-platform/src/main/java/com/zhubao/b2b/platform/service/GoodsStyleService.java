package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.platform.model.GoodsStyle;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午4:25
 */
public interface GoodsStyleService {

    public void createStyle(GoodsStyle style);

    public void updateStyle(GoodsStyle style);

    public void deleteStyle(String styleId);

    public GoodsStyle getStyle(String styleId);

    public List<GoodsStyle> getStyles();
}

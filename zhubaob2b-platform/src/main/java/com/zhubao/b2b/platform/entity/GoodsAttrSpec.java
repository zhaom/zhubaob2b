package com.zhubao.b2b.platform.entity;

import com.zhubao.b2b.platform.model.GoodsAttribute;
import com.zhubao.b2b.platform.model.GoodsAttributeValue;

import java.io.Serializable;

/**
 * User: xiaoping lu
 * Date: 13-10-8
 * Time: 上午11:31
 */
public class GoodsAttrSpec implements Serializable {

    private String attributeId;
    private String attributeName;
    private String valueId;
    private String value;

    public GoodsAttrSpec(GoodsAttribute attr, GoodsAttributeValue attrValue) {
        if (attr != null) {
            this.attributeId = attr.getId();
            this.attributeName = attr.getName();
        }
        if (attrValue != null) {
            this.valueId = attrValue.getId();
            this.value = attrValue.getValue();
        }
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

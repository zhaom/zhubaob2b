package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import java.io.Serializable;

/**
 * User: xiaoping lu
 * Date: 13-10-14
 * Time: 下午5:31
 */
@Entity(value = "goods_sku_attribute_value", noClassnameStored = true)
public class GoodsSkuAttributeValue implements Serializable {

	private static final long serialVersionUID = 8221824652717032397L;
	
	@Id
    private String id;
    @Indexed
    private String attributeId;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

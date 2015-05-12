package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-10
 * Time: 下午5:33
 */
@Entity(value = "goods_sku_attribute", noClassnameStored = true)
public class GoodsSkuAttribute implements Serializable {

	private static final long serialVersionUID = -7719938809174115446L;
	
	@Id
    private String id;
    @Indexed
    private String styleId;
    private String name;
    @Transient
    private List<GoodsSkuAttributeValue> values;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GoodsSkuAttributeValue> getValues() {
        return values;
    }

    public void setValues(List<GoodsSkuAttributeValue> values) {
        this.values = values;
    }
}

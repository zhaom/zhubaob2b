package com.zhubao.b2b.platform.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-9-25
 * Time: 上午10:21
 */
@Entity(value = "goods_attribute", noClassnameStored = true)
public class GoodsAttribute implements Serializable {

	private static final long serialVersionUID = -1201038738986716406L;
	
	@Id
    private String id;
    @Indexed
    private String materialId;
    @Indexed
    private String styleId;
    private String name;
    @Transient
    private List<GoodsAttributeValue> values;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
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

    public List<GoodsAttributeValue> getValues() {
        return values;
    }

    public void setValues(List<GoodsAttributeValue> values) {
        this.values = values;
    }
}

package com.zhubao.b2b.platform.morphia;

import com.google.code.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 下午3:50
 */
public class MorphiaFactoryBean extends AbstractFactoryBean<Morphia> {

    private String[] mapPackages;

    @Override
    protected Morphia createInstance() {
        Morphia morphia = new Morphia();
        if (mapPackages != null) {
            for (String mapPackage : mapPackages) {
                morphia.mapPackage(mapPackage);
            }
        }
        return morphia;
    }

    @Override
    public Class<?> getObjectType() {
        return Morphia.class;
    }

    public String[] getMapPackages() {
        return mapPackages;
    }

    public void setMapPackages(String[] mapPackages) {
        this.mapPackages = mapPackages;
    }
}

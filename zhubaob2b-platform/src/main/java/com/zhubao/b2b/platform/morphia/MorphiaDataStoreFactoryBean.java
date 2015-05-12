package com.zhubao.b2b.platform.morphia;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 下午3:55
 */
public class MorphiaDataStoreFactoryBean extends AbstractFactoryBean<Datastore> {

    private Morphia morphia;
    private Mongo mongo;
    private String dbName;

    @Override
    protected Datastore createInstance() throws Exception {
        Datastore store = morphia.createDatastore(mongo, dbName);
        store.ensureIndexes();
        return store;
    }

    @Override
    public Class<?> getObjectType() {
        return Datastore.class;
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}

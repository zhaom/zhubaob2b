package com.zhubao.b2b.platform.dao.impl;

import com.google.code.morphia.mapping.Mapper;
import com.zhubao.b2b.platform.dao.VenderDAO;
import com.zhubao.b2b.platform.model.Vender;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-8-28
 * Time: 上午9:18
 */
public class VenderDAOImpl extends BasicDAOSupport implements VenderDAO {

    @Override
    public void insert(Vender user) {
        getDatastore().save(user);
    }

    @Override
    public Vender select(String venderId) {
        return getDatastore().find(Vender.class).field(Mapper.ID_KEY).equal(venderId).get();
    }

    @Override
    public Vender selectByKShopAgencyId(String kshopAgencyId) {
        return getDatastore().find(Vender.class).field("kshopAgencyId").equal(kshopAgencyId).offset(0).limit(1).get();
    }

    @Override
    public List<Vender> selectList() {
        return getDatastore().find(Vender.class).asList();
    }

    @Override
    public Vender selectByKShopUserId(String kshopUserId) {
        return getDatastore().find(Vender.class).field("kshopUserId").equal(kshopUserId).get();
    }

    @Override
    public Vender selectByKShopUserName(String kshopUserName) {
        return getDatastore().find(Vender.class).field("kshopUserName").equal(kshopUserName).get();
    }
}

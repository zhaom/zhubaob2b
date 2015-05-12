package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.Vender;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-8-28
 * Time: 上午9:15
 */
public interface VenderDAO {

    public void insert(Vender vender);

    public Vender selectByKShopAgencyId(String kshopAgencyId);

    public Vender select(String venderId);

    public List<Vender> selectList();

    public Vender selectByKShopUserId(String kshopUserId);

    public Vender selectByKShopUserName(String kshopUserName);
}

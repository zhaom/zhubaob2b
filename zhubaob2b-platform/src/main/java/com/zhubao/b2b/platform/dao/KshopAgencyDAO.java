package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.KshopAgency;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-2-22
 * Time: 上午9:25
 * To change this template use File | Settings | File Templates.
 */
public interface KshopAgencyDAO {

    public KshopAgency select(String id);

    public void insert(KshopAgency kshopAgency);
}

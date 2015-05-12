package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.Payway;

import java.util.List;

public interface PaywayDAO {

    public void insert(Payway payway);

    public List<Payway> selectList(String status);

    public Payway select(String paywayId);
}

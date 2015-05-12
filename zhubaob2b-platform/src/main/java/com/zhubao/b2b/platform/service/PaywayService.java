package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.platform.model.Payway;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-21
 * Time: 下午1:59
 */
public interface PaywayService {

    public void createPayway(Payway payway);

    public List<Payway> getPayways(String status);

}

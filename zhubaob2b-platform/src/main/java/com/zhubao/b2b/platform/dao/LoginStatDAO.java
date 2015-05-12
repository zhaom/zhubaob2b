package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.LoginStat;

/**
 * User: xiaoping lu
 * Date: 13-8-19
 * Time: 上午11:37
 */
public interface LoginStatDAO {

    public void insert(LoginStat stat);

    public void delete(String userId);

    public LoginStat select(String userId);
}

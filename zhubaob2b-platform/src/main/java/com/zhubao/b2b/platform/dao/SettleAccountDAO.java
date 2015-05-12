package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.entry.QueryParameter;
import com.zhubao.b2b.platform.model.SettleAccount;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-2-27
 * Time: 上午11:55
 * To change this template use File | Settings | File Templates.
 */
public interface SettleAccountDAO {

    public void insert(SettleAccount settleAccount);

    public long count(QueryParameter<SettleAccount> queryParameter);

    public List<SettleAccount> select(QueryParameter<SettleAccount> queryParameter, int start, int range);

    public SettleAccount getById(String id);

    public SettleAccount getLastSettleAccount(String venderId);

    public SettleAccount getByVenderTime(String venderId, long beginTime, long endTime);

    public void remove(SettleAccount settleAccount1);
}

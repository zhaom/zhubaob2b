package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.CustPayway;
import com.zhubao.b2b.platform.model.Payway;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface CustPaywayDAO {

    public CustPayway getById(String id);

    public List<Payway> getPaywayByCustId(String custId);

    public void setCustPayway(String custId, Payway payway);

    public void unsetCustPayway(String userid, Payway payway);
}

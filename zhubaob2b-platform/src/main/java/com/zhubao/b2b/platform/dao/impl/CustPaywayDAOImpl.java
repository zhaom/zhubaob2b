package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.dao.CustPaywayDAO;
import com.zhubao.b2b.platform.model.CustPayway;
import com.zhubao.b2b.platform.model.Payway;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class CustPaywayDAOImpl extends BasicDAOSupport implements CustPaywayDAO {


    @Override
    public CustPayway getById(String id) {
        return getDatastore().get(CustPayway.class, id);
    }

    @Override
    public List<Payway> getPaywayByCustId(String custId) {
        List<CustPayway> list = getDatastore().find(CustPayway.class).field("customerid").equal(custId).field("status").equal(Constants.STATUS_SHOW).asList();
        List<Payway> result = new ArrayList<Payway>();
        for (CustPayway entry:list){
            result.add(entry.getPayway());
        }
        return result;
    }

    @Override
    public void setCustPayway(String custId, Payway payway) {
        List<CustPayway> list = getDatastore().find(CustPayway.class).field("customerid").equal(custId).asList();
        CustPayway target = null;
        for(CustPayway entry: list){
            if(entry.getPayway().getId().equalsIgnoreCase(payway.getId())){
                target = entry;
            }
        }
        if(target == null){
            target = new CustPayway();
            target.setId(IdFactory.generateId());
            target.setCustomerid(custId);
            target.setLastupdate(new Date().getTime());
            target.setStatus(Constants.STATUS_SHOW+"");
            target.setPayway(payway);
        } else {
            target.setStatus(Constants.STATUS_SHOW+"");
            target.setLastupdate(new Date().getTime());
        }
        getDatastore().save(target);
    }

    @Override
    public void unsetCustPayway(String userid, Payway payway) {
        List<CustPayway> list = getDatastore().find(CustPayway.class).field("customerid").equal(userid).asList();
        CustPayway target = null;
        for(CustPayway entry: list){
            if(entry.getPayway().getId().equalsIgnoreCase(payway.getId())){
                target = entry;
            }
        }
        if(target == null){
            target = new CustPayway();
            target.setId(IdFactory.generateId());
            target.setCustomerid(userid);
            target.setLastupdate(new Date().getTime());
            target.setStatus(Constants.STATUS_DELETE+"");
            target.setPayway(payway);
        } else {
            target.setStatus(Constants.STATUS_DELETE+"");
            target.setLastupdate(new Date().getTime());
        }
        getDatastore().save(target);
    }
}

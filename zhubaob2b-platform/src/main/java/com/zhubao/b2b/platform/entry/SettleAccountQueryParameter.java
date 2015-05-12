package com.zhubao.b2b.platform.entry;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.model.SettleAccount;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-2-27
 * Time: 下午1:57
 * To change this template use File | Settings | File Templates.
 */
public class SettleAccountQueryParameter implements QueryParameter<SettleAccount>, Serializable {

    private String venderId;

    private int status;

    @Override
    public Query<SettleAccount> buildQuery(Datastore datastore) {
        Query<SettleAccount> query = datastore.createQuery(SettleAccount.class);
        if(StringUtils.hasText(venderId)){
            query.field("venderId").equal(venderId);
        }
        if(status != -1){
            query.field("status").notEqual(-1);
        }else {
            query.field("status").equal(-1);
        }
        return query;
    }

    @Override
    public String getOrderBy() {
        return "-createdTime";
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

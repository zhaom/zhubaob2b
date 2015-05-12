package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.InvoiceDAO;
import com.zhubao.b2b.platform.model.Invoice;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class InvoiceDAOImpl extends BasicDAOSupport implements InvoiceDAO {
    @Override
    public Invoice insert(Invoice invoice) {
        getDatastore().save(invoice);
        return invoice;
    }

    @Override
    public Invoice getById(String id) {
        return getDatastore().get(Invoice.class, id);
    }

    @Override
    public Invoice getByOrderId(String orderId) {
        return getDatastore().find(Invoice.class).field("orderId").equal(orderId).get();
    }
}

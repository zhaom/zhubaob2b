package com.zhubao.b2b.platform.dao.impl;

import com.zhubao.b2b.platform.dao.InvoiceItemDAO;
import com.zhubao.b2b.platform.model.InvoiceItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class InvoiceItemDAOImpl extends BasicDAOSupport implements InvoiceItemDAO {
    @Override
    public InvoiceItem insert(InvoiceItem invoiceItem) {
        getDatastore().save(invoiceItem);
        return invoiceItem;
    }

    @Override
    public List<InvoiceItem> getByInvoiceId(String invoiceId) {
        return getDatastore().find(InvoiceItem.class).field("invoiceId").equal(invoiceId).asList();
    }
}

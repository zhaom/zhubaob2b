package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.InvoiceItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface InvoiceItemDAO {

    public InvoiceItem insert(InvoiceItem invoiceItem);

    public List<InvoiceItem> getByInvoiceId(String invoiceId);
}

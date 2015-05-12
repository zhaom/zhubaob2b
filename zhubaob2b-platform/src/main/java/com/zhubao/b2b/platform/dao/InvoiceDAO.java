package com.zhubao.b2b.platform.dao;

import com.zhubao.b2b.platform.model.Invoice;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface InvoiceDAO {

    public Invoice insert(Invoice invoice);

    public Invoice getById(String id);

    public Invoice getByOrderId(String orderId);

}

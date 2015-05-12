package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.platform.entity.BuybackApplyItem;
import com.zhubao.b2b.platform.entry.BuybackQueryParameter;
import com.zhubao.b2b.platform.model.BuybackApply;
import com.zhubao.common.utils.Pagination;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
public interface BuybackService {

    public ServiceResult<BuybackApply> createApply(String userId, String venderId, String applyTime, List<BuybackApplyItem> buybackApplyItemList);

    public ServiceResult<Pagination<BuybackApply>> getPaginationApply(BuybackQueryParameter queryParameter, int pindex, int pcount);

    public ServiceResult<BuybackApply> getApplyById(String userId, String applyId);

    public ServiceResult<BuybackApply> deleteApply(String userId, String applyId);
}

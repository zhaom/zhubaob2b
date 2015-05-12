package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.platform.entry.SettleAccountCreateParams;
import com.zhubao.b2b.platform.model.SettleAccount;
import com.zhubao.common.utils.Pagination;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-2-27
 * Time: 下午12:10
 * To change this template use File | Settings | File Templates.
 */
public interface SettleAccountService {

    public ServiceResult<Pagination<SettleAccount>> getPaginationSettleAccounts(String userId, int pageIndex, int pageSize);

    public ServiceResult<SettleAccount> getDetail(String userId, String id);

    public ServiceResult<SettleAccount> createSettleAccount(String userId, String venderId, long beginTime, long endTime);

    public ServiceResult<SettleAccount> confirmSettleAccount(String userId, String id);

    public ServiceResult<SettleAccount> checkSettleAccount(String userId, String id);

    public ServiceResult<SettleAccount> deleteSettleAccount(String userId, String id);

    public ServiceResult<List<SettleAccountCreateParams>> getTaskCreateParams(long currenttime, long cycle);
}

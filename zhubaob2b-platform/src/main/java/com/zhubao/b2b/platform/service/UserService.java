package com.zhubao.b2b.platform.service;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.model.LoginStat;
import com.zhubao.b2b.platform.model.Payway;
import com.zhubao.b2b.platform.model.Vender;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-8-19
 * Time: 下午12:07
 */
public interface UserService {

    public void createLoginStat(LoginStat stat);

    public void deleteLoginStats(String userId);

    public LoginStat getLastLoginStat(String userId);

    public Customer getCustomer(String customerId);

    public Vender getVender(String venderId);

    public List<Vender> getAllVenders();

    public Customer getIsLegalCustomer(String loginName, String password);

    public Vender getIsLegalVender(String loginName, String password);

    public ServiceResult<List<Payway>> getPayway(String userid);

    public ServiceResult<String> setPayway(String userid, String paywayIds);

    public ServiceResult<String> unsetPayway(String userid, String paywayIds);

    public ServiceResult<List<Customer>> getMembers(String userid);

    public boolean isSuperUser(String userId);
}

package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.exception.ServiceException;
import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.dao.*;
import com.zhubao.b2b.platform.entity.KShopUser;
import com.zhubao.b2b.platform.model.*;
import com.zhubao.b2b.platform.service.UserService;
import com.zhubao.b2b.platform.utils.KShopHelper;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-8-19
 * Time: 下午12:10
 */
public class UserServiceImpl extends BasicServiceSupport implements UserService {

    private CustomerDAO customerDAO;
    private VenderDAO venderDAO;
    private LoginStatDAO loginStatDAO;
    private ShipAddressDAO shipAddressDAO;
    private CustPaywayDAO custPaywayDAO;
    private PaywayDAO paywayDAO;
    private KshopAgencyDAO kshopAgencyDAO;
    private KShopHelper kshopHelper;

    @Override
    public void createLoginStat(LoginStat stat) {
        try {
            loginStatDAO.insert(stat);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteLoginStats(String userId) {
        try {
            loginStatDAO.delete(userId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public LoginStat getLastLoginStat(String userId) {
        try {
            return loginStatDAO.select(userId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public Customer getCustomer(String customerId) {
        try {
            Customer customer = customerDAO.select(customerId);
            KshopAgency kshopAgency = kshopAgencyDAO.select(customer.getKshopAgencyId());
            customer.setAgency(kshopAgency);
            if(customer.getVenderKshopAgencyId() != null){
                KshopAgency venderKshopAgency = kshopAgencyDAO.select(customer.getVenderKshopAgencyId());
                customer.setVenderAgency(venderKshopAgency);
            }
            return customer;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public Vender getVender(String venderId) {
        try {
            Vender vender = venderDAO.selectByKShopAgencyId(venderId);
            KshopAgency kshopAgency = kshopAgencyDAO.select(venderId);
            vender.setAgency(kshopAgency);
            return vender;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public List<Vender> getAllVenders() {
        try {
            List<Vender> venderList = venderDAO.selectList();
            for(Vender vender: venderList){
                vender.setAgency(kshopAgencyDAO.select(vender.getKshopAgencyId()));
            }
            return venderList;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public Customer getIsLegalCustomer(String loginName, String password) {
        try {
            Customer customer = null;

            KShopUser kShopUser = kshopHelper.doLogin(loginName, password);
            if (kShopUser != null) {
                String token = kShopUser.getToken();

                // 拿取用户详细资料, 若非采购商用户,  则登陆失败
                kShopUser = kshopHelper.doGetUserInfo(kShopUser.getUserId(), token);
                if (kShopUser != null && !"0".equals(kShopUser.getAgencyParentId())) {
                    // 查询用户上次登陆保存的资料
                    customer = customerDAO.selectByKShopUserId(kShopUser.getUserId());
                    if (customer == null) {
                        customer = new Customer();
                        customer.setId(IdFactory.generateId());
                    }

                    // 更新用户本次登陆的最新资料
                    customer.setKshopToken(token);
                    customer.setKshopUserEmail(kShopUser.getEmail());
                    customer.setKshopUserId(kShopUser.getUserId());
                    customer.setKshopUserTel(kShopUser.getTel());
                    customer.setKshopUserName(kShopUser.getUserName());

                    customer.setKshopAgencyId(kShopUser.getAgencyId());
                    customer.setKshopAgencyName(kShopUser.getAgencyName());
                    customer.setKshopUserRegDate(kShopUser.getUserRegDate());
                    customer.setKshopUserNickName(kShopUser.getUserNickName());

                    customer.setVenderKshopAgencyId(kShopUser.getAgencyParentId());

                    KshopAgency kshopAgency = new KshopAgency();
                    kshopAgency.setKshopAgencyId(kShopUser.getAgencyId());
                    kshopAgency.setKshopAgencyName(kShopUser.getAgencyName());
                    kshopAgency.setKshopAgencyGradeName(kShopUser.getAgencyGradeName());
                    kshopAgency.setKshopAgencyParentId(kShopUser.getAgencyParentId());
                    kshopAgency.setKshopAgencyStatus(kShopUser.getAgencyStatus());

                    customerDAO.insert(customer);
                    kshopAgencyDAO.insert(kshopAgency);
                    customer = customerDAO.selectByKShopUserId(kShopUser.getUserId());
                }
            }
            return customer;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public Vender getIsLegalVender(String loginName, String password) {
        try {
            Vender vender = null;

            KShopUser kShopUser = kshopHelper.doLogin(loginName, password);
            if (kShopUser != null) {
                String token = kShopUser.getToken();

                // 拿取用户详细资料, 若非供货商用户,  则登陆失败
                kShopUser = kshopHelper.doGetUserInfo(kShopUser.getUserId(), token);
                if (kShopUser != null && "0".equals(kShopUser.getAgencyParentId())) {
                    // 查询用户上次登陆保存的资料
                    vender = venderDAO.selectByKShopUserId(kShopUser.getUserId());
                    if (vender == null) {
                        vender = new Vender();
                        vender.setId(IdFactory.generateId());
                    }

                    // 更新用户本次登陆的最新资料
                    vender.setKshopToken(token);
                    vender.setKshopUserEmail(kShopUser.getEmail());
                    vender.setKshopUserId(kShopUser.getUserId());
                    vender.setKshopUserTel(kShopUser.getTel());
                    vender.setKshopUserName(kShopUser.getUserName());
                    vender.setKshopAgencyId(kShopUser.getAgencyId());
                    vender.setKshopAgencyName(kShopUser.getAgencyName());
                    vender.setKshopUserRegDate(kShopUser.getUserRegDate());
                    vender.setKshopUserNickName(kShopUser.getUserNickName());

                    KshopAgency kshopAgency = new KshopAgency();
                    kshopAgency.setKshopAgencyId(kShopUser.getAgencyId());
                    kshopAgency.setKshopAgencyName(kShopUser.getAgencyName());
                    kshopAgency.setKshopAgencyGradeName(kShopUser.getAgencyGradeName());
                    kshopAgency.setKshopAgencyParentId(kShopUser.getAgencyParentId());
                    kshopAgency.setKshopAgencyStatus(kShopUser.getAgencyStatus());

                    venderDAO.insert(vender);
                    kshopAgencyDAO.insert(kshopAgency);
                    vender = venderDAO.selectByKShopUserId(kShopUser.getUserId());
                }
            }
            return vender;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public ServiceResult<List<Payway>> getPayway(String userid) {
        ServiceResult<List<Payway>> serviceResult = new ServiceResult<List<Payway>>();
        try {
            List<Payway> list = custPaywayDAO.getPaywayByCustId(userid);
            serviceResult.setSuccess(true);
            serviceResult.setValue(list);
        } catch (Exception e) {
            logger.error("fatal error occur", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<String> setPayway(String userid, String paywayIds) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            String[] ids = StringUtils.tokenizeToStringArray(paywayIds, ",");
            for (String id : ids) {
                Payway payway = paywayDAO.select(id);
                if (payway != null) {
                    custPaywayDAO.setCustPayway(userid, payway);
                }
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(Constants.RESULT_OK);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<String> unsetPayway(String userid, String paywayIds) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            String[] ids = StringUtils.tokenizeToStringArray(paywayIds, ",");
            for (String id : ids) {
                Payway payway = paywayDAO.select(id);
                if (payway != null) {
                    custPaywayDAO.unsetCustPayway(userid, payway);
                }
            }
            serviceResult.setSuccess(true);
            serviceResult.setValue(Constants.RESULT_OK);
        } catch (Exception e) {
            logger.error("some fatal error occur", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Customer>> getMembers(String userid) {
        ServiceResult<List<Customer>> serviceResult = new ServiceResult<List<Customer>>();
        try{
            Vender vender = venderDAO.select(userid);
            List<Customer> customerList = customerDAO.selectByVenderKshopAgencyId(vender.getKshopAgencyId());
            serviceResult.setSuccess(true);
            serviceResult.setValue(customerList);
        }catch (Exception e){
            logger.error("some fatal error occur", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public boolean isSuperUser(String userId) {
        return Constants.SUPER_USER_ID.equalsIgnoreCase(userId);
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void setVenderDAO(VenderDAO venderDAO) {
        this.venderDAO = venderDAO;
    }

    public void setLoginStatDAO(LoginStatDAO loginStatDAO) {
        this.loginStatDAO = loginStatDAO;
    }

    public void setShipAddressDAO(ShipAddressDAO shipAddressDAO) {
        this.shipAddressDAO = shipAddressDAO;
    }

    public void setCustPaywayDAO(CustPaywayDAO custPaywayDAO) {
        this.custPaywayDAO = custPaywayDAO;
    }

    public void setPaywayDAO(PaywayDAO paywayDAO) {
        this.paywayDAO = paywayDAO;
    }

    public void setKshopHelper(KShopHelper kshopHelper) {
        this.kshopHelper = kshopHelper;
    }

    public void setKshopAgencyDAO(KshopAgencyDAO kshopAgencyDAO) {
        this.kshopAgencyDAO = kshopAgencyDAO;
    }
}

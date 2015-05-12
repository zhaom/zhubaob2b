package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.dao.BuybackApplyDAO;
import com.zhubao.b2b.platform.dao.CustomerDAO;
import com.zhubao.b2b.platform.dao.VenderDAO;
import com.zhubao.b2b.platform.entity.BuybackApplyItem;
import com.zhubao.b2b.platform.entry.BuybackQueryParameter;
import com.zhubao.b2b.platform.model.BuybackApply;
import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.model.Vender;
import com.zhubao.b2b.platform.service.BuybackService;
import com.zhubao.b2b.platform.service.UserService;
import com.zhubao.common.utils.Pagination;

import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
public class BuybackServiceImpl extends BasicServiceSupport implements BuybackService {

    private static final Logger logger = LoggerFactory.getLogger(BuybackServiceImpl.class);

    private BuybackApplyDAO buybackApplyDAO;

    private CustomerDAO customerDAO;

    private VenderDAO venderDAO;

    private UserService userService;

    public void setBuybackApplyDAO(BuybackApplyDAO buybackApplyDAO) {
        this.buybackApplyDAO = buybackApplyDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void setVenderDAO(VenderDAO venderDAO) {
        this.venderDAO = venderDAO;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ServiceResult<BuybackApply> createApply(String userId, String venderId, String applyTime, List<BuybackApplyItem> buybackApplyItemList) {
        ServiceResult<BuybackApply> result = new ServiceResult<BuybackApply>();
        try{
            Customer customer  = customerDAO.select(userId);
            if(customer == null){
                result.setSuccess(false);
                result.setErrorCode(HttpStatus.SC_FORBIDDEN);
                result.setErrorMessage("no valid user available.");
                return result;
            }
            Vender vender = venderDAO.selectByKShopAgencyId(venderId);
            if(vender == null){
                result.setSuccess(false);
                result.setErrorCode(HttpStatus.SC_BAD_REQUEST);
                result.setErrorMessage("invalid vender");
                return result;
            }

            BuybackApply buybackApply = new BuybackApply();
            buybackApply.setId(IdFactory.generateId());
            buybackApply.setCustomerId(userId);
            buybackApply.setVenderId(venderId);
            buybackApply.setApplyTime(applyTime);
            buybackApply.setCreatedTime(new Date().getTime());
            buybackApply.setStatus(Constants.STATUS_SHOW+"");

            for(BuybackApplyItem entry  : buybackApplyItemList){
                entry.setApplyId(buybackApply.getId());
            }

            buybackApply.setItems(buybackApplyItemList);
            buybackApplyDAO.create(buybackApply);
            result.setSuccess(true);
            result.setValue(buybackApply);
        }catch (Exception e){
            logger.error("create new apply error.", e);
            result.setSuccess(false);
            result.setErrorCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<Pagination<BuybackApply>> getPaginationApply(BuybackQueryParameter queryParameter, int pindex, int pcount) {
        ServiceResult<Pagination<BuybackApply>> serviceResult = new ServiceResult<Pagination<BuybackApply>>();
        try{
            Pagination<BuybackApply> pagination = new Pagination<BuybackApply>(pindex, pcount);
            int totalCount = buybackApplyDAO.count(queryParameter).intValue();
            pagination.setTotalRecord(totalCount);

            int start = 0;
            int range = 10;
            start = (pagination.getPage() - 1) * pagination.getRange();
            range = pagination.getRange();
            List<BuybackApply> buybackApplyList = buybackApplyDAO.selectPageList(queryParameter, start, range);
            for(BuybackApply entry: buybackApplyList){
                Customer customer = customerDAO.select(entry.getCustomerId());
                entry.setCustomer(customer);
                Vender vender = venderDAO.selectByKShopAgencyId(entry.getVenderId());
                entry.setVender(vender);
            }
            pagination.setData(buybackApplyList);
            serviceResult.setSuccess(true);
            serviceResult.setValue(pagination);
        }catch (Exception e){
            logger.error("fatal error", e);
            serviceResult.setErrorCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            serviceResult.setSuccess(false);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<BuybackApply> getApplyById(String userId, String applyId) {
        ServiceResult<BuybackApply> serviceResult = new ServiceResult<BuybackApply>();
        try{
            BuybackApply buybackApply = buybackApplyDAO.select(applyId);
            Customer customer = customerDAO.select(buybackApply.getCustomerId());
            Vender vender = venderDAO.selectByKShopAgencyId(buybackApply.getVenderId());
            if(!userId.equalsIgnoreCase(buybackApply.getCustomerId())){
                Vender uVender = venderDAO.select(userId);
                if(!vender.getKshopAgencyId().equalsIgnoreCase(uVender.getKshopAgencyId())){
                    if(!userService.isSuperUser(userId)){
                        logger.error("get the detail of apply,denied.only the creator,the vender or the superuser are valid");
                        serviceResult.setSuccess(false);
                        serviceResult.setErrorCode(HttpStatus.SC_FORBIDDEN);
                        serviceResult.setErrorMessage("the apply data is forbidden");
                        return serviceResult;
                    }
                }
            }
            buybackApply.setVender(vender);
            buybackApply.setCustomer(customer);
            serviceResult.setSuccess(true);
            serviceResult.setValue(buybackApply);
        } catch (Exception e){
            logger.error("fatal error", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<BuybackApply> deleteApply(String userId, String applyId) {
        ServiceResult<BuybackApply> serviceResult = new ServiceResult<BuybackApply>();
        try{
            BuybackApply buybackApply = buybackApplyDAO.select(applyId);
            if(userId.equalsIgnoreCase(buybackApply.getCustomerId())){
                buybackApply = buybackApplyDAO.deleteApply(applyId);
                serviceResult.setSuccess(true);
                serviceResult.setValue(buybackApply);
            }else {
                logger.error("not the creator do delete,denied");
                serviceResult.setSuccess(false);
                serviceResult.setErrorCode(HttpStatus.SC_FORBIDDEN);
                serviceResult.setErrorMessage("delete forbidden,only creator is valid");
            }
        }catch (Exception e){
            logger.error("fatal error", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }
}

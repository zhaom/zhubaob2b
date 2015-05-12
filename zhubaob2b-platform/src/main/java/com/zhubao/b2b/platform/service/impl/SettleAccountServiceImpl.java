package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.common.utils.Constants;
import com.zhubao.b2b.platform.dao.SettleAccountDAO;
import com.zhubao.b2b.platform.dao.SettleAccountItemDAO;
import com.zhubao.b2b.platform.dao.VenderDAO;
import com.zhubao.b2b.platform.entry.SettleAccountCreateParams;
import com.zhubao.b2b.platform.entry.SettleAccountQueryParameter;
import com.zhubao.b2b.platform.model.Order;
import com.zhubao.b2b.platform.model.SettleAccount;
import com.zhubao.b2b.platform.model.SettleAccountItem;
import com.zhubao.b2b.platform.model.Vender;
import com.zhubao.b2b.platform.service.OrderService;
import com.zhubao.b2b.platform.service.SettleAccountService;
import com.zhubao.b2b.platform.service.SettleService;
import com.zhubao.b2b.platform.service.UserService;
import com.zhubao.b2b.platform.utils.SettleAccountStatus;
import com.zhubao.common.utils.Pagination;
import org.apache.commons.httpclient.HttpStatus;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-2-27
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
public class SettleAccountServiceImpl extends BasicServiceSupport implements SettleAccountService {

    private SettleAccountDAO settleAccountDAO;

    private SettleAccountItemDAO settleAccountItemDAO;

    private VenderDAO venderDAO;

    private OrderService orderService;

    private SettleService settleService;

    private UserService userService;

    public void setSettleAccountDAO(SettleAccountDAO settleAccountDAO) {
        this.settleAccountDAO = settleAccountDAO;
    }

    public void setSettleAccountItemDAO(SettleAccountItemDAO settleAccountItemDAO) {
        this.settleAccountItemDAO = settleAccountItemDAO;
    }

    public void setVenderDAO(VenderDAO venderDAO) {
        this.venderDAO = venderDAO;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSettleService(SettleService settleService) {
        this.settleService = settleService;
    }

    @Override
    public ServiceResult<Pagination<SettleAccount>> getPaginationSettleAccounts(String userId, int pageIndex, int pageSize) {
        ServiceResult<Pagination<SettleAccount>> serviceResult = new ServiceResult<Pagination<SettleAccount>>();
        try{
            SettleAccountQueryParameter settleAccountQueryParameter = new SettleAccountQueryParameter();
            Vender vender = venderDAO.select(userId);
            if(vender != null){
                logger.debug("vender:[{}] request settle account list.", vender.getKshopUserName());
                settleAccountQueryParameter.setVenderId(vender.getKshopAgencyId());
            }else if (userService.isSuperUser(userId)){
                logger.debug("super user:[{}] request settle account list", userId);
            } else {
                logger.error("invalid user");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            Pagination pagination = new Pagination(pageIndex, pageSize);
            long totalCount = settleAccountDAO.count(settleAccountQueryParameter);
            pagination.setTotalRecord((int)totalCount);
            int start = 0;
            int range = 10;
            start = (pagination.getPage() - 1) * pagination.getRange();
            range = pagination.getRange();
            List<SettleAccount> settleAccountList = settleAccountDAO.select(settleAccountQueryParameter, start, range);
            pagination.setData(settleAccountList);
            serviceResult.setValue(pagination);
            serviceResult.setSuccess(true);
        }catch (Exception e){
            logger.error("fatal error!", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<SettleAccount> getDetail(String userId, String id) {
        ServiceResult<SettleAccount> serviceResult = new ServiceResult<SettleAccount>();
        try{
            SettleAccount settleAccount = settleAccountDAO.getById(id);
            if(!userService.isSuperUser(userId)){
                Vender vender = venderDAO.select(userId);
                if(!settleAccount.getVenderId().equalsIgnoreCase(vender.getKshopAgencyId())){
                    logger.error("invalid user");
                    serviceResult.setErrorCode(600);
                    serviceResult.setErrorMessage("operation forbidden");
                    serviceResult.setSuccess(false);
                    return serviceResult;
                }
            }
            settleAccount.setItems(settleAccountItemDAO.listBySettleAccountId(id));
            serviceResult.setSuccess(true);
            serviceResult.setValue(settleAccount);
        }catch (Exception e){
            logger.error("fatal error!", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<SettleAccount> createSettleAccount(String userId, String venderId, long beginTime, long endTime) {
        ServiceResult<SettleAccount> serviceResult = new ServiceResult<SettleAccount>();
        try{
            if(!userService.isSuperUser(userId)){
                logger.error("invalid user");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            long nowTime = System.currentTimeMillis();
            if(beginTime > endTime || endTime > nowTime){
                logger.error("time value is invalid");
                serviceResult.setSuccess(false);
                serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
                serviceResult.setErrorMessage("time value is invalid");
            }
            List<Order> listOrders = orderService.selectListByVenderIdPayTime(userId,venderId, beginTime, endTime).getValue();
            SettleAccount settleAccount1 = settleAccountDAO.getByVenderTime(venderId, beginTime, endTime);
            if(settleAccount1 != null){
                settleAccountItemDAO.removeBySettleId(settleAccount1.getId());
                settleAccountDAO.remove(settleAccount1);
            }
            Vender vender = venderDAO.selectByKShopAgencyId(venderId);
            SettleAccount settleAccount = new SettleAccount();
            settleAccount.setId(IdFactory.generateId());
            settleAccount.setVenderId(venderId);
            settleAccount.setBeginTime(beginTime);
            settleAccount.setEndTime(endTime);
            settleAccount.setCreatedTime(nowTime);
            settleAccount.setStatus(SettleAccountStatus.INIT.getValue());
            settleAccount.setStatusDesc(SettleAccountStatus.INIT.getDesc());
            settleAccount.setVenderAgencyName(vender.getKshopAgencyName());

            List<SettleAccountItem> listItem = new ArrayList<SettleAccountItem>();
            for(Order entry : listOrders){
                SettleAccountItem item = settleService.doSettle(settleAccount.getId(), entry);
                listItem.add(item);
                settleAccountItemDAO.insert(item);
            }
            settleAccount.setItems(listItem);
            buildSettleSum(settleAccount);
            settleAccountDAO.insert(settleAccount);
            serviceResult.setSuccess(true);
            serviceResult.setValue(settleAccount);
        }catch (Exception e){
            logger.error("fatal error!", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    private void buildSettleSum(SettleAccount settleAccount) {
        settleAccount.setTradeVolume(0.00f);
        settleAccount.setReceivables(0.00f);
        settleAccount.setCommission(0.00f);
        settleAccount.setServiceFee(0.00f);
        List<SettleAccountItem> accountItemList = settleAccount.getItems();
        for (SettleAccountItem item : accountItemList){
            settleAccount.setTradeVolume(settleAccount.getTradeVolume() + item.getOrderAmount());
            settleAccount.setCommission(settleAccount.getCommission() + item.getCommission());
            settleAccount.setServiceFee(settleAccount.getServiceFee() + item.getServiceFee());
        }
        settleAccount.setReceivables(settleAccount.getTradeVolume() - settleAccount.getCommission() - settleAccount.getServiceFee());
    }


    @Override
    public ServiceResult<SettleAccount> confirmSettleAccount(String userId, String id) {
        ServiceResult<SettleAccount> serviceResult = new ServiceResult<SettleAccount>();
        try{
            SettleAccount settleAccount = settleAccountDAO.getById(id);
            Vender vender = venderDAO.select(userId);
            if(!settleAccount.getVenderId().equalsIgnoreCase(vender.getKshopAgencyId())){
                logger.error("invalid user");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            settleAccount.setStatus(SettleAccountStatus.CONFIRMED.getValue());
            settleAccount.setStatusDesc(SettleAccountStatus.CONFIRMED.getDesc());
            settleAccountDAO.insert(settleAccount);
            serviceResult.setSuccess(true);
            serviceResult.setValue(settleAccount);
        }catch (Exception e){
            logger.error("fatal error!", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }


    @Override
    public ServiceResult<SettleAccount> checkSettleAccount(String userId, String id) {
        ServiceResult<SettleAccount> serviceResult = new ServiceResult<SettleAccount>();
        try{
            if(!userService.isSuperUser(userId)){
                logger.error("invalid user");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            SettleAccount settleAccount = settleAccountDAO.getById(id);
            settleAccount.setStatus(SettleAccountStatus.CHECKED.getValue());
            settleAccount.setStatusDesc(SettleAccountStatus.CHECKED.getDesc());
            settleAccount.setCheckTime(System.currentTimeMillis());
            settleAccountDAO.insert(settleAccount);
            serviceResult.setSuccess(true);
            serviceResult.setValue(settleAccount);
        } catch (Exception e){
            logger.error("fatal error!", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<SettleAccount> deleteSettleAccount(String userId, String id) {
        ServiceResult<SettleAccount> serviceResult = new ServiceResult<SettleAccount>();
        try{
            if(!userService.isSuperUser(userId)){
                logger.error("invalid user");
                serviceResult.setErrorCode(600);
                serviceResult.setErrorMessage("operation forbidden");
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            SettleAccount settleAccount = settleAccountDAO.getById(id);
            settleAccount.setStatus(SettleAccountStatus.DELETED.getValue());
            settleAccount.setStatusDesc(SettleAccountStatus.DELETED.getDesc());
            settleAccountDAO.insert(settleAccount);
            serviceResult.setSuccess(true);
            serviceResult.setValue(settleAccount);
        } catch (Exception e){
            logger.error("fatal error!", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<SettleAccountCreateParams>> getTaskCreateParams(long currenttime, long cycle) {
        ServiceResult<List<SettleAccountCreateParams>> serviceResult = new ServiceResult<List<SettleAccountCreateParams>>();
        List<SettleAccountCreateParams> settleAccountCreateParamsList = new ArrayList<SettleAccountCreateParams>();
        try{
            List<Vender> venderList = venderDAO.selectList();
            Set<String> venderIdSet = new HashSet<String>();
            Date date = new Date(currenttime - 10 * cycle);
            long lastTime = date.getTime()/cycle * cycle;
            for(Vender vender:venderList){
                venderIdSet.add(vender.getKshopAgencyId());
            }
            for(String venderId:venderIdSet){
                SettleAccount settleAccount = settleAccountDAO.getLastSettleAccount(venderId);
                if(settleAccount != null){
                    lastTime = settleAccount.getEndTime();
                }
                for (long indextime = lastTime; (indextime + cycle) < currenttime; indextime += cycle){
                    SettleAccountCreateParams params = new SettleAccountCreateParams();
                    params.setVenderId(venderId);
                    params.setUserId(Constants.SUPER_USER_ID);
                    params.setBeginTime(indextime);
                    params.setEndTime(indextime + cycle);
                    settleAccountCreateParamsList.add(params);
                }
            }
            logger.debug("create params count:[{}]", settleAccountCreateParamsList.size());
            serviceResult.setSuccess(true);
            serviceResult.setValue(settleAccountCreateParamsList);
        }catch (Exception e){
            logger.error("fatal error!", e);
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(610);
            serviceResult.setErrorMessage(e.getMessage());
        }
        return serviceResult;
    }
}

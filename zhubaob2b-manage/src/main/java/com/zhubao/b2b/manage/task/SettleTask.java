package com.zhubao.b2b.manage.task;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.platform.entry.SettleAccountCreateParams;
import com.zhubao.b2b.platform.model.SettleAccount;
import com.zhubao.b2b.platform.service.SettleAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 */
@Component("settleTaskJob")
public class SettleTask {

    private static Logger logger = LoggerFactory.getLogger(SettleTask.class);

    private static final long DAY_IN_MILLI = 24 * 60 * 60 * 1000l;

    @Autowired
    private SettleAccountService settleAccountService;

    @Scheduled(cron = "0,10,20,30,40,50 * * * * ?")
    public void settleJob() {
        long beginTime = System.currentTimeMillis();
        logger.debug("settle job start at[{}]", beginTime);
        ServiceResult<List<SettleAccountCreateParams>> serviceResult = settleAccountService.getTaskCreateParams(beginTime, DAY_IN_MILLI);
        if(serviceResult.isSuccess()){
            List<SettleAccountCreateParams> listParams = serviceResult.getValue();
            for(SettleAccountCreateParams params: listParams){
                logger.debug("create settle account with[{}][{}][{}][{}]", new Object[]{params.getUserId(), params.getVenderId(), params.getBeginTime(), params.getEndTime()});
                ServiceResult<SettleAccount> serviceResult1 = settleAccountService.createSettleAccount(params.getUserId(), params.getVenderId(), params.getBeginTime(), params.getEndTime());
                logger.debug("create settle account with[{}][{}][{}][{}],result:[{}]", new Object[]{params.getUserId(), params.getVenderId(), params.getBeginTime(), params.getEndTime(),serviceResult1.isSuccess()});
            }
        } else {
            logger.error("get create params error,code:[{}]msg[{}]", serviceResult.getErrorCode(), serviceResult.getErrorMessage());
        }
        long endTime = System.currentTimeMillis();
        logger.debug("settle job end at[{}],elapse:[{}]", endTime, (endTime-beginTime));
    }
}

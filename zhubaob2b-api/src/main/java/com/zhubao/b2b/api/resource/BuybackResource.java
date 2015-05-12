package com.zhubao.b2b.api.resource;

import com.zhubao.b2b.common.service.ServiceResult;
import com.zhubao.b2b.platform.entity.BuybackApplyItem;
import com.zhubao.common.utils.Pagination;
import com.zhubao.b2b.platform.entry.BuybackQueryParameter;
import com.zhubao.b2b.platform.model.BuybackApply;
import com.zhubao.b2b.platform.service.BuybackService;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */

@Path("/api")
public class BuybackResource {

    private static final Logger logger = LoggerFactory.getLogger(BuybackResource.class);

    private BuybackService buybackService;

    public void setBuybackService(BuybackService buybackService) {
        this.buybackService = buybackService;
    }

    @POST
    @Path("/1/order/buyback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createApply(@HeaderParam("uid")String userId, @FormParam("vid")String venderId, @FormParam("applyTime")String applyTime, @FormParam("rolex")String[] rolex, @FormParam("weight")String[] weight){
        logger.debug("[{}] create one new apply with vender:[{}] and other params:[{}]",new Object[]{userId, venderId, applyTime});
        List<BuybackApplyItem> buybackApplyItemList = new ArrayList<BuybackApplyItem>();
        for (int i = 0, j = rolex.length; i < j; i++){
            BuybackApplyItem item = new BuybackApplyItem();
            item.setMaterialRolex(rolex[i]);
            item.setSelfWeight(weight[i]);
            buybackApplyItemList.add(item);
        }
        ServiceResult<BuybackApply> result = buybackService.createApply(userId, venderId, applyTime, buybackApplyItemList);
        if(!result.isSuccess()){
            logger.error("[{}] create apply, but fatal error occur:[{}]! message:[{}]", new Object[]{ userId, result.getErrorCode(), result.getErrorMessage()});
            return Response.status(result.getErrorCode()).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @GET
    @Path("/1/order/buyback/list/{pindex}_{pcount}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listByCustomer(@HeaderParam("uid")String userId, @PathParam("pindex")int pindex, @PathParam("pcount")int pcount){
        logger.debug("[{}] list apply", userId);
        BuybackQueryParameter queryParameter = new BuybackQueryParameter();
        queryParameter.setCustomerId(userId);
        if(pindex < 0)
            pindex = 0;
        if(pcount < 0)
            pcount = 20;
        ServiceResult<Pagination<BuybackApply>> result = buybackService.getPaginationApply(queryParameter, pindex, pcount);
        if(!result.isSuccess()){
            logger.error("[{}] list apply but fatal error occur.", userId);
            return Response.status(result.getErrorCode()).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @GET
    @Path("/1/order/buyback/vender/list/{vid}_{pindex}_{pcount}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listByVender(@HeaderParam("uid")String userId, @PathParam("vid")String venderId, @PathParam("pindex") int pindex, @PathParam("pcount") int pcount){
        logger.debug("[{}] list apply of vender:[{}]", userId, venderId);
        BuybackQueryParameter queryParameter = new BuybackQueryParameter();
        queryParameter.setVenderId(venderId);
        if(pindex < 0)
            pindex = 0;
        if(pcount < 0)
            pcount = 20;
        ServiceResult<Pagination<BuybackApply>> result = buybackService.getPaginationApply(queryParameter, pindex, pcount);
        if(!result.isSuccess()){
            logger.error("[{}] list apply of vender:[{}] but fatal error occur.", userId, venderId);
            return Response.status(result.getErrorCode()).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }


    @GET
    @Path("/1/order/buyback/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuyback(@HeaderParam("uid")String userId, @PathParam("id")String applyId){
        logger.debug("[{}] get apply:[{}]", userId, applyId);
        ServiceResult<BuybackApply> result = buybackService.getApplyById(userId, applyId);
        if(!result.isSuccess()){
            logger.error("[{}] get apply but fatal error occur.", userId);
            return Response.status(result.getErrorCode()).entity(result.getErrorMessage()).build();
        }
        return Response.ok(result.getValue()).build();
    }

    @DELETE
    @Path("/1/order/buyback/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBuyback(@HeaderParam("uid")String userId, @PathParam("id")String applyId){
        logger.debug("[{}] delete apply:[{}]", userId, applyId);
        ServiceResult<BuybackApply> result = buybackService.deleteApply(userId, applyId);
        if(!result.isSuccess()){
            logger.error("[{}] delete apply:[{}], but fatal error occur.", userId, applyId);
            return Response.status(result.getErrorCode()).entity(result.getErrorMessage()).build();
        }
        return  Response.ok(result.getValue()).build();
    }
}

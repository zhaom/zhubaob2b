package com.zhubao.b2b.api.resource;


import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.platform.model.ShipAddress;
import com.zhubao.b2b.platform.service.ShipAddressService;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class UserReceiptAddrResouce {

    private final static Logger logger = LoggerFactory.getLogger(UserReceiptAddrResouce.class);

    private ShipAddressService shipAddressService;

    public void setShipAddressService(ShipAddressService shipAddressService) {
        this.shipAddressService = shipAddressService;
    }

    @POST
    @Path("/api/1/user/addr")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveAddress(@HeaderParam("uid")String userId, ShipAddress shipAddress){
        logger.debug("user [{}] save one address []", userId, shipAddress);
        shipAddress.setCustomerId(userId);
        if(shipAddress.getId() == null){
            shipAddress.setId(IdFactory.generateId());
        }
        try{
            shipAddressService.createShipAddress(shipAddress);
        } catch (Exception e){
            logger.error("user [{}] save shipaddress error.", userId, e);
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(shipAddress.getId()).build();
    }

    @POST
    @Path("/api/1/user/addr/default/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setDefaultAddress(@HeaderParam("uid")String userId, @PathParam("id")String addrId){
        logger.debug("user [{}] set addr [{}] to be default addr", userId, addrId);
        try{
            shipAddressService.setDefaultAddress(userId, addrId);
        } catch (Exception e){
            logger.error("user [{}] set addr [{}] to be deault error", new Object[]{userId, addrId}, e);
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/api/1/user/addr/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAddress(@HeaderParam("uid") String userId, @PathParam("id")String addrId){
        logger.debug("user [{}] delete addr[{}]", userId, addrId);
        try{
            shipAddressService.deleteAddress(userId, addrId);
        } catch (Exception e){
            logger.error("user [{}] delete ");
        }
        return Response.ok().build();
    }

    @GET
    @Path("/api/1/user/addr/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response myAddresses(@HeaderParam("uid")String userId){
        logger.debug("user [{}] list addr", userId);
        try{
            List<ShipAddress> shipAddressList = shipAddressService.getAddresses(userId);
        } catch (Exception e){
            logger.error("user [{}] list addr error", userId, e);
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }
}

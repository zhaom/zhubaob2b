package com.zhubao.b2b.api.resource;

import com.zhubao.b2b.common.utils.Symbols;
import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.model.LoginStat;
import com.zhubao.b2b.platform.model.Vender;
import com.zhubao.b2b.platform.service.UserService;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User: kin
 * Date: 13-8-19
 * Time: 下午2:15
 */

@Path("/api")
public class UserResource extends BasicResourceSupport {

    private UserService userService;

    @GET
    @Path("/1/user/last-login-stat")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response getLastLoginStat(@HeaderParam("userId") String userId) {
        if (logger.isDebugEnabled()) {
            logger.debug("get user last login stat: {}", new Object[]{userId});
        }
        LoginStat stat = userService.getLastLoginStat(userId);
        if (stat != null) {
            return Response.ok(stat).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/0/user/customer/item/{customerId}")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response getCustomer(@PathParam("customerId") String customerId) {
        if (logger.isDebugEnabled()) {
            logger.debug("get customer: {}", new Object[]{customerId});
        }
        Customer customer = userService.getCustomer(customerId);
        if (customer != null) {
            return Response.ok(customer).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/0/user/vender/item/{venderId}")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response getVender(@PathParam("venderId") String venderId) {
        if (logger.isDebugEnabled()) {
            logger.debug("get vender: {}", new Object[]{venderId});
        }
        Vender vender = userService.getVender(venderId);
        if (vender != null) {
            return Response.ok(vender).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/0/user/vender/list")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response listAllVenders() {
        if (logger.isDebugEnabled()) {
            logger.debug("list all venders...");
        }
        List<Vender> venders = userService.getAllVenders();
        return Response.ok(venders).build();
    }

    @GET
    @Path("/0/user/customer/login")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response customerLogin(@QueryParam("loginName") String loginName, @QueryParam("password") String password) {
        if (StringUtils.isNotEmpty(loginName) && StringUtils.isNotEmpty(password)) {
            if (logger.isDebugEnabled()) {
                logger.debug("customer login: {}, {}", new Object[]{loginName, password});
            }
            Customer customer = userService.getIsLegalCustomer(loginName, password);
            if (customer != null) {
                return Response.ok(customer).build();
            }
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/0/user/vender/login")
    @Produces(Symbols.MediaTypes.MEDIA_TYPE_JSON)
    public Response venderLogin(@QueryParam("loginName") String loginName, @QueryParam("password") String password) {
        if (StringUtils.isNotEmpty(loginName) && StringUtils.isNotEmpty(password)) {
            if (logger.isDebugEnabled()) {
                logger.debug("vender login: {}, {}", new Object[]{loginName, password});
            }
            Vender vender = userService.getIsLegalVender(loginName, password);
            if (vender != null) {
                return Response.ok(vender).build();
            }
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

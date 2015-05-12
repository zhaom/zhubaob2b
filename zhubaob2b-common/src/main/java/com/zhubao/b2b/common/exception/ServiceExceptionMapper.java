package com.zhubao.b2b.common.exception;

import org.apache.commons.httpclient.HttpStatus;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * User: xiaoping lu
 * Date: 13-8-19
 * Time: 下午5:18
 */

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException exception) {
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }
}

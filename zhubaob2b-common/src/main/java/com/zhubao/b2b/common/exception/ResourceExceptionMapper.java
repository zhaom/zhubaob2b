package com.zhubao.b2b.common.exception;

import org.apache.commons.httpclient.HttpStatus;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * User: xiaoping lu
 * Date: 13-8-19
 * Time: 下午5:22
 */

@Provider
public class ResourceExceptionMapper implements ExceptionMapper<ResourceException> {

    @Override
    public Response toResponse(ResourceException exception) {
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }
}

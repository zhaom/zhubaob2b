package com.zhubao.b2b.common.exception;

/**
 * User: xiaoping lu
 * Date: 13-8-19
 * Time: 下午12:08
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message, Throwable t) {
        super(message, t);
    }
}

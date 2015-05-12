package com.zhubao.b2b.common.service;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class ServiceResult<E> {

    private boolean isSuccess;

    private int errorCode;

    private String errorMessage;

    private E value;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}

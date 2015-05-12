package com.zhubao.b2b.file.utils;

import javax.servlet.http.HttpServletRequest;

public class UploadParam {

    public static final int ERROR_SAVE = 1;
    public static final int SUCC_SAVE = 0;

    private HttpServletRequest request;
    private boolean succ;
    private String saveTo;
    private int errorCode;
    private String srcFilename;
    private String destFilename;
    private String fileUrl;
    private String fileUrlBase;
    private String fileExt;
    private long fileSize;
    private String message;

    public String getReplyJSONMsg() {
        StringBuffer result = new StringBuffer();
        result.append("{");
        result.append("\"succ\":\"").append(succ).append("\"");
        result.append(",\"srcFilename\":\"").append(srcFilename).append("\"");
        result.append(",\"destFilename\":\"").append(destFilename).append("\"");
        result.append(",\"savePath\":\"").append(saveTo).append("\"");
        result.append(",\"fileUrlBase\":\"").append(fileUrlBase).append("\"");
        result.append(",\"fileUrl\":\"").append(fileUrl).append("\"");
        result.append(",\"fileExt\":\"").append(fileExt).append("\"");
        result.append(",\"fileSize\":\"").append(fileSize).append("\"");
        result.append(",\"errorCode\":\"").append(errorCode).append("\"");
        result.append(",\"url\":\"").append(fileUrl).append("\"");
        result.append(",\"message\":\"").append(message).append("\"");
        result.append(",\"error\":").append(errorCode);
        result.append("}");
        return result.toString();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public String getSaveTo() {
        return saveTo;
    }

    public void setSaveTo(String saveTo) {
        this.saveTo = saveTo;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getSrcFilename() {
        return srcFilename;
    }

    public void setSrcFilename(String srcFilename) {
        this.srcFilename = srcFilename;
    }

    public String getDestFilename() {
        return destFilename;
    }

    public void setDestFilename(String destFilename) {
        this.destFilename = destFilename;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUrlBase() {
        return fileUrlBase;
    }

    public void setFileUrlBase(String fileUrlBase) {
        this.fileUrlBase = fileUrlBase;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

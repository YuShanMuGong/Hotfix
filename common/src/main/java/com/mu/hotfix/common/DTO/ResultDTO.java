package com.mu.hotfix.common.DTO;

import java.io.Serializable;

public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 4212345654321L;

    private boolean success;
    private String errorCode;
    private String errorMsg;
    private T content;

    public ResultDTO() {
    }

    private ResultDTO(boolean success, String errorCode , String errorMsg, T content) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.content = content;
    }

    public boolean isSuccess(){
        return success;
    }

    public static <T> ResultDTO<T> fail(String errorCode , String errorMsg){
        return new ResultDTO<>(false,errorCode,errorMsg,null);
    }

    public static <T> ResultDTO<T> success(T content){
        return new ResultDTO<>(true,null,null,content);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public T getContent() {
        return content;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

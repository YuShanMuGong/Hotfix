package com.mu.hotfix.common.DTO;

import java.io.Serializable;

public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 4212345654321L;

    private final boolean success;
    private final String errorCode;
    private final String errorMsg;
    private final T content;

    private ResultDTO(boolean success,String errorCode , String errorMsg, T content) {
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

}

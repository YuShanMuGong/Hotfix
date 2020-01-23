package com.mu.hotfix.client.exception;

public class HotFixClientException extends RuntimeException {

    private String code;
    private String msg;

    public HotFixClientException(String code, String msg) {
        super("exception! code=" + code + ",msg=" + msg);
        this.code = code;
        this.msg = msg;
    }

    public HotFixClientException(String code, Throwable throwable) {
        super("exception! code=" + code, throwable);
        this.code = code;
        this.msg = throwable.getLocalizedMessage();
    }


    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

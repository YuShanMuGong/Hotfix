package com.mu.hotfix.srv.exception;

public class HotFixSrvException extends RuntimeException {

    private String code;
    private String msg;

    public HotFixSrvException(String code, String msg) {
        super("exception! code=" + code + ",msg=" + msg);
        this.code = code;
        this.msg = msg;
    }

    public HotFixSrvException(String code, Throwable throwable) {
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

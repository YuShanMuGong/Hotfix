package com.mu.hotfix.common.BO;

import java.io.Serializable;

public class RemoteClassBO implements Serializable {

    private static final long serialVersionUID = 413456765432L;

    private String app;

    private String className;

    private int version;

    private byte[] content;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}

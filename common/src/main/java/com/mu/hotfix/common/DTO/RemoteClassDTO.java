package com.mu.hotfix.common.DTO;

import java.io.Serializable;

public class RemoteClassDTO implements Serializable {

    private static final long serialVersionUID = 413456765432L;

    private String app;

    private String className;

    private String version;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}

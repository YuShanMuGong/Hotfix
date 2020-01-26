package com.mu.hotfix.client.remote;

import com.mu.hotfix.common.bo.RemoteClassBO;

import java.util.List;

public interface IRemoteManager {

    byte[] getClass(String app , String className);

    List<RemoteClassBO> getAllClass(String app);

}

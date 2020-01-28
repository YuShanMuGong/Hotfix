package com.mu.hotfix.client.manager.remote;

import com.mu.hotfix.common.bo.RemoteClassBO;

import java.util.List;

public interface IRemoteManager {

    RemoteClassBO getClass(String app , String className);

    List<RemoteClassBO> getAllClass(String app);

}

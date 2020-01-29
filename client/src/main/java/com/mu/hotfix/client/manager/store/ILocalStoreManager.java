package com.mu.hotfix.client.manager.store;

import com.mu.hotfix.common.BO.RemoteClassBO;

import java.util.List;

public interface ILocalStoreManager {

    RemoteClassBO getClass(String app , String className);

    void saveClass(RemoteClassBO remoteClassBO);

    void asyncSaveClasses(List<RemoteClassBO> remoteClassBOS);

    void asyncSaveClass(RemoteClassBO remoteClassBO);

}

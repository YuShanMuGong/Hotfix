package com.mu.hotfix.client.manager.store;


import com.mu.hotfix.common.DTO.RemoteClassDTO;

import java.util.List;

public interface ILocalStoreManager {

    RemoteClassDTO getClass(String app , String className);

    void saveClass(RemoteClassDTO remoteClassBO);

    void asyncSaveClasses(List<RemoteClassDTO> remoteClassBOS);

    void asyncSaveClass(RemoteClassDTO remoteClassBO);

}

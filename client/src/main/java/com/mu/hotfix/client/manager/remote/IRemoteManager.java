package com.mu.hotfix.client.manager.remote;

import com.mu.hotfix.common.DTO.RemoteClassDTO;

import java.util.List;

public interface IRemoteManager {

    RemoteClassDTO getClass(String app , String className);

    List<RemoteClassDTO> getAllClass(String app);

}

package com.mu.hotfix.client.manager.cache;

import com.mu.hotfix.common.DTO.RemoteClassDTO;

import java.util.List;

public interface ICacheManager {

    void put(String key , RemoteClassDTO remoteClassDTO);

    RemoteClassDTO get(String key);

    void invalid(String key);

    List<String> listAllKeys();

}

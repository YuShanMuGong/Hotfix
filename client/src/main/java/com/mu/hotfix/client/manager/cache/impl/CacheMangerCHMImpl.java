package com.mu.hotfix.client.manager.cache.impl;

import com.mu.hotfix.client.manager.cache.ICacheManager;
import com.mu.hotfix.common.DTO.RemoteClassDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache 基于 ConcurrentHashMap 的实现
 */
public class CacheMangerCHMImpl implements ICacheManager {

    private ConcurrentHashMap<String, RemoteClassDTO> cacheStore = new ConcurrentHashMap<>();

    @Override
    public void put(String key, RemoteClassDTO content) {
        cacheStore.put(key,content);
    }

    @Override
    public RemoteClassDTO get(String key) {
       return cacheStore.get(key);
    }

    @Override
    public void invalid(String key) {
        cacheStore.remove(key);
    }

    @Override
    public List<String> listAllKeys() {
       ConcurrentHashMap.KeySetView<String,RemoteClassDTO> keySetView = cacheStore.keySet();
       List<String> keys = new ArrayList<>(keySetView.size());
       keys.addAll(keySetView);
       return keys;
    }
}

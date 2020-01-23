package com.mu.hotfix.client.cache.impl;

import com.mu.hotfix.client.cache.ICacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache 基于 ConcurrentHashMap 的实现
 */
public class CacheMangerCHMImpl implements ICacheManager {

    private ConcurrentHashMap<String,byte[]> cacheStore = new ConcurrentHashMap<>();

    @Override
    public void put(String key, byte[] content) {
        cacheStore.put(key,content);
    }

    @Override
    public byte[] get(String key) {
       return cacheStore.get(key);
    }

    @Override
    public void invalid(String key) {
        cacheStore.remove(key);
    }

    @Override
    public List<String> listAllKeys() {
       ConcurrentHashMap.KeySetView<String,byte[]> keySetView = cacheStore.keySet();
       List<String> keys = new ArrayList<>(keySetView.size());
       keys.addAll(keySetView);
       return keys;
    }
}

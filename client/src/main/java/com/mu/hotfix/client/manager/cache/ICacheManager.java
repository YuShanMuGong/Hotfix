package com.mu.hotfix.client.manager.cache;

import java.util.List;

public interface ICacheManager {

    void put(String key , byte[] content);

    byte[] get(String key);

    void invalid(String key);

    List<String> listAllKeys();

}

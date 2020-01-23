package com.mu.hotfix.client.cache;

import java.util.List;

public interface ICacheManager {

    void put(String key , byte[] content);

    byte[] get(String key);

    void invalide(String key);

    List<String> listAllKeys();

}

package com.mu.hotfix.client.manager.store;

public interface ILocalStoreManager {

    byte[] getClass(String app , String className);

    void putClass(String app , String className , byte[] contents);
}

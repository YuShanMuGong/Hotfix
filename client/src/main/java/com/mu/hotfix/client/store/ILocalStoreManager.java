package com.mu.hotfix.client.store;

public interface ILocalStoreManager {

    byte[] getClass(String app , String className);

    void putClass(String app , String className , byte[] contents);
}

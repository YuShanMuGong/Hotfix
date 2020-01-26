package com.mu.hotfix.client.core;

import com.mu.hotfix.client.cache.ICacheManager;
import com.mu.hotfix.client.config.IConfigManager;
import com.mu.hotfix.client.constans.ConfigConstants;
import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.remote.IRemoteManager;
import com.mu.hotfix.client.store.ILocalStoreManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义的支持热更新的ClassLoader
 */
public class XHotFixClassLoaderInner extends ClassLoader {

    private ICacheManager cacheManager;

    private IRemoteManager remoteManager;

    private IConfigManager configManager;

    private ILocalStoreManager localStoreManager;

    private String classLoaderName;

    private List<String> loadedClassName;

    public XHotFixClassLoaderInner(ClassLoader parentClassLoader , String name){
        super(parentClassLoader);
        this.classLoaderName = name;
        loadedClassName = new ArrayList<>();
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name){
        byte[] classBytes = findClassBytes(name);
        Class<?> cl = defineClass(name,classBytes,0,classBytes.length);
        loadedClassName.add(name);
        return cl;
    }

    private byte[] findClassBytes(String name){
        // 缓存命中
        if(cacheManager.get(name) != null){
            return cacheManager.get(name);
        }
        try {
            // 从远程服务，获取class文件
            byte[] remoteClass = remoteManager.getClass(null,name);
            // 获取远程Class失败
            if(remoteClass == null || remoteClass.length == 0){
                if(Boolean.valueOf(configManager.getConfig(ConfigConstants.FETCH_LOCAL_IF_REMOTE_FAIL))){
                    throw new HotFixClientException(ErrorCodes.FETCH_REMOTE_CLASS_FAIL,"fetch remote class return null");
                }
                byte[] localClass = localStoreManager.getClass(null,name);
                if(localClass == null || localClass.length == 0){
                    throw new HotFixClientException(ErrorCodes.FETCH_CLASS_FAIL,"can not find class");
                }
                return localClass;
            }
            return remoteClass;
        } catch (Exception e){
            throw new HotFixClientException(ErrorCodes.UNKNOW_EXCEPTION,e);
        }
    }

    public List<String> getLoadedClassNameList(){
        return Collections.unmodifiableList(loadedClassName);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + classLoaderName;
    }
}

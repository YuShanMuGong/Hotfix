package com.mu.hotfix.client.core;

import com.mu.hotfix.client.manager.cache.ICacheManager;
import com.mu.hotfix.client.manager.config.IConfigManager;
import com.mu.hotfix.client.constans.ConfigConstants;
import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.manager.remote.IRemoteManager;
import com.mu.hotfix.client.manager.store.ILocalStoreManager;
import com.mu.hotfix.common.bo.RemoteClassBO;
import com.mu.hotfix.common.util.ByteArrayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义的支持热更新的ClassLoader
 */
public class XHotFixClassLoaderInner extends ClassLoader {

    private ICacheManager cacheManager;

    private IRemoteManager remoteManager;

    private IConfigManager configAction;

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
            RemoteClassBO remoteClass = remoteManager.getClass(null,name);
            // 获取远程Class失败
            if(remoteClass == null || ByteArrayUtil.isEmpty(remoteClass.getContent())){
                if(Boolean.valueOf(configAction.getConfig(ConfigConstants.FETCH_LOCAL_IF_REMOTE_FAIL))){
                    throw new HotFixClientException(ErrorCodes.FETCH_REMOTE_CLASS_FAIL,"fetch remote class return null");
                }
                byte[] localClass = localStoreManager.getClass(null,name);
                if(localClass == null || localClass.length == 0){
                    throw new HotFixClientException(ErrorCodes.FETCH_CLASS_FAIL,"can not find class");
                }
                return localClass;
            }
            return remoteClass.getContent();
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

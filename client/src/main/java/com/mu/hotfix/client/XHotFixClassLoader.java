package com.mu.hotfix.client;

import com.mu.hotfix.client.cache.ICacheManager;
import com.mu.hotfix.client.config.IConfigManager;
import com.mu.hotfix.client.constans.ConfigConstants;
import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.remote.IRemoteManager;
import com.mu.hotfix.client.store.ILocalStoreManager;

/**
 * 自定义的支持热更新的ClassLoader
 */
public class XHotFixClassLoader extends ClassLoader {

    private ICacheManager cacheManager;

    private IRemoteManager remoteManager;

    private IConfigManager configManager;

    private ILocalStoreManager localStoreManager;

    private XHotFixClassLoader(){

    }

    public static XHotFixClassLoader getInstance(){
        return XHotFixClassLoaderHolder.loader;
    }

    @Override
    protected Class<?> findClass(String name){
        // 缓存命中
        if(cacheManager.get(name) != null){
            byte[] bytes = cacheManager.get(name);
            return defineClass(name,bytes,0,bytes.length);
        }
        try {
            // 从远程服务，获取class文件
            byte[] remoteClass = remoteManager.getClass(null,name);
            if(remoteClass == null || remoteClass.length == 0){
                if(Boolean.valueOf(configManager.getConfig(ConfigConstants.FETCH_LOCAL_IF_REMOTE_FAIL))){
                    throw new HotFixClientException(ErrorCodes.FETCH_REMOTE_CLASS_FAIL,"fetch remote class return null");
                }
                byte[] localClass = localStoreManager.getClass(null,name);
                if(localClass == null || localClass.length == 0){
                    throw new HotFixClientException(ErrorCodes.FETCH_CLASS_FAIL,"can not find class");
                }
                return defineClass(name,localClass,0,localClass.length);
            }
            return defineClass(name,remoteClass,0,remoteClass.length);
        } catch (Exception e){
            throw new HotFixClientException(ErrorCodes.UNKNOW_EXCEPTION,e);
        }
    }

    private static class XHotFixClassLoaderHolder{
        private static final XHotFixClassLoader loader = new XHotFixClassLoader();
    }


}

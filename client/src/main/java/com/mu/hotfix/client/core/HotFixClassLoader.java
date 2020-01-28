package com.mu.hotfix.client.core;

import com.mu.hotfix.client.constans.ConfigConstants;
import com.mu.hotfix.client.manager.cache.ICacheManager;
import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.manager.cache.impl.CacheMangerCHMImpl;
import com.mu.hotfix.client.manager.config.IConfigManager;
import com.mu.hotfix.client.manager.config.impl.ConfigManagerImpl;
import com.mu.hotfix.client.manager.remote.IRemoteManager;
import com.mu.hotfix.client.manager.remote.impl.RemoteManagerImpl;
import com.mu.hotfix.client.manager.store.ILocalStoreManager;
import com.mu.hotfix.client.manager.store.impl.LocalStoreManagerImpl;
import com.mu.hotfix.common.bo.RemoteClassBO;
import com.mu.hotfix.common.util.ByteArrayUtil;
import com.mu.hotfix.common.util.StringUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HotFixClassLoader extends ClassLoader implements IHotFixLoaderProcess {

    private AtomicInteger classLoaderInc = new AtomicInteger();

    private ClassLoader parentClassLoader;

    private IRemoteManager remoteManager;
    private ICacheManager cacheManager;
    private IConfigManager configManager;
    private ILocalStoreManager localStoreManager;
    private HotFixClassLoaderInner activeClassLoaderInner;

    public HotFixClassLoader(ClassLoader parentClassLoader){
        this.parentClassLoader = parentClassLoader;
        // 初始化Manager
        initManagers();
        // 启动时 获取远程最新的Class文件
        fetchRemoteClasses();
        activeClassLoaderInner = newClassLoader();
    }

    private void initManagers(){
        String configFilePath = ConfigConstants.DEFAULT_CONFIG_FILE_PATH;
        if(!StringUtil.isTrimEmpty(System.getProperty(ConfigConstants.CONFIG_FILE_KEY))){
            configFilePath = System.getProperty(ConfigConstants.CONFIG_FILE_KEY);
        }
        configManager = new ConfigManagerImpl(configFilePath);
        remoteManager = new RemoteManagerImpl(configManager.getConfig(ConfigConstants.REMOTE_SRV_HOST));
        cacheManager = new CacheMangerCHMImpl();
        localStoreManager = new LocalStoreManagerImpl(configManager.getConfig(ConfigConstants.LOCAL_STORE_PATH));
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
       return activeClassLoaderInner.loadClass(name,resolve);
    }

    @Override
    public void updateClass(RemoteClassBO updateClassBO){
        if(updateClassBO == null
                || StringUtil.isEmpty(updateClassBO.getClassName())
                || ByteArrayUtil.isEmpty(updateClassBO.getContent())){
            throw new HotFixClientException(ErrorCodes.PARAM_ILLEGAL,"update class param illegal");
        }
        cacheManager.put(updateClassBO.getClassName(),updateClassBO.getContent());
        //TODO: 写入本地文件系统
        activeClassLoaderInner = newClassLoader();
    }

    private HotFixClassLoaderInner newClassLoader(){
        return new HotFixClassLoaderInner(parentClassLoader,classLoaderInc.getAndIncrement()+"",this);
    }

    public byte[] findClassBytes(String name){
        // 缓存命中
        if(cacheManager.get(name) != null){
            return cacheManager.get(name);
        }
        try {
            // 从远程服务，获取class文件
            RemoteClassBO remoteClass = remoteManager.getClass(null,name);
            // 获取远程Class失败
            if(remoteClass == null || ByteArrayUtil.isEmpty(remoteClass.getContent())){
                if(Boolean.valueOf(configManager.getConfig(ConfigConstants.FETCH_LOCAL_IF_REMOTE_FAIL))){
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

    private void fetchRemoteClasses(){
        List<RemoteClassBO> remoteClassBOList = remoteManager.getAllClass(null);
        for (RemoteClassBO classBO : remoteClassBOList){
            cacheManager.put(classBO.getClassName(),classBO.getContent());
        }
        //TODO: 写入本地文件系统
    }

}

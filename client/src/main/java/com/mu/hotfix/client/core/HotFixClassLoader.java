package com.mu.hotfix.client.core;

import com.mu.hotfix.client.constants.ClientSrvUrlConstants;
import com.mu.hotfix.client.constants.ConfigConstants;
import com.mu.hotfix.client.handler.IRequestHandler;
import com.mu.hotfix.client.handler.impl.UpdateClassHandler;
import com.mu.hotfix.client.manager.cache.ICacheManager;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.manager.cache.impl.CacheMangerCHMImpl;
import com.mu.hotfix.client.manager.config.IConfigManager;
import com.mu.hotfix.client.manager.config.impl.ConfigManagerImpl;
import com.mu.hotfix.client.manager.remote.IRemoteManager;
import com.mu.hotfix.client.manager.remote.impl.RemoteManagerImpl;
import com.mu.hotfix.client.manager.store.ILocalStoreManager;
import com.mu.hotfix.client.manager.store.impl.LocalStoreManagerImpl;
import com.mu.hotfix.client.srv.EmbeddableHttpSrv;
import com.mu.hotfix.common.DTO.RemoteClassDTO;
import com.mu.hotfix.common.constants.ErrorCodes;
import com.mu.hotfix.common.util.ByteArrayUtil;
import com.mu.hotfix.common.util.MixUtil;
import com.mu.hotfix.common.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class HotFixClassLoader extends ClassLoader {

    private AtomicInteger classLoaderInc = new AtomicInteger();

    private ClassLoader parentClassLoader;

    private IRemoteManager remoteManager;
    private ICacheManager cacheManager;
    private IConfigManager configManager;
    private ILocalStoreManager localStoreManager;
    private HotFixClassLoaderInner activeClassLoaderInner;
    private String appName;
    private EmbeddableHttpSrv httpSrv;

    public HotFixClassLoader(ClassLoader parentClassLoader){
        this.parentClassLoader = parentClassLoader;
    }


    public void init(){
        // 初始化Manager
        initManagers();
        appName = configManager.getConfig(ConfigConstants.APP_NAME);
        int port = Integer.valueOf(configManager.getConfig(ConfigConstants.CLIENT_SRV_PORT));
        httpSrv = new EmbeddableHttpSrv(initSrvHandlerMap(),port);
    }

    public void start(){
        // 启动时 获取远程最新的Class文件
        fetchRemoteClasses();
        httpSrv.start();
        activeClassLoaderInner = newClassLoader();
    }

    private Map<String, IRequestHandler> initSrvHandlerMap(){
        Map<String,IRequestHandler> handlerMap = new HashMap<>();
        handlerMap.put(ClientSrvUrlConstants.UPDATE_CLASS,new UpdateClassHandler(this));
        return handlerMap;
    }

    private void initManagers(){
        String configFilePath = System.getProperty(ConfigConstants.CONFIG_FILE_KEY,ConfigConstants.DEFAULT_CONFIG_FILE_PATH);
        configManager = new ConfigManagerImpl(configFilePath);
        remoteManager = new RemoteManagerImpl(configManager.getConfig(ConfigConstants.REMOTE_SRV_HOST));
        cacheManager = new CacheMangerCHMImpl();
        localStoreManager = new LocalStoreManagerImpl(configManager.getConfig(ConfigConstants.LOCAL_STORE_PATH));
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
       return activeClassLoaderInner.loadClass(name,resolve);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return activeClassLoaderInner.loadClass(name);
    }

    public void updateClass(RemoteClassDTO updateClassBO){
        if(updateClassBO == null
                || StringUtil.isEmpty(updateClassBO.getClassName())
                || ByteArrayUtil.isEmpty(updateClassBO.getContent())){
            throw new HotFixClientException(ErrorCodes.PARAM_ILLEGAL,"update class param illegal");
        }
        cacheManager.put(updateClassBO.getClassName(),updateClassBO.getContent());
        localStoreManager.asyncSaveClass(updateClassBO);
        activeClassLoaderInner = newClassLoader();
    }

    private HotFixClassLoaderInner newClassLoader(){
        return new HotFixClassLoaderInner(parentClassLoader,classLoaderInc.getAndIncrement()+"",this);
    }

    byte[] findClassBytes(String name){
        // 缓存命中
        if(cacheManager.get(name) != null){
            return cacheManager.get(name);
        }
        try {
            // 从远程服务，获取class文件
            RemoteClassDTO remoteClass = remoteManager.getClass(appName,name);
            // 获取远程Class失败
            if(!MixUtil.isValid(remoteClass)){
                if(Boolean.valueOf(configManager.getConfig(ConfigConstants.FETCH_LOCAL_IF_REMOTE_FAIL))){
                    throw new HotFixClientException(ErrorCodes.FETCH_REMOTE_CLASS_FAIL,"fetch remote class return null");
                }
                RemoteClassDTO remoteClassBO = localStoreManager.getClass(appName,name);
                if(!MixUtil.isValid(remoteClassBO)){
                    throw new HotFixClientException(ErrorCodes.FETCH_CLASS_FAIL,"can not find class");
                }
                return remoteClassBO.getContent();
            }
            return remoteClass.getContent();
        } catch (Exception e){
            throw new HotFixClientException(ErrorCodes.UN_KNOW_EXCEPTION,e);
        }
    }

    private void fetchRemoteClasses(){
        List<RemoteClassDTO> remoteClassBOList = remoteManager.getAllClass(appName);
        for (RemoteClassDTO classBO : remoteClassBOList){
            cacheManager.put(classBO.getClassName(),classBO.getContent());
        }
        localStoreManager.asyncSaveClasses(remoteClassBOList);
    }

}

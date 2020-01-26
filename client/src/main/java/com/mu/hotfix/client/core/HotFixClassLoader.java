package com.mu.hotfix.client.core;

import com.mu.hotfix.client.cache.ICacheManager;
import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.remote.IRemoteManager;
import com.mu.hotfix.common.bo.RemoteClassBO;
import com.mu.hotfix.common.util.ByteArrayUtil;
import com.mu.hotfix.common.util.StringUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HotFixClassLoader extends ClassLoader {

    private AtomicInteger classLoaderInc = new AtomicInteger();

    private ClassLoader parentClassLoader;

    private IRemoteManager remoteManager;
    private ICacheManager cacheManager;
    private XHotFixClassLoaderInner activeClassLoaderInner;

    public HotFixClassLoader(ClassLoader parentClassLoader){
        this.parentClassLoader = parentClassLoader;
        // 启动时 获取远程最新的Class文件
        fetchRemoteClasses();
        activeClassLoaderInner = newClassLoader();
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
       return activeClassLoaderInner.loadClass(name,resolve);
    }

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

    private XHotFixClassLoaderInner newClassLoader(){
        return new XHotFixClassLoaderInner(parentClassLoader,classLoaderInc.getAndIncrement()+"");
    }

    private void fetchRemoteClasses(){
        List<RemoteClassBO> remoteClassBOList = remoteManager.getAllClass(null);
        for (RemoteClassBO classBO : remoteClassBOList){
            cacheManager.put(classBO.getClassName(),classBO.getContent());
        }
        //TODO: 写入本地文件系统
    }

}

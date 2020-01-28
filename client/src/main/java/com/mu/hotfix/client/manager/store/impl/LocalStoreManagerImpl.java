package com.mu.hotfix.client.manager.store.impl;

import com.alibaba.fastjson.JSON;
import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.manager.store.ILocalStoreManager;
import com.mu.hotfix.common.bo.RemoteClassBO;
import com.mu.hotfix.common.util.ByteArrayUtil;
import com.mu.hotfix.common.util.CollectionUtil;
import com.mu.hotfix.common.util.FileUtil;
import com.mu.hotfix.common.util.StringUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalStoreManagerImpl implements ILocalStoreManager {

    private final String basePath;
    private final Charset charset = Charset.forName("utf8");
    private ExecutorService asyncStoreExecutor;

    public LocalStoreManagerImpl(String storeBasePath){
        this.basePath = storeBasePath;
        asyncStoreExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread();
            thread.setName("LocalStoreManagerImpl-Async-Write-Thread");
            return thread;
        });
    }

    @Override
    public RemoteClassBO getClass(String app, String className) {
        String filePath = basePath + File.pathSeparator + "class" + File.pathSeparator + app + "_" + className + ".cl";
        byte[] jsonBytes = FileUtil.readFile(filePath);
        return JSON.parseObject(new String(jsonBytes,charset),RemoteClassBO.class);
    }

    @Override
    public void saveClass(RemoteClassBO remoteClassBO) {
        if(remoteClassBO == null
                || ByteArrayUtil.isEmpty(remoteClassBO.getContent())
                || StringUtil.isTrimEmpty(remoteClassBO.getApp())
                || StringUtil.isTrimEmpty(remoteClassBO.getClassName())){
            throw new HotFixClientException(ErrorCodes.PARAM_ILLEGAL,"save class params illegal");
        }
        String filePath = basePath + File.pathSeparator + "class"
                + File.pathSeparator + remoteClassBO.getApp()
                + "_" + remoteClassBO.getClassName() + ".cl";
        String json = JSON.toJSONString(remoteClassBO);
        FileUtil.newOrReplaceFile(filePath,json.getBytes(charset));
    }

    @Override
    public void asyncSaveClasses(List<RemoteClassBO> remoteClassBOS){
        if(CollectionUtil.isEmpty(remoteClassBOS)){
            return;
        }
        asyncStoreExecutor.execute(() -> {
            for (RemoteClassBO remoteClassBO:remoteClassBOS){
                saveClass(remoteClassBO);
            }
        });
    }

    @Override
    public void asyncSaveClass(RemoteClassBO remoteClassBO) {
        asyncStoreExecutor.execute(() -> saveClass(remoteClassBO));
    }


}

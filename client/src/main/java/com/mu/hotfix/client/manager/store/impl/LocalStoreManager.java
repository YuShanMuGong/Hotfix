package com.mu.hotfix.client.manager.store.impl;

import com.mu.hotfix.client.constans.ConfigConstants;
import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.manager.store.ILocalStoreManager;
import com.mu.hotfix.common.util.FileUtil;
import com.mu.hotfix.common.util.StringUtil;

import java.io.File;

public class LocalStoreManager implements ILocalStoreManager {

    private final String basePath;

    public LocalStoreManager(){
        basePath = getStorePath();
    }

    private String getStorePath(){
        String path = System.getProperty(ConfigConstants.LOCAL_STORE_PATH);
        if(StringUtil.isEmpty(path)){
            // 默认使用项目的位置
            path = getClass().getResource("/").getPath();
        }
        return path;
    }

    @Override
    public byte[] getClass(String app, String className) {
        String filePath = basePath + File.pathSeparator + "class" + File.pathSeparator + app + "_" + className + ".cl";
        return FileUtil.readFile(filePath);
    }

    @Override
    public void putClass(String app, String className, byte[] contents) {
        if(StringUtil.isEmpty(app)
                || StringUtil.isEmpty(className)
                || contents == null || contents.length == 0){
            throw new HotFixClientException(ErrorCodes.PARAM_ILLEGAL,"putClass param is illegal");
        }
        String filePath = basePath + File.pathSeparator + "class" + File.pathSeparator + app + "_" + className + ".cl";
        FileUtil.newOrReplaceFile(filePath,contents);
    }


}

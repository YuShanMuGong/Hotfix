package com.mu.hotfix.client.manager.remote.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mu.hotfix.client.manager.config.IConfigManager;
import com.mu.hotfix.client.constans.ConfigConstants;
import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.constans.RemoteUrlConstants;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.manager.remote.IRemoteManager;
import com.mu.hotfix.client.util.OkHttpUtils;
import com.mu.hotfix.common.bo.RemoteClassBO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteManagerImpl implements IRemoteManager {

    private IConfigManager configAction;

    public RemoteManagerImpl(IConfigManager configAction){
        this.configAction = configAction;
    }

    @Override
    public RemoteClassBO getClass(String app, String className) {
        String url = configAction.getConfig(ConfigConstants.REMOTE_SRV_HOST + RemoteUrlConstants.FETCH_CLASS);
        Map<String,String> params = new HashMap<>();
        params.put("app",app);
        params.put("className",className);
        try {
            String json = OkHttpUtils.getJson(url,params);
            return JSON.parseObject(json,RemoteClassBO.class);
        } catch (Exception e) {
            throw new HotFixClientException(ErrorCodes.FETCH_CLASS_FAIL,e);
        }
    }

    @Override
    public List<RemoteClassBO> getAllClass(String app) {
        String url = configAction.getConfig(ConfigConstants.REMOTE_SRV_HOST + RemoteUrlConstants.FETCH_ALL_CLASS);
        Map<String,String> params = new HashMap<>();
        params.put("app",app);
        try {
            String json = OkHttpUtils.getJson(url,params);
            return JSONArray.parseArray(json,RemoteClassBO.class);
        } catch (Exception e) {
            throw new HotFixClientException(ErrorCodes.FETCH_CLASS_FAIL,e);
        }
    }

}

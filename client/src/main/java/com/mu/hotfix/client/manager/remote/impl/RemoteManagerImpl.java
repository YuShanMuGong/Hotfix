package com.mu.hotfix.client.manager.remote.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.constans.RemoteSrvUrlConstants;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.manager.remote.IRemoteManager;
import com.mu.hotfix.client.util.OkHttpUtils;
import com.mu.hotfix.common.BO.RemoteClassBO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteManagerImpl implements IRemoteManager {

    private String baseRemoteHost;

    public RemoteManagerImpl(String baseRemoteHost){
        this.baseRemoteHost = baseRemoteHost;
    }

    @Override
    public RemoteClassBO getClass(String app, String className) {
        String url = baseRemoteHost + RemoteSrvUrlConstants.FETCH_CLASS;
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
        String url = baseRemoteHost + RemoteSrvUrlConstants.FETCH_ALL_CLASS;
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

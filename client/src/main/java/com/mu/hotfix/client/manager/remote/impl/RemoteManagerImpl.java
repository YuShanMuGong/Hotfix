package com.mu.hotfix.client.manager.remote.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.manager.remote.IRemoteManager;
import com.mu.hotfix.client.util.OkHttpUtils;
import com.mu.hotfix.common.DTO.RemoteClassDTO;
import com.mu.hotfix.common.DTO.ResultDTO;
import com.mu.hotfix.common.constants.ErrorCodes;
import com.mu.hotfix.common.constants.RemoteSrvUrlConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteManagerImpl implements IRemoteManager {

    private String baseRemoteHost;

    public RemoteManagerImpl(String baseRemoteHost){
        this.baseRemoteHost = baseRemoteHost;
    }

    @Override
    public RemoteClassDTO getClass(String app, String className) {
        String url = baseRemoteHost + RemoteSrvUrlConstants.FETCH_CLASS;
        Map<String,String> params = new HashMap<>();
        params.put("app",app);
        params.put("className",className);
        try {
            String json = OkHttpUtils.getJson(url,params);
            return JSON.parseObject(json,RemoteClassDTO.class);
        } catch (Exception e) {
            throw new HotFixClientException(ErrorCodes.FETCH_CLASS_FAIL,e);
        }
    }

    @Override
    public List<RemoteClassDTO> getAllClass(String app) {
        String url = baseRemoteHost + RemoteSrvUrlConstants.FETCH_ALL_CLASS;
        Map<String,String> params = new HashMap<>();
        params.put("app",app);
        try {
            String json = OkHttpUtils.getJson(url,params);
            ResultDTO<List<RemoteClassDTO>> resultDTO = JSON.parseObject(json,new TypeReference<ResultDTO<List<RemoteClassDTO>>>(){});
            if(!resultDTO.isSuccess() || resultDTO.getContent() ==null){
                throw new HotFixClientException(ErrorCodes.FETCH_CLASS_FAIL,"request all classes fail ,res="+json);
            }
            return resultDTO.getContent();
        }catch (HotFixClientException he){
            throw he;
        }
        catch (Exception e) {
            throw new HotFixClientException(ErrorCodes.FETCH_CLASS_FAIL,e);
        }
    }

}

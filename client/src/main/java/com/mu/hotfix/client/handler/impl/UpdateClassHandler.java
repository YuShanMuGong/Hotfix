package com.mu.hotfix.client.handler.impl;

import com.alibaba.fastjson.JSON;
import com.mu.hotfix.client.core.HotFixClassLoader;
import com.mu.hotfix.client.handler.AbstractRequstHanlder;
import com.mu.hotfix.common.BO.RemoteClassBO;
import com.mu.hotfix.common.DTO.ResultDTO;

import java.util.Map;

public class UpdateClassHandler extends AbstractRequstHanlder<Boolean> {

    private HotFixClassLoader hotFixClassLoader;
    private static final String CLASS = "class";

    public UpdateClassHandler(HotFixClassLoader hotFixClassLoader){
        this.hotFixClassLoader = hotFixClassLoader;
    }

    @Override
    public ResultDTO<Boolean> handle(Map<String, String> params) {
        validParams(params,CLASS);
        RemoteClassBO remoteClass = JSON.parseObject(params.get(CLASS), RemoteClassBO.class);
        hotFixClassLoader.updateClass(remoteClass);
        return ResultDTO.success(Boolean.TRUE);
    }
}

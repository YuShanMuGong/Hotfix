package com.mu.hotfix.client.handler;

import com.mu.hotfix.common.DTO.ResultDTO;

import java.util.Map;

public interface IRequestHandler<T> {

    ResultDTO<T> handle(Map<String,String> params);

}

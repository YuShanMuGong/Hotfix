package com.mu.hotfix.client.srv;

import com.alibaba.fastjson.JSON;
import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.handler.IRequestHandler;
import com.mu.hotfix.client.util.ExceptionHandler;
import com.mu.hotfix.common.DTO.ResultDTO;
import fi.iki.elonen.NanoHTTPD;

import java.util.Map;


public class EmbeddableHttpSrv extends NanoHTTPD {

    private Map<String,IRequestHandler> requestHandlersMap;

    private static final int TIMEOUT = 5000;

    public EmbeddableHttpSrv(Map<String,IRequestHandler> requestHandlersMap , int port) {
        super(port);
        this.requestHandlersMap = requestHandlersMap;
    }

    @Override
    public Response serve(IHTTPSession session) {
        ResultDTO<?> resultDTO;
        try {
            String url = session.getUri();
            IRequestHandler requestHandler = requestHandlersMap.get(url);
            if(requestHandler == null){
                throw new HotFixClientException(ErrorCodes.NO_REQUEST_HANDLER_FAIL,"can not find requestHandler");
            }
            resultDTO = requestHandler.handle(session.getParms());
        }catch (Exception e){
            resultDTO = ExceptionHandler.handle(e);
        }
        return newFixedLengthResponse(JSON.toJSONString(resultDTO));
    }

    @Override
    public void start() {
        try {
            super.start(TIMEOUT,true);
        } catch (Exception e) {
            throw new HotFixClientException(ErrorCodes.LOCAL_SRV_START_FAIL,e);
        }
    }
}

package com.mu.hotfix.client.util;

import com.mu.hotfix.client.constans.ErrorCodes;
import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.common.DTO.ResultDTO;

public class ExceptionHandler {

    public static <T> ResultDTO<T> handle(Exception e){
        if(e instanceof HotFixClientException){
            HotFixClientException he = (HotFixClientException) e;
            return ResultDTO.fail(he.getCode(),he.getMsg());
        }
        return ResultDTO.fail(ErrorCodes.UN_KNOW_EXCEPTION,e.getMessage());
    }

}

package com.mu.hotfix.client.handler;


import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.common.constants.ErrorCodes;
import com.mu.hotfix.common.util.CollectionUtil;
import com.mu.hotfix.common.util.StringUtil;

import java.util.Map;

public abstract class AbstractRequestHandler<T> implements IRequestHandler<T> {

    protected void validParams(Map<String,String> params , String... mustKeys){
        if(mustKeys == null || mustKeys.length == 0){
            return;
        }
        if(CollectionUtil.isEmpty(params)){
            return;
        }
        for (String mustKey : mustKeys){
            if(StringUtil.isEmpty(params.get(mustKey))){
                throw new HotFixClientException(ErrorCodes.PARAM_ILLEGAL,"must key is empty ,key="+mustKey);
            }
        }
    }

}

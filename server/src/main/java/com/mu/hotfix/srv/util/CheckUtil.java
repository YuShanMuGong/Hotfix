package com.mu.hotfix.srv.util;

import com.mu.hotfix.common.constants.ErrorCodes;
import com.mu.hotfix.common.util.StringUtil;
import com.mu.hotfix.srv.exception.HotFixSrvException;

public class CheckUtil {

    public static void checkNotEmpty(String... args){
        for (String it : args){
            if(StringUtil.isEmpty(it)){
                throw new HotFixSrvException(ErrorCodes.PARAM_ILLEGAL,"param is null");
            }
        }
    }

}

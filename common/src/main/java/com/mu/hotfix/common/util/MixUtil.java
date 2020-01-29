package com.mu.hotfix.common.util;

import com.mu.hotfix.common.BO.RemoteClassBO;

public final class MixUtil {

    public static boolean isValid(RemoteClassBO remoteClassBO){
        return  remoteClassBO != null &&
                !StringUtil.isTrimEmpty(remoteClassBO.getClassName()) &&
                !StringUtil.isTrimEmpty(remoteClassBO.getApp()) &&
                !ByteArrayUtil.isEmpty(remoteClassBO.getContent());
    }

}

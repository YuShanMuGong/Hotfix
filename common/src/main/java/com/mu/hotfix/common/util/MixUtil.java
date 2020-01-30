package com.mu.hotfix.common.util;

import com.mu.hotfix.common.DTO.RemoteClassDTO;

public final class MixUtil {

    public static boolean isValid(RemoteClassDTO remoteClassDTO){
        return  remoteClassDTO != null &&
                !StringUtil.isTrimEmpty(remoteClassDTO.getClassName()) &&
                !StringUtil.isTrimEmpty(remoteClassDTO.getApp()) &&
                !ByteArrayUtil.isEmpty(remoteClassDTO.getContent());
    }

}

package com.mu.hotfix.client.converter;

import com.mu.hotfix.common.DTO.RemoteClassDTO;
import com.mu.hotfix.common.DTO.RemoteClassInfoDTO;

public class RemoteClassInfoDTOConverter {

    public static RemoteClassInfoDTO convert(RemoteClassDTO remoteClassDTO){
        if(remoteClassDTO == null){
            return null;
        }
        RemoteClassInfoDTO infoDTO = new RemoteClassInfoDTO();
        infoDTO.setApp(remoteClassDTO.getApp());
        infoDTO.setClassName(remoteClassDTO.getClassName());
        infoDTO.setVersion(remoteClassDTO.getVersion());
        return infoDTO;
    }

}

package com.mu.hotfix.srv.converter;

import com.mu.hotfix.common.DTO.RemoteClassDTO;
import com.mu.hotfix.srv.BO.RemoteClassBO;

public class RemoteClassDTOConverter {

    public static RemoteClassDTO converter(RemoteClassBO remoteClassBO){
        if (remoteClassBO == null){
            return null;
        }
        RemoteClassDTO dto = new RemoteClassDTO();
        dto.setApp(remoteClassBO.getApp());
        dto.setClassName(remoteClassBO.getClassName());
        dto.setContent(remoteClassBO.getContent());
        dto.setVersion(remoteClassBO.getVersion());
        return dto;
    }

}

package com.mu.hotfix.client.handler.impl;

import com.mu.hotfix.client.converter.RemoteClassInfoDTOConverter;
import com.mu.hotfix.client.core.IHotFixClientProcess;
import com.mu.hotfix.client.handler.AbstractRequestHandler;
import com.mu.hotfix.common.DTO.RemoteClassDTO;
import com.mu.hotfix.common.DTO.RemoteClassInfoDTO;
import com.mu.hotfix.common.DTO.ResultDTO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListLoadedClassHandler extends AbstractRequestHandler<List<RemoteClassInfoDTO>> {

    private IHotFixClientProcess hotFixClientProcess;

    public ListLoadedClassHandler(IHotFixClientProcess hotFixClientProcess) {
        this.hotFixClientProcess = hotFixClientProcess;
    }

    @Override
    public ResultDTO<List<RemoteClassInfoDTO>> handle(Map<String, String> params) {
        List<RemoteClassDTO> remoteClassDTOS = hotFixClientProcess.listLoadedClass();
        List<RemoteClassInfoDTO> infoDTOS = remoteClassDTOS
                .stream()
                .filter(Objects::nonNull)
                .map(RemoteClassInfoDTOConverter::convert)
                .collect(Collectors.toList());
        return ResultDTO.success(infoDTOS);
    }
}

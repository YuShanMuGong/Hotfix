package com.mu.hotfix.srv.process;

import com.google.common.collect.Lists;
import com.mu.hotfix.common.DTO.RemoteClassDTO;
import com.mu.hotfix.srv.BO.RemoteClassBO;
import com.mu.hotfix.srv.action.ClassStoreAction;
import com.mu.hotfix.srv.converter.RemoteClassDTOConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class FetchClassProcess {

    @Resource
    private ClassStoreAction classStoreAction;

    public RemoteClassDTO getRemoteClass(String app , String className){
        RemoteClassBO remoteClassBO = classStoreAction.getRemoteClass(app,className);
        return RemoteClassDTOConverter.converter(remoteClassBO);
    }

    public List<RemoteClassDTO> listRemoteClass(String app){
        return Lists.newArrayList(getRemoteClass(app,"com.mu.learn.test.Test"));
    }

}

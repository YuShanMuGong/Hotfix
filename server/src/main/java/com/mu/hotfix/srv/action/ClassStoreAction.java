package com.mu.hotfix.srv.action;

import com.mu.hotfix.common.util.FileUtil;
import com.mu.hotfix.srv.BO.RemoteClassBO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ClassStoreAction {

    @Value("${store.class.base.path}")
    private String storeBasePath;

    public RemoteClassBO getRemoteClass(String app,String className){
        byte[] bytes = FileUtil.readFile(storeBasePath + File.separator + app + "_" + className + ".cl");
        RemoteClassBO classBO = new RemoteClassBO();
        classBO.setApp(app);
        classBO.setClassName(className);
        classBO.setVersion("test");
        classBO.setContent(bytes);
        return classBO;
    }

}

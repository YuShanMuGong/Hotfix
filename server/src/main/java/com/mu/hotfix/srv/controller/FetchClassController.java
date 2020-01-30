package com.mu.hotfix.srv.controller;

import com.mu.hotfix.common.DTO.RemoteClassDTO;
import com.mu.hotfix.common.DTO.ResultDTO;
import com.mu.hotfix.srv.process.FetchClassProcess;
import com.mu.hotfix.srv.util.CheckUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/fetch")
public class FetchClassController {

    @Resource
    private FetchClassProcess fetchClassProcess;

    @RequestMapping("/class")
    public ResultDTO<RemoteClassDTO> fetchClass(String app , String className){
        CheckUtil.checkNotEmpty(app,className);
        return ResultDTO.success(fetchClassProcess.getRemoteClass(app,className));
    }

    @RequestMapping("/classes")
    public ResultDTO<List<RemoteClassDTO>> fetchClasses(String app){
        CheckUtil.checkNotEmpty(app);
        return ResultDTO.success(fetchClassProcess.listRemoteClass(app));
    }

}

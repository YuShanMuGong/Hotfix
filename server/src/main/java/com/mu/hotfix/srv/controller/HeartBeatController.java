package com.mu.hotfix.srv.controller;

import com.mu.hotfix.common.DTO.ResultDTO;
import com.mu.hotfix.common.constants.ErrorCodes;
import com.mu.hotfix.common.util.StringUtil;
import com.mu.hotfix.srv.exception.HotFixSrvException;
import com.mu.hotfix.srv.process.HeartBeatProcess;
import com.mu.hotfix.srv.util.NetworkUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/heartBeat")
public class HeartBeatController {

    @Resource
    private HeartBeatProcess heartBeatProcess;

    @RequestMapping("/clientPing")
    public ResultDTO<Boolean> clientPing(HttpServletRequest request , String app){
        if(StringUtil.isEmpty(app)){
            throw new HotFixSrvException(ErrorCodes.PARAM_ILLEGAL,"clientPing Request app必填");
        }
        String ip = NetworkUtil.getIP(request);
        if(StringUtil.isEmpty(ip)){
            throw new HotFixSrvException(ErrorCodes.UN_KNOW_EXCEPTION,"获取Client IP失败");
        }
        heartBeatProcess.clientPing(ip,app);
        return ResultDTO.success(Boolean.TRUE);
    }

}

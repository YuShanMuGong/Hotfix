package com.mu.hotfix.srv.process;

import com.mu.hotfix.srv.action.ClientStatsAction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class HeartBeatProcess {

    @Resource
    private ClientStatsAction clientStatsAction;

    public void clientPing(String clientIP, String app) {
        clientStatsAction.clientPing(clientIP,app,new Date());
    }

}

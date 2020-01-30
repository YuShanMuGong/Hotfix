package com.mu.hotfix.srv.action;

import com.mu.hotfix.common.util.DateUtil;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClientStatsAction {

    private ConcurrentHashMap<String, Date> clientIPMap;
    private ConcurrentHashMap<String, Set<String>> clientAppIPMap;

    public ClientStatsAction(){
        clientIPMap = new ConcurrentHashMap<>();
        clientAppIPMap = new ConcurrentHashMap<>();
    }

    public void clientPing(String ip , String app , Date time){
        clientIPMap.compute(ip,(key,oldTime) -> DateUtil.max(oldTime,time));
        clientAppIPMap.compute(app,(key,ips) -> {
            if(ips == null){
                ips = new HashSet<>();
            }
            ips.add(ip);
            return ips;
        });
    }

    public Map<String,Date> listAllClient(){
        return Collections.unmodifiableMap(clientIPMap);
    }

    public Map<String,Date> listClient(String app){
        Set<String> ips = clientAppIPMap.get(app);
        HashMap<String,Date> returnMaps = new HashMap<>(ips.size());
        for (String ip : ips){
            returnMaps.put(ip,clientIPMap.get(ip));
        }
        return returnMaps;
    }

}

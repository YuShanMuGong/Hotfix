package com.mu.hotfix.client.manager.config.impl;

import com.mu.hotfix.client.exception.HotFixClientException;
import com.mu.hotfix.client.manager.config.IConfigManager;
import com.mu.hotfix.common.constants.ErrorCodes;
import com.mu.hotfix.common.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static com.mu.hotfix.client.constants.ConfigConstants.*;

public class ConfigManagerImpl implements IConfigManager {

    private Map<String,String> configCache = new HashMap<>();

    public ConfigManagerImpl(String configPropertiesPath){
        // 先加载默认配置
        loadDefaultConfig();
        // 首先从配置文件中加载
        loadFromConfigFile(configPropertiesPath);
        // 然后再加载 系统的环境变量 环境变量设置的优先级高，会覆盖配置文件的配置
        loadFromSystemProperties();
    }

    private void loadDefaultConfig(){
        configCache.put(CONFIG_FILE_KEY,DEFAULT_CONFIG_FILE_PATH);
        configCache.put(CLIENT_SRV_PORT,CLIENT_DEFAULT_SRV_PORT);
    }

    private void loadFromSystemProperties() {

    }

    private void loadFromConfigFile(String configPropertiesPath){
        if(StringUtil.isTrimEmpty(configPropertiesPath)){
            return;
        }
        String path = getClass().getClassLoader().getResource("").getPath() + configPropertiesPath;
        File file = new File(path);
        if(!file.exists()){
            return;
        }
        if(!file.canRead()){
            throw new HotFixClientException(ErrorCodes.FILE_NO_ACCESS_ERROR,"path="+path+"can not read");
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
            Set<String> keys = properties.stringPropertyNames();
            for (String key : keys){
                configCache.put(key,properties.getProperty(key));
            }
        }catch (Exception e){
            throw new HotFixClientException(ErrorCodes.LOAD_CONFIG_ERROR,e);
        }
    }


    @Override
    public String getConfig(String key) {
        return configCache.get(key);
    }

}

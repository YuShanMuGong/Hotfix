package com.mu.hotfix.client.constants;

public class ConfigConstants {

    public static final String CONFIG_FILE_KEY = "config_path";

    public static final String DEFAULT_CONFIG_FILE_PATH = "hotfix_config.properties";

    public static final String REMOTE_SRV_HOST = "remote_srv_host";

    /**
     * 如果获取远程class失败，是否停止进程
     */
    public static final String FETCH_LOCAL_IF_REMOTE_FAIL = "fetch_local_if_remoteFail";

    /**
     * 本地存储缓存Class 的位置,绝对地址
     */
    public static final String LOCAL_STORE_PATH = "local_store_path";

    /**
     * 应用名称
     */
    public static final String APP_NAME = "app_name";

    /**
     * Client 提供服务的端口配置
     */
    public static final String CLIENT_SRV_PORT = "client_srv_port";

    /**
     * Client 提供服务的默认端口
     */
    public static final String CLIENT_DEFAULT_SRV_PORT = "9000";

}

package com.mu.hotfix.common.util;

import java.io.File;

public class PathUtil {

    public static String separatedEnd(String path){
        if(StringUtil.isEmpty(path)){
            return path;
        }
        if(path.trim().endsWith(File.separator)){
            return path.trim();
        }else{
            return path.trim() + File.separator;
        }
    }

    public static String separatedStart(String path){
        if(StringUtil.isEmpty(path)){
            return path;
        }
        if(path.trim().startsWith(File.separator)){
            return path.trim();
        }else{
            return File.separator + path.trim();
        }
    }

}

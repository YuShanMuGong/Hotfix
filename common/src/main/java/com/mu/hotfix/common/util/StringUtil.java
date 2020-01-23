package com.mu.hotfix.common.util;

public final class StringUtil {

    private StringUtil(){

    }

    public static boolean isEmpty(String str){
        return str != null && str.isEmpty();
    }

    public static boolean isTrimEmpty(String str){
        return str != null && str.trim().isEmpty();
    }

}

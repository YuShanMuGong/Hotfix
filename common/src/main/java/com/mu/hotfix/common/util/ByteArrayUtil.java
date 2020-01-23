package com.mu.hotfix.common.util;

public final class ByteArrayUtil {

    private ByteArrayUtil(){

    }

    public static boolean isEmpty(byte[] bytes){
        return bytes == null || bytes.length == 0;
    }

}

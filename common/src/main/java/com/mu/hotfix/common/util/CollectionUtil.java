package com.mu.hotfix.common.util;

import java.util.List;
import java.util.Map;

public final class CollectionUtil {

    public static boolean isEmpty(List<?> list){
        return list == null || list.size() == 0;
    }

    public static boolean isEmpty(Map<?,?> map){
        return map == null || map.size() == 0;
    }

}

package com.mu.hotfix.common.util;

import java.util.List;

public final class CollectionUtil {

    public static boolean isEmpty(List<?> list){
        return list == null || list.size() == 0;
    }

}

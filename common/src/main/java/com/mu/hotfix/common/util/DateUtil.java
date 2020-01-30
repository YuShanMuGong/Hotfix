package com.mu.hotfix.common.util;

import java.util.Date;

public class DateUtil {

    public static Date max(Date... dates){
        Date maxDate = null;
        for (Date date : dates){
            if(date == null){
                continue;
            }
            if(maxDate == null){
                maxDate = date;
            }else{
                if(maxDate.compareTo(date) < 0){
                    maxDate = date;
                }
            }
        }
        return maxDate;
    }

}

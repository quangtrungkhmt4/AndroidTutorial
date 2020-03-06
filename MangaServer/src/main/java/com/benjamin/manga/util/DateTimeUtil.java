package com.benjamin.manga.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static Long convertDate(long milisecond, String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return Long.parseLong(simpleDateFormat.format(new Date(milisecond)));
    }

    public static Long getCurrentTime(){
        return System.currentTimeMillis();
    }


}

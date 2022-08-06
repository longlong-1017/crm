package com.atlong.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: YunLong
 * @Date: 2022/7/22 20:34
 */
public class DateUtils {
    /**
     * 对指定的date对象进行格式化: yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr=simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 对指定的date对象进行格式化: yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=simpleDateFormat.format(date);
        return dateStr;
    }
    /**
     * 对指定的date对象进行格式化: HH:mm:ss
     * @param date
     * @return
     */
    public static String formatTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String dateStr=simpleDateFormat.format(date);
        return dateStr;
    }

}

package com.atlong.crm.commons.utils;

import java.util.UUID;

/**
 * @Author: YunLong
 * @Date: 2022/7/29 17:03
 */
public class UUIDUtils {
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return uuid;
    }
}

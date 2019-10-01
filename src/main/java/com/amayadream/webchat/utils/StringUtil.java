package com.amayadream.webchat.utils;

import java.util.UUID;

/**
 * FileName: RegisterController
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description:
 * History:
 */
public class StringUtil {

    public static String getGuid() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "").toLowerCase();
    }

}

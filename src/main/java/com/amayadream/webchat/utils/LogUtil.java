package com.amayadream.webchat.utils;

import com.amayadream.webchat.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileName: RegisterController
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description:
 * History:
 */
public class LogUtil {

    public Log setLog(String userid, String time, String type, String detail, String ip) {
        Log log = new Log();
        log.setUserid(userid);
        log.setTime(time);
        log.setType(type);
        log.setDetail(detail);
        log.setId(ip);
        return log;
    }

    public static final Logger getLogger(Object o) {
        Logger looger = LoggerFactory.getLogger(o.getClass());
        return looger;
    }

}

package com.amayadream.webchat.utils;

import java.util.HashMap;

/**
 * FileName: RegisterController
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description: 封装结果集
 * History:
 */
public class Result extends HashMap {
    public static final int SUCCESS = 0;
    public static final int ERROR = -1;

    public Result(int _result, String _msg) {
        this.put("result", _result);
        this.put("msg", _msg);
    }

    public static Result success(String _msg) {
        return new Result(SUCCESS, _msg);
    }

    public static Result error(String _msg) {
        return new Result(ERROR, _msg);
    }
}

package com.amayadream.webchat.service;

import com.amayadream.webchat.pojo.Log;

import java.util.List;

/**
 * FileName: ILogService
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description:
 * History:
 */
public interface ILogService {
    List<Log> selectAll(int page, int pageSize);

    List<Log> selectLogByname(String name, int page, int pageSize);

    int selectCount(int pageSize);

    int selectCountByname(String name, int pageSize);

    boolean insert(Log log);

    boolean delete(String id);

    boolean deleteThisUser(String name);

    boolean deleteAll();
}

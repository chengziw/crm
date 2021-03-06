package com.amayadream.webchat.mapping;

import com.amayadream.webchat.pojo.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: ILogDao
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description: 注册接口
 * History:
 */
@Service(value = "logDao")
public interface ILogDao {
    List<Log> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    List<Log> selectLogByname(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    Log selectCount();

    Log selectCountByname(@Param("name") String name);

    boolean insert(Log log);

    boolean delete(String id);

    boolean deleteThisUser(String name);

    boolean deleteAll();
}

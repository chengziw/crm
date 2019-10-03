package com.amayadream.webchat.serviceImpl;

import com.amayadream.webchat.mapping.ILogDao;
import com.amayadream.webchat.pojo.Log;
import com.amayadream.webchat.service.ILogService;
import com.amayadream.webchat.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: LogServiceImpl
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description:
 * History:
 */
@Service(value = "logService")
public class LogServiceImpl implements ILogService {

    @Resource
    private ILogDao logDao;

    @Override
    public List<Log> selectAll(int page, int pageSize) {
        return logDao.selectAll(page, pageSize);
    }

    @Override
    public List<Log> selectLogByname(String name, int page, int pageSize) {
        int start = 1;
        int end = pageSize;
        if (page != 1) {
            start = pageSize * (page - 1) + 1;
            end = pageSize * page;
        }
        return logDao.selectLogByname(name, start, end);
    }

    @Override
    public int selectCount(int pageSize) {
        int pageCount = Integer.parseInt(logDao.selectCount().getname());
        return pageCount % pageSize == 0 ? pageCount / pageSize : pageCount / pageSize + 1;
    }

    @Override
    public int selectCountByname(String name, int pageSize) {
        int pageCount = Integer.parseInt(logDao.selectCountByname(name).getname());
        return pageCount % pageSize == 0 ? pageCount / pageSize : pageCount / pageSize + 1;
    }


    @Override
    public boolean insert(Log log) {
        log.setId(StringUtil.getGuid());
        return logDao.insert(log);
    }

    @Override
    public boolean delete(String id) {
        return logDao.delete(id);
    }

    @Override
    public boolean deleteThisUser(String name) {
        return logDao.deleteThisUser(name);
    }

    @Override
    public boolean deleteAll() {
        return logDao.deleteAll();
    }
}

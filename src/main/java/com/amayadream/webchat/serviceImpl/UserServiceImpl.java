package com.amayadream.webchat.serviceImpl;

import com.amayadream.webchat.mapping.IUserDao;
import com.amayadream.webchat.pojo.User;
import com.amayadream.webchat.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: UserServiceImpl
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description:
 * History:
 */
@Service(value = "userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    @Override
    public List<User> selectAll(int page, int pageSize) {
        return userDao.selectAll(page, pageSize);
    }

    @Override
    public User selectUserByname(String name) {
        return userDao.selectUserByname(name);
    }

    @Override
    public int selectCount(int pageSize) {
        int pageCount = Integer.parseInt(userDao.selectCount().getName());
        return pageCount % pageSize == 0 ? pageCount / pageSize : pageCount / pageSize + 1;
    }

    @Override
    public boolean insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(String name) {
        return userDao.delete(name);
    }
}

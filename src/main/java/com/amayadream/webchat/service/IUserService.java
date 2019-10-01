package com.amayadream.webchat.service;

import com.amayadream.webchat.pojo.User;

import java.util.List;

/**
 * FileName: RegisterController
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description:
 * History:
 */
public interface IUserService {
    List<User> selectAll(int page, int pageSize);

    User selectUserByUserid(String userid);

    int selectCount(int pageSize);

    boolean insert(User user);

    boolean update(User user);

    boolean delete(String userid);
}

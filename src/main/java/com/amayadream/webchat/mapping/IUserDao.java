package com.amayadream.webchat.mapping;

import com.amayadream.webchat.pojo.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: RegisterController
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description: 注册接口
 * History:
 */
@Service(value = "userDao")
public interface IUserDao {
    List<User> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    User selectUserByUserid(String userid);

    User selectCount();

    boolean insert(User user);

    boolean update(User user);

    boolean delete(String userid);
}

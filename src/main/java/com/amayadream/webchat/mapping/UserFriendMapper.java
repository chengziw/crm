package com.amayadream.webchat.mapping;

import com.amayadream.webchat.dto.UserFriend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "UserFriendMapper")
public interface UserFriendMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFriend record);

    int insertSelective(UserFriend record);

    UserFriend selectByPrimaryKey(String id);

    UserFriend selectByName(@Param("userName") String userName, @Param("friendName") String friendName);

    int updateByPrimaryKeySelective(UserFriend record);

    int updateByPrimaryKey(UserFriend record);

    List<String> getAllList(String name);

    int offLine(@Param("userName") String userName);

    int onLine(@Param("userName") String userName);
}
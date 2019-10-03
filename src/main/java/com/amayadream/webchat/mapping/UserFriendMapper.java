package com.amayadream.webchat.mapping;

import com.amayadream.webchat.dto.UserFriend;

import java.util.List;

public interface UserFriendMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFriend record);

    int insertSelective(UserFriend record);

    UserFriend selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserFriend record);

    int updateByPrimaryKey(UserFriend record);

    List<String> getAllList();

    int offLine(String userName);

    int onLine(String userName);
}
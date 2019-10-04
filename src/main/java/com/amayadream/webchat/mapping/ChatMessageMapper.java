package com.amayadream.webchat.mapping;

import com.amayadream.webchat.dto.ChatMessage;

public interface ChatMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatMessage record);

    int insertSelective(ChatMessage record);

    ChatMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatMessage record);

    int updateByPrimaryKey(ChatMessage record);
}
package com.amayadream.webchat.mapping;

import com.amayadream.webchat.dto.ChatMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatMessage record);

    int insertSelective(ChatMessage record);

    ChatMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatMessage record);

    int updateByPrimaryKey(ChatMessage record);

    List<ChatMessage> getChatRecord(@Param("from") String from, @Param("to") String to);
}
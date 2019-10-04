package com.amayadream.webchat.utils;

import com.alibaba.fastjson.JSONObject;
import com.amayadream.webchat.dto.ChatMessage;
import com.amayadream.webchat.mapping.ChatMessageMapper;
import com.amayadream.webchat.pojo.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * FileName: ChatUtil
 * Author:  wangzicheng
 * Date:     2019/10/4 0004 15:11
 * Description: 聊天工具类
 * History:
 */
public class ChatUtil {

    public static final Logger LOGGER = LogUtil.getLogger(ChatUtil.class);

    @Autowired
    private static ChatMessageMapper chatMessageMapper = SpringUtil.getBean(ChatMessageMapper.class);

    public static boolean sentToOne(String _message, JSONObject message, String user) {
        LOGGER.info("ChatUtil.sentToOne _message:{}.message:{},user:{}", _message, message, user);
        ChatMessage chatMessage = getDTO(_message, message);
        chatMessage.setToUser(user);
        chatMessage.setFromUser(message.get("from").toString());
        chatMessage.setType("One");
        int n = chatMessageMapper.insertSelective(chatMessage);
        if (n == 0) {
            return false;
        }
        return true;
    }

    public static boolean sentToAll(String _message, JSONObject message, User user) {
        LOGGER.info("ChatUtil.sentToAll _message:{}.message:{},user:{}", _message, message, user);
        ChatMessage chatMessage = getDTO(_message, message);
        chatMessage.setToUser(user.getName());
        chatMessage.setFromUser(message.get("from").toString());
        chatMessage.setType("All");
        int n = chatMessageMapper.insertSelective(chatMessage);
        if (n == 0) {
            return false;
        }
        return true;
    }


    public static ChatMessage getDTO(String _message, JSONObject message) {
        LOGGER.info("ChatUtil.getDTO _message:{}.message:{}", _message, message);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMsgId(UUID.randomUUID().toString());
        chatMessage.setTimestamp(new Date());
        chatMessage.setUrl("");
        chatMessage.setMsg(message.get("content").toString());
        chatMessage.setDirection(_message);
        return chatMessage;
    }


}

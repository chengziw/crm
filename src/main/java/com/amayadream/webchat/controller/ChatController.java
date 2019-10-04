package com.amayadream.webchat.controller;

import com.alibaba.fastjson.JSONObject;
import com.amayadream.webchat.dto.ChatMessage;
import com.amayadream.webchat.mapping.ChatMessageMapper;
import com.amayadream.webchat.utils.LogUtil;
import com.amayadream.webchat.utils.TimeUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: ChatController
 * Author:  wangzicheng
 * Date:     2019/10/4 0004 16:22
 * Description: 聊天
 * History:
 */
@Controller
@RequestMapping(value = "/chat")
public class ChatController {
    private static final Logger LOGGER = LogUtil.getLogger(ChatController.class);

    @Resource
    private ChatMessageMapper chatMessageMapper;

    @ResponseBody
    @RequestMapping(value = "/chatRecord", method = RequestMethod.POST)
    public JSONObject chatRecord(@RequestBody ChatMessage chatMessage) {
        LOGGER.info("ChatController.chatRecord chatMessage:{}", chatMessage);
        List<ChatMessage> list = chatMessageMapper.getChatRecord(chatMessage.getFromUser(), chatMessage.getToUser());
        List<String> messageList = new ArrayList<>();
        for (ChatMessage dto : list) {
            JSONObject message = new JSONObject();
            message.put("from", dto.getFromUser());
            message.put("to", dto.getToUser());
            message.put("content", dto.getMsg());
            message.put("time", TimeUtils.getYyyymmddhhmmss(dto.getTimestamp()));
            String temp = JSONObject.toJSONString(message);
            messageList.add(temp);
        }
        JSONObject result = new JSONObject();
        result.put("resultList", messageList);
        return result;
    }

}

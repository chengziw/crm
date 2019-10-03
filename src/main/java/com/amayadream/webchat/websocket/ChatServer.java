package com.amayadream.webchat.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amayadream.webchat.mapping.UserFriendMapper;
import com.amayadream.webchat.utils.LogUtil;
import com.amayadream.webchat.utils.SpringUtil;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * FileName: ChatServer
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description: scoket应用
 * History:
 */
@ServerEndpoint(value = "/chatServer", configurator = HttpSessionConfigurator.class)
public class ChatServer {

    private static final Logger LOGGER = LogUtil.getLogger(ChatServer.class);

    private UserFriendMapper userFriendMapper = SpringUtil.getBean(UserFriendMapper.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<ChatServer> webSocketSet = new CopyOnWriteArraySet<ChatServer>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 用户名
     */
    private String name;
    /**
     * request的session
     */
    private HttpSession httpSession;

    /**
     * 在线列表,记录用户名称
     */
    private static List list = new ArrayList<>();
    /**
     * 所有好友列表,记录用户名称
     */
    private static List Alllist = new ArrayList<>();
    /**
     * 用户名和websocket的session绑定的路由表
     */
    private static Map routetab = new HashMap<>();

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        LOGGER.info("ChatServer.onOpen->session:{},config:{}", session, config);
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //在线数加1;
        addOnlineCount();
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        //获取当前用户
        this.name = (String) httpSession.getAttribute("name");
        //将用户名加入在线列表
        list.add(name);
        userFriendMapper.onLine(name);
        //将用户名和session绑定到路由表
        routetab.put(name, session);
        List<String> friends = userFriendMapper.getAllList(name);
        if (null == friends) {
            friends = new ArrayList<>();
        }
        String message = getMessage("[" + name + "]加入聊天室,当前在线人数为" + getOnlineCount() + "位", "notice", list, friends);
        LOGGER.info("ChatServer.onOpen->message:{}", message);
        //广播
        broadcast(message);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        LOGGER.info("ChatServer.onOpen->onClose->start");
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        //从在线列表移除这个用户
        list.remove(name);
        userFriendMapper.offLine(name);
        routetab.remove(name);
        List<String> friends = userFriendMapper.getAllList(name);
        if (null == friends) {
            friends = new ArrayList<>();
        }
        String message = getMessage("[" + name + "]离开了聊天室,当前在线人数为" + getOnlineCount() + "位", "notice", list, friends);
        //广播
        broadcast(message);
    }

    /**
     * 接收客户端的message,判断是否有接收人而选择进行广播还是指定发送
     *
     * @param _message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String _message) {
        LOGGER.info("ChatServer.onMessage->_message:{}", _message);
        JSONObject chat = JSON.parseObject(_message);
        JSONObject message = JSON.parseObject(chat.get("message").toString());
        //如果to为空,则广播;如果不为空,则对指定的用户发送消息
        if (message.get("to") == null || message.get("to").equals("")) {
            broadcast(_message);
        } else {
            String[] userlist = message.get("to").toString().split(",");
            //发送给自己,这个别忘了
            singleSend(_message, (Session) routetab.get(message.get("from")));
            for (String user : userlist) {
                if (!user.equals(message.get("from"))) {
                    //分别发送给每个指定用户
                    singleSend(_message, (Session) routetab.get(user));
                }
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        LOGGER.error("ChatServer.onError->error:{}", error.getMessage());
    }

    /**
     * 广播消息
     *
     * @param message
     */
    public void broadcast(String message) {
        LOGGER.info("ChatServer.broadcast->message:{}", message);
        for (ChatServer chat : webSocketSet) {
            try {
                chat.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 对特定用户发送消息
     *
     * @param message
     * @param session
     */
    public void singleSend(String message, Session session) {
        LOGGER.info("ChatServer.singleSend->message:{},session:{}", message, session);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            LOGGER.error("ChatServer.singleSend->message:{},session:{}", e.getMessage());
        }
    }

    /**
     * 组装返回给前台的消息
     *
     * @param message 交互信息
     * @param type    信息类型
     * @param list    在线列表
     * @return
     */
    public String getMessage(String message, String type, List list, List alllist) {
        LOGGER.info("ChatServer.getMessage->message:{},session:{},list:{},alllist:{}", message, type, list, alllist);
        JSONObject member = new JSONObject();
        member.put("message", message);
        member.put("type", type);
        member.put("list", list);
        member.put("Alllist", alllist);
        return member.toString();
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void addOnlineCount() {
        ChatServer.onlineCount++;
    }

    public void subOnlineCount() {
        ChatServer.onlineCount--;
    }
}

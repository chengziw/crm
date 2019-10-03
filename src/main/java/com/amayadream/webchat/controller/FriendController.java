package com.amayadream.webchat.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.amayadream.webchat.dto.UserFriend;
import com.amayadream.webchat.mapping.IUserDao;
import com.amayadream.webchat.mapping.UserFriendMapper;
import com.amayadream.webchat.pojo.User;
import com.amayadream.webchat.utils.LogUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import java.util.UUID;

/**
 * FileName: FriendController
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description: 登录接口
 * History:
 */
@Controller
@RequestMapping(value = "/friend")
public class FriendController extends HttpServlet {
    private static final Logger LOGGER = LogUtil.getLogger(FriendController.class);

    @Resource
    private IUserDao iUserDao;
    @Resource
    private UserFriendMapper userFriendMapper;

    @ResponseBody
    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    public JSONObject addFriend(@RequestBody UserFriend userFriendParam) {
        JSONObject jsonObject = new JSONObject();
        String friendName = userFriendParam.getFriendName();
        String userName = userFriendParam.getUserName();
        LOGGER.info("FriendController.addFriend friendName:{},userName:{}", friendName, userName);
        if (StringUtils.isEmpty(friendName) || StringUtils.isEmpty(userName)) {
            jsonObject.put("mess", "参数为空");
            return jsonObject;
        }
        User user = iUserDao.selectUserByname(friendName);
        User userF = iUserDao.selectUserByname(userName);
        if (null == user) {
            jsonObject.put("mess", "用户不存在");
            return jsonObject;
        }
        UserFriend userFriend = userFriendMapper.selectByName(friendName, userName);
        if (null == userFriend) {
            userFriend = new UserFriend();
            userFriend.setId(UUID.randomUUID().toString());
            userFriend.setUserId(userF.getId());
            userFriend.setFriendName(friendName);
            userFriend.setFriendId(user.getId());
            userFriend.setUserName(userName);
            userFriend.setOnLine((byte) 1);
            userFriend.setStatus((byte) 0);
            userFriend.setLogicalDel((byte) 0);
            userFriend.setFriendId(user.getId());
            userFriend.setFriendName(user.getName());
            int n = userFriendMapper.insertSelective(userFriend);
            if (n > 0) {
                jsonObject.put("mess", "添加成功");
            } else {
                jsonObject.put("mess", "添加失败");
            }
            return jsonObject;
        }
        jsonObject.put("mess", "好友已存在");
        return jsonObject;
    }

}

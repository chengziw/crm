package com.amayadream.webchat.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.amayadream.webchat.mapping.IUserDao;
import com.amayadream.webchat.pojo.User;
import com.amayadream.webchat.service.ILogService;
import com.amayadream.webchat.service.IUserService;
import com.amayadream.webchat.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

/**
 * FileName: LoginController
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description: 登录接口
 * History:
 */
@Controller
@RequestMapping(value = "/user")
public class LoginController {

    @Resource
    private IUserService userService;

    @Resource
    private ILogService logService;

    @Resource
    private IUserDao iUserDao;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String name, String password, HttpSession session, RedirectAttributes attributes,
                        WordDefined defined, CommonDate date, LogUtil logUtil, NetUtil netUtil, HttpServletRequest request) {
        User user = userService.selectUserByname(name);
        if (user == null) {
            attributes.addFlashAttribute("error", defined.LOGIN_name_ERROR);
            return "redirect:/user/login";
        } else {
            if (!user.getPassword().equals(password)) {
                attributes.addFlashAttribute("error", defined.LOGIN_PASSWORD_ERROR);
                return "redirect:/user/login";
            } else {
                if (user.getStatus() != 1) {
                    attributes.addFlashAttribute("error", defined.LOGIN_name_DISABLED);
                    return "redirect:/user/login";
                } else {
                    logService.insert(logUtil.setLog(name, date.getTime24(), defined.LOG_TYPE_LOGIN, defined.LOG_DETAIL_USER_LOGIN, netUtil.getIpAddress(request)));
                    session.setAttribute("name", name);
                    session.setAttribute("login_status", true);
                    user.setLasttime(date.getTime24());
                    userService.update(user);
                    attributes.addFlashAttribute("message", defined.LOGIN_SUCCESS);
                    return "redirect:/chat";
                }
            }
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, RedirectAttributes attributes, WordDefined defined) {
        session.removeAttribute("name");
        session.removeAttribute("login_status");
        attributes.addFlashAttribute("message", defined.LOGOUT_SUCCESS);
        return "redirect:/user/login";
    }

    @RequestMapping(value = "/register")
    public String register(String name, String password, HttpSession session, RedirectAttributes attributes,
                           WordDefined defined, CommonDate date, LogUtil logUtil, NetUtil netUtil, HttpServletRequest request) {
        User user = userService.selectUserByname(name);
        if (user != null) {
            attributes.addFlashAttribute("error", defined.name_IS_USED);
            return "redirect:/user/login";
        } else {
            if (StringUtils.isNotEmpty(user.getName()) && StringUtils.isNotEmpty(password)) {
                user.setId(UUID.randomUUID().toString());
                user.setNickname(name);
                user.setSex(0);
                user.setAge(0);
                user.setProfilehead("upload/" + name + "/" + name + ".jpg");
                user.setStatus(1);
                user.setFirsttime(TimeUtils.getYyyymmddhhmmss(new Date()));
                iUserDao.insert(user);
            }
            attributes.addFlashAttribute("message", defined.REGISTER_SUCCESS);
            return "redirect:/user/login";
        }
    }
}

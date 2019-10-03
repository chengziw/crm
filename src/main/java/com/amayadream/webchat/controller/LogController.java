package com.amayadream.webchat.controller;

import com.amayadream.webchat.pojo.Log;
import com.amayadream.webchat.service.ILogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: LogController
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description: 日志记录接口
 * History:
 */
@Controller
@RequestMapping(value = "")
public class LogController {

    @Resource
    private ILogService logService;

    @RequestMapping(value = "{name}/log")
    public ModelAndView selectAll(@PathVariable("name") String name, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;
        ModelAndView view = new ModelAndView("log");
        List<Log> list = logService.selectLogByname(name, page, pageSize);
        int count = logService.selectCountByname(name, pageSize);
        view.addObject("list", list);
        view.addObject("count", count);
        return view;
    }

}

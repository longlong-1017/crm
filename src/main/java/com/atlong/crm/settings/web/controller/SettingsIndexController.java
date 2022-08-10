package com.atlong.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: YunLong
 * @Date: 2022/8/10 16:15
 */
@Controller
public class SettingsIndexController {
    @RequestMapping("/settings/index.do")
    public String index(){
        //跳转页面
        return "settings/index";
    }
}

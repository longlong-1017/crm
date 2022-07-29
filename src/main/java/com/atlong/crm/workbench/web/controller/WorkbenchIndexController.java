package com.atlong.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: YunLong
 * @Date: 2022/7/22 21:39
 */
@Controller
public class WorkbenchIndexController {
    @RequestMapping("/workbench/index.do")
    public String index(){
        //跳转主页面
        return "workbench/index";
    }
}

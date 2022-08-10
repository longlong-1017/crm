package com.atlong.crm.settings.web.controller;

import com.atlong.crm.settings.domian.DictionaryType;
import com.atlong.crm.settings.service.DictionaryTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/10 16:36
 */
@Controller
public class DictionaryController {
    @RequestMapping("/settings/dictionary/index.do")
    public String index(){
        return "settings/dictionary/index";
    }
}

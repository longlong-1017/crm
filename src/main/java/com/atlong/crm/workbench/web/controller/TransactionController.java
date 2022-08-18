package com.atlong.crm.workbench.web.controller;

import com.atlong.crm.settings.domain.User;
import com.atlong.crm.settings.domian.DictionaryValue;
import com.atlong.crm.settings.service.DictionaryValueService;
import com.atlong.crm.settings.service.UserService;
import com.atlong.crm.workbench.mapper.CustomerMapper;
import com.atlong.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @Author: YunLong
 * @Date: 2022/8/18 16:36
 */
@Controller
public class TransactionController {
    @Autowired
    private DictionaryValueService dictionaryValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/workbench/transaction/index.do")
    public ModelAndView index(ModelAndView mv){
        List<DictionaryValue> stageList = dictionaryValueService.queryDicValuesByTypeCode("stage");
        List<DictionaryValue> transactionTypeList = dictionaryValueService.queryDicValuesByTypeCode("transactionType");
        List<DictionaryValue> sourceList = dictionaryValueService.queryDicValuesByTypeCode("source");
        mv.addObject("stageList",stageList);
        mv.addObject("transactionTypeList",transactionTypeList);
        mv.addObject("sourceList",sourceList);
        mv.setViewName("workbench/transaction/index");
        return mv;
    }

    @RequestMapping("/workbench/transaction/toSave.do")
    public ModelAndView toSave(ModelAndView mv){
        List<User> users = userService.queryAllUser();
        List<DictionaryValue> transactionTypeList = dictionaryValueService.queryDicValuesByTypeCode("transactionType");
        List<DictionaryValue> sourceList = dictionaryValueService.queryDicValuesByTypeCode("source");
        List<DictionaryValue> stageList = dictionaryValueService.queryDicValuesByTypeCode("stage");
        mv.addObject("users",users);
        mv.addObject("transactionTypeList",transactionTypeList);
        mv.addObject("sourceList",sourceList);
        mv.addObject("stageList",stageList);
        mv.setViewName("workbench/transaction/save");
        return mv;
    }

    @RequestMapping("/workbench/transaction/getPossibilityByStage.do")
    public @ResponseBody Object getPossibilityByStage(String stageValue){
        /*????????为什么ResourceBundle报错 properties在web项目中load文件绝对路径是啥???
        * */
       /* ResourceBundle resourceBundle = ResourceBundle.getBundle("possibility");
        String possibility = resourceBundle.getString(stageValue);*/
        String possibility="";
        try {
            Properties properties = new Properties();
            //
            properties.load(new FileReader("F:\\IdeaProjects\\crm\\target\\classes\\possibility.properties"));
            possibility= properties.getProperty(stageValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return possibility;
    }

    @RequestMapping("/workbench/transaction/queryCustomerNameByName.do")
    public @ResponseBody Object queryCustomerNameByName(String customerName){
        List<String> customerNameList = customerService.queryCustomerNameByName(customerName);
        return customerNameList;
    }

}

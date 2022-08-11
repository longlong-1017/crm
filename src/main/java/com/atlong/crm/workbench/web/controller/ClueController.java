package com.atlong.crm.workbench.web.controller;

import com.atlong.crm.commons.constant.Constant;
import com.atlong.crm.commons.domain.ReturnObject;
import com.atlong.crm.commons.utils.DateUtils;
import com.atlong.crm.commons.utils.UUIDUtils;
import com.atlong.crm.settings.domain.User;
import com.atlong.crm.settings.domian.DictionaryValue;
import com.atlong.crm.settings.service.DictionaryValueService;
import com.atlong.crm.settings.service.UserService;
import com.atlong.crm.workbench.domian.Clue;
import com.atlong.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: YunLong
 * @Date: 2022/8/11 13:41
 */
@Controller
public class ClueController {
    @Autowired
    private ClueService clueService;
    @Autowired
    private UserService userService;
    @Autowired
    private DictionaryValueService dictionaryValueService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request) {
        List<User> users = userService.queryAllUser();
        List<DictionaryValue> appellationList = dictionaryValueService.queryDicValuesByTypeCode("appellation");
        List<DictionaryValue> clueStateList = dictionaryValueService.queryDicValuesByTypeCode("clueState");
        List<DictionaryValue> sourceList = dictionaryValueService.queryDicValuesByTypeCode("source");
        request.setAttribute("users", users);
        request.setAttribute("appellationList", appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/createClue.do")
    public @ResponseBody Object createClue(Clue clue, HttpSession session) {
        ReturnObject returnObject=new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formatDate(new Date()));
        try {
            int ret = clueService.saveClue(clue);
            if (ret==1){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后");
            }
        } catch (Exception e) {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后");
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/queryClueByConditionForPage.do")
    public @ResponseBody Object queryClueByConditionForPage(String fullname,String company,String phone,String source,String owner,String state,String mphone,int pageNo,int pageSize){
        Map<String,Object> map=new HashMap<>();
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("phone",phone);
        map.put("source",source);
        map.put("owner",owner);
        map.put("state",state);
        map.put("mphone",mphone);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Clue> clueList = clueService.queryClueByConditionForPage(map);
        int totalRows=clueService.queryCountOfClueByConditionForPage(map);
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("clueList", clueList);
        retMap.put("totalRows", totalRows);
        return retMap;
    }
}

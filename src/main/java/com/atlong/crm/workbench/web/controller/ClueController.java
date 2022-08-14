package com.atlong.crm.workbench.web.controller;

import com.atlong.crm.commons.constant.Constant;
import com.atlong.crm.commons.domain.ReturnObject;
import com.atlong.crm.commons.utils.DateUtils;
import com.atlong.crm.commons.utils.UUIDUtils;
import com.atlong.crm.settings.domain.User;
import com.atlong.crm.settings.domian.DictionaryValue;
import com.atlong.crm.settings.service.DictionaryValueService;
import com.atlong.crm.settings.service.UserService;
import com.atlong.crm.workbench.domian.Activity;
import com.atlong.crm.workbench.domian.Clue;
import com.atlong.crm.workbench.domian.ClueActivityRelation;
import com.atlong.crm.workbench.domian.ClueRemark;
import com.atlong.crm.workbench.service.ActivityService;
import com.atlong.crm.workbench.service.ClueActivityRelationService;
import com.atlong.crm.workbench.service.ClueRemarkService;
import com.atlong.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    @Autowired
    private ClueRemarkService clueRemarkService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request) {
        List<User> users = userService.queryAllUser();
        List<DictionaryValue> appellationList = dictionaryValueService.queryDicValuesByTypeCode("appellation");
        List<DictionaryValue> clueStateList = dictionaryValueService.queryDicValuesByTypeCode("clueState");
        List<DictionaryValue> sourceList = dictionaryValueService.queryDicValuesByTypeCode("source");
        request.setAttribute("users", users);
        request.setAttribute("appellationList", appellationList);
        request.setAttribute("clueStateList", clueStateList);
        request.setAttribute("sourceList", sourceList);
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/createClue.do")
    public @ResponseBody Object createClue(Clue clue, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formatDate(new Date()));
        try {
            int ret = clueService.saveClue(clue);
            if (ret == 1) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
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
    public @ResponseBody Object queryClueByConditionForPage(String fullname, String company, String phone, String source, String owner, String state, String mphone, int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("fullname", fullname);
        map.put("company", company);
        map.put("phone", phone);
        map.put("source", source);
        map.put("owner", owner);
        map.put("state", state);
        map.put("mphone", mphone);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        List<Clue> clueList = clueService.queryClueByConditionForPage(map);
        int totalRows = clueService.queryCountOfClueByConditionForPage(map);
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("clueList", clueList);
        retMap.put("totalRows", totalRows);
        return retMap;
    }

    @RequestMapping("/workbench/clue/detailClue.do")
    public String detailClue(String id, HttpServletRequest request) {
        Clue clue = clueService.queryClueById(id);
        List<ClueRemark> clueRemarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);
        request.setAttribute("clue", clue);
        request.setAttribute("clueRemarkList", clueRemarkList);
        request.setAttribute("activityList", activityList);
        return "workbench/clue/detail";
    }

    @RequestMapping("/workbench/clue/queryActivityForDetailByNameClueId.do")
    public @ResponseBody Object queryActivityForDetailByNameClueId(String clueId, String activityName) {
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        //调用service方法查询市场活动
        List<Activity> activityList = activityService.queryActivityForDetailByNameClueId(map);
        return activityList;
    }

    @RequestMapping("/workbench/clue/saveBound.do")
    public @ResponseBody Object saveBound(@RequestParam("activityId") String[] activityIds, String clueId) {
        List<ClueActivityRelation> clueActivityRelations = new ArrayList<>();
        //封装数据
        ClueActivityRelation clueActivityRelation=null;
        for (String activityId : activityIds) {
            clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtils.getUUID());
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setActivityId(activityId);
            clueActivityRelations.add(clueActivityRelation);
        }
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = clueActivityRelationService.saveBoundByActivityIdsAndClueId(clueActivityRelations);
            if (ret > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                //根据id查询新关联的市场活动传入前端进行展示
                List<Activity> activityList=activityService.queryActivitiesForBoundByIds(activityIds);
                returnObject.setRetData(activityList);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        } catch (Exception e) {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
            e.printStackTrace();
        }
        return returnObject;
    }
}

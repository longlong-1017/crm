package com.atlong.crm.workbench.web.controller;

import com.atlong.crm.commons.constant.Constant;
import com.atlong.crm.commons.domain.ReturnObject;
import com.atlong.crm.commons.utils.ActivitiesWorkbookUtils;
import com.atlong.crm.commons.utils.DateUtils;
import com.atlong.crm.commons.utils.HSSFUtils;
import com.atlong.crm.commons.utils.UUIDUtils;
import com.atlong.crm.settings.domain.User;
import com.atlong.crm.settings.service.UserService;
import com.atlong.crm.workbench.domian.Activity;
import com.atlong.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @Author: YunLong
 * @Date: 2022/7/27 21:13
 */
@Controller
public class ActivityController {
    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request) {
        List<User> users = userService.queryAllUser();
        request.setAttribute("users", users);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity, HttpSession session) {
        //从session中获取创建者
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = activityService.saveActivity(activity);
            if (result > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试...");
            }
        } catch (Exception e) {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    public @ResponseBody Object queryActivityByConditionForPage(String name, String owner, String startDate, String endDate, int pageNo, int pageSize) {
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        //调用service层方法，查询数据
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        //封装相应信息
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("activityList", activityList);
        retMap.put("totalRows", totalRows);
        return retMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    public @ResponseBody Object deleteActivityByIds(String[] ids) {
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = activityService.deleteActivityByIds(ids);
            if (result > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试...");
            }

        } catch (Exception e) {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    public @ResponseBody Object queryActivityById(String id) {
        Activity activity = activityService.queryActivityById(id);
        return activity;
    }

    @RequestMapping("workbench/activity/editActivity.do")
    public @ResponseBody ReturnObject editActivity(Activity activity, HttpSession session) {
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        activity.setEditBy(user.getName());
        activity.setEditTime(DateUtils.formatDate(new Date()));
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = activityService.updateActivity(activity);
            if (result == 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙,请稍后重试");
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }
        } catch (Exception e) {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙,请稍后重试");
            throw new RuntimeException(e);
        }
        return returnObject;
    }

    @RequestMapping("workbench/activity/exportAllActivities.do")
    public void exportAllActivities(HttpServletResponse response) throws Exception {
        //查询所有的市场活动
        List<Activity> activities = activityService.queryAllActivities();
        HSSFWorkbook wb = ActivitiesWorkbookUtils.getWorkbook(activities);
        //把生成的excel文件下载到客户端
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=activities.xls");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        wb.close();
        out.flush();
    }

    @RequestMapping("workbench/activity/exportSelectedActivities.do")
    public void exportSelectedActivities(@RequestParam("id") String[] ids, HttpServletResponse response) throws Exception {
        List<Activity> activities = activityService.queryActivitiesByIds(ids);
        HSSFWorkbook wb = ActivitiesWorkbookUtils.getWorkbook(activities);
        //把生成的excel文件下载到客户端
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=activities.xls");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        wb.close();
        out.flush();
    }

    @RequestMapping("workbench/activity/downloadActivityFile.do")
    public void downloadActivityFile(HttpServletResponse response) throws Exception {
        List<User> users = userService.queryAllActAndName();
        HSSFWorkbook wb = ActivitiesWorkbookUtils.getMouldWorkbook(users);
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=ActivityMouldFile.xls");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        wb.close();
        out.flush();
    }

    @RequestMapping("workbench/activity/importActivities.do")
    public @ResponseBody ReturnObject importActivities(MultipartFile activityFile, HttpSession session) throws Exception {
        ReturnObject returnObject = new ReturnObject();
        User createUser = (User) session.getAttribute(Constant.SESSION_USER);
        List<User> users = userService.queryAllActAndName();
        Map<String, User> actUsersMap = new HashMap<>();
        for (User user : users) {
            actUsersMap.put(user.getLoginAct(), user);
        }
        Map<String, User> nameUsrMap = new HashMap<>();
        for (User user : users) {
            nameUsrMap.put(user.getName(), user);
        }
        InputStream is = activityFile.getInputStream();
        HSSFWorkbook wb = new HSSFWorkbook(is);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = null;
        HSSFCell cell = null;
        Activity activity = null;
        List<Activity> activityList = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()：最后一行的下标
            row = sheet.getRow(i);//行的下标，下标从0开始，依次增加
            activity = new Activity();
            activity.setId(UUIDUtils.getUUID());
            activity.setCreateTime(DateUtils.formatDate(new Date()));
            activity.setCreateBy(createUser.getId());
            //row.getLastCellNum():最后一列的下标+1
            for (int j = 0; j < row.getLastCellNum() - 1; j++) {
                //根据row获取HSSFCell对象，封装了一列的所有信息
                cell = row.getCell(j);//列的下标，下标从0开始，依次增加
                //获取列中的数据
                String cellValue = HSSFUtils.getCellValueForStr(cell);
                if (j == 0) {
                    cell = row.getCell(row.getLastCellNum() - 1);
                    cellValue = HSSFUtils.getCellValueForStr(cell);
                    if ("".equals(cellValue) || cellValue == null) {//使用name找ID
                        cell = row.getCell(j);//列的下标，下标从0开始，依次增加
                        //获取列中的数据
                        cellValue = HSSFUtils.getCellValueForStr(cell);
                        activity.setOwner(nameUsrMap.get(cellValue).getId());
                    } else { //出错
                        cell = row.getCell(row.getLastCellNum() - 1);//列的下标，下标从0开始，依次增加
                        //获取列中的数据
                        cellValue = HSSFUtils.getCellValueForStr(cell);
                        activity.setOwner(actUsersMap.get(cellValue).getId());
                    }
                } else if (j == 1) {
                    activity.setName(cellValue);
                } else if (j == 2) {
                    if (cellValue.contains("/")) {
                        cellValue = cellValue.replaceAll("/", "-");
                    }
                    activity.setStartDate(cellValue);
                } else if (j == 3) {
                    if (cellValue.contains("/")) {
                        cellValue = cellValue.replaceAll("/", "-");
                    }
                    activity.setEndDate(cellValue);
                } else if (j == 4) {
                    activity.setCost(cellValue);
                } else if (j == 5) {
                    activity.setDescription(cellValue);
                }
            }
            activityList.add(activity);
        }
        try {
            //调用service层方法，保存市场活动
            int ret = activityService.addActivities(activityList);

            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(ret);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }

}

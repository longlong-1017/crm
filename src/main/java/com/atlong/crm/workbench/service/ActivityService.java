package com.atlong.crm.workbench.service;


import com.atlong.crm.workbench.domian.Activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: YunLong
 * @Date: 2022/7/29 16:51
 */
public interface ActivityService {
    int saveActivity(Activity activity);
    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);

    int queryCountOfActivityByCondition(Map<String,Object> map);

    int deleteActivityByIds(String[] ids);

    Activity queryActivityById(String id);

    int updateActivity(Activity activity);

    List<Activity> queryAllActivities();

    List<Activity> queryActivitiesByIds(String[] ids);
    int addActivities(List<Activity> activities);

    Activity queryActivityForDetailById(String id);

    List<Activity> queryActivityForDetailByClueId(String id);

    List<Activity> queryActivityForDetailByNameClueId(Map<String,Object> map);

    List<Activity> queryActivitiesForBoundByIds(String[] activityIds);

    List<Activity> queryActivityForConvertByNameClueId(Map<String, Object> map);
}

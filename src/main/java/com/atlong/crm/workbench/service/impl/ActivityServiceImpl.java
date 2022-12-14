package com.atlong.crm.workbench.service.impl;

import com.atlong.crm.workbench.domian.Activity;
import com.atlong.crm.workbench.mapper.ActivityMapper;
import com.atlong.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: YunLong
 * @Date: 2022/7/29 16:52
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Override
    public int saveActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }
    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryCountOfActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountOfActivityByCondition(map);
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateActivity(Activity activity) {
        return activityMapper.updateByPrimaryKeySelective(activity);
    }

    @Override
    public List<Activity> queryAllActivities() {
        return activityMapper.selectAllActivities();
    }

    @Override
    public List<Activity> queryActivitiesByIds(String[] ids) {
        return activityMapper.selectActivitiesByIds(ids);
    }

    @Override
    public int addActivities(List<Activity> activities) {
        return activityMapper.insertActivities(activities);
    }

    @Override
    public Activity queryActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }

    @Override
    public List<Activity> queryActivityForDetailByClueId(String id) {
        return activityMapper.selectActivityForDetailByClueId(id);
    }

    @Override
    public List<Activity> queryActivityForDetailByNameClueId(Map<String,Object> map) {
        return activityMapper.selectActivityForDetailByNameClueId(map);
    }

    @Override
    public List<Activity> queryActivitiesForBoundByIds(String[] activityIds) {
        return activityMapper.selectActivitiesForBoundByIds(activityIds);
    }

    @Override
    public List<Activity> queryActivityForConvertByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForConvertByNameClueId(map);
    }


}

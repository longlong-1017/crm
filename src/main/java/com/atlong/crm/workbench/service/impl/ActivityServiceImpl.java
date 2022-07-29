package com.atlong.crm.workbench.service.impl;

import com.atlong.crm.workbench.domian.Activity;
import com.atlong.crm.workbench.mapper.ActivityMapper;
import com.atlong.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

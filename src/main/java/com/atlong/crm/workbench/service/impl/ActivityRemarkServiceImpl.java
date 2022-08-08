package com.atlong.crm.workbench.service.impl;

import com.atlong.crm.workbench.domian.ActivityRemark;
import com.atlong.crm.workbench.mapper.ActivitiesRemarkMapper;
import com.atlong.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/8 11:11
 */
@Service("activityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    private ActivitiesRemarkMapper activitiesRemarkMapper;
    @Override
    public List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String id) {
        return activitiesRemarkMapper.selectActivityRemarksByActivityId(id);
    }

    @Override
    public int saveCreateActivityRemark(ActivityRemark remark) {
        return activitiesRemarkMapper.insertActivityRemark(remark);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
        return activitiesRemarkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int saveEditActivityRemark(ActivityRemark remark) {
        return activitiesRemarkMapper.updateByPrimaryKey(remark);
    }
}

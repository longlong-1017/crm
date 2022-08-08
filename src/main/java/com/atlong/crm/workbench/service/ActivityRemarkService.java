package com.atlong.crm.workbench.service;

import com.atlong.crm.workbench.domian.ActivityRemark;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/8 11:11
 */
public interface ActivityRemarkService {
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String id);

    int saveCreateActivityRemark(ActivityRemark remark);

    int deleteActivityRemarkById(String id);

    int saveEditActivityRemark(ActivityRemark remark);
}

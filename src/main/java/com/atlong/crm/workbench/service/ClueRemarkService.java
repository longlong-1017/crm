package com.atlong.crm.workbench.service;

import com.atlong.crm.workbench.domian.ClueRemark;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/12 22:15
 */
public interface ClueRemarkService {
    List<ClueRemark> queryClueRemarkForDetailByClueId(String id);
}

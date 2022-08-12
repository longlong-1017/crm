package com.atlong.crm.workbench.service.impl;

import com.atlong.crm.workbench.domian.ClueRemark;
import com.atlong.crm.workbench.mapper.ClueRemarkMapper;
import com.atlong.crm.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/12 22:17
 */
@Service("clueRemarkService")
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Override
    public List<ClueRemark> queryClueRemarkForDetailByClueId(String id) {
        return clueRemarkMapper.selectClueRemarkForDetailByClueId(id);
    }
}

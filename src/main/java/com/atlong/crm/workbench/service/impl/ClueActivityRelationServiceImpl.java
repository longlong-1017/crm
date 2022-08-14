package com.atlong.crm.workbench.service.impl;

import com.atlong.crm.workbench.domian.ClueActivityRelation;
import com.atlong.crm.workbench.mapper.ClueActivityRelationMapper;
import com.atlong.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/14 16:36
 */
@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public int saveBoundByActivityIdsAndClueId(List<ClueActivityRelation> clueActivityRelations) {
        return clueActivityRelationMapper.insertBoundByActivityIdsAndClueId(clueActivityRelations);
    }
}

package com.atlong.crm.workbench.service;

import com.atlong.crm.workbench.domian.ClueActivityRelation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/14 16:34
 */

public interface ClueActivityRelationService {
    int saveBoundByActivityIdsAndClueId(List<ClueActivityRelation> clueActivityRelations);
}

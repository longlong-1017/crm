package com.atlong.crm.workbench.service;

import com.atlong.crm.workbench.domian.Clue;

import java.util.List;
import java.util.Map;

/**
 * @Author: YunLong
 * @Date: 2022/8/11 16:30
 */
public interface ClueService {
    int saveClue(Clue clue);

    List<Clue> queryClueByConditionForPage(Map<String,Object> map);

    int queryCountOfClueByConditionForPage(Map<String,Object> map);
}

package com.atlong.crm.workbench.service.impl;

import com.atlong.crm.workbench.domian.Clue;
import com.atlong.crm.workbench.mapper.ClueMapper;
import com.atlong.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: YunLong
 * @Date: 2022/8/11 16:31
 */
@Service("clueService")
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;

    @Override
    public int saveClue(Clue clue) {
        return clueMapper.insert(clue);
    }

    @Override
    public List<Clue> queryClueByConditionForPage(Map<String,Object> map) {
        return clueMapper.selectClueByConditionForPage(map);
    }

    @Override
    public int queryCountOfClueByConditionForPage(Map<String, Object> map) {
        return clueMapper.selectCountOfClueByConditionForPage(map);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectByPrimaryKey(id);
    }

    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }
}

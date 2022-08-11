package com.atlong.crm.settings.service.impl;

import com.atlong.crm.settings.domian.DictionaryValue;
import com.atlong.crm.settings.mapper.DictionaryValueMapper;
import com.atlong.crm.settings.service.DictionaryValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/11 14:41
 */
@Service("dictionaryValueService")
public class DictionaryValueServiceImpl implements DictionaryValueService {
    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;

    @Override
    public List<DictionaryValue> queryDicValuesByTypeCode(String typeCode) {
        return dictionaryValueMapper.selectDicValuesByDicTypeCode(typeCode);
    }
}

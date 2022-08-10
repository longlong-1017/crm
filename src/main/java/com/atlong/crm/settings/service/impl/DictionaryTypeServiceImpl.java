package com.atlong.crm.settings.service.impl;

import com.atlong.crm.settings.domian.DictionaryType;
import com.atlong.crm.settings.mapper.DictionaryTypeMapper;
import com.atlong.crm.settings.service.DictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/10 16:51
 */
@Service("dictionaryTypeService")
public class DictionaryTypeServiceImpl implements DictionaryTypeService {
    @Autowired
    private DictionaryTypeMapper dictionaryTypeMapper;

    @Override
    public List<DictionaryType> queryAll() {
        return dictionaryTypeMapper.selectAll();
    }

    @Override
    public int queryCountByCode(String code) {
        return dictionaryTypeMapper.selectCountByCode(code);
    }

    @Override
    public int saveDictionaryType(DictionaryType dictionaryType) {
        return dictionaryTypeMapper.insert(dictionaryType);
    }

    @Override
    public int deleteDictionaryTypeByCodes(String[] code) {
        return dictionaryTypeMapper.deleteByCodes(code);
    }

    @Override
    public int editDictionaryByCode(DictionaryType dictionaryType) {
        return dictionaryTypeMapper.updateByPrimaryKeySelective(dictionaryType);
    }
}

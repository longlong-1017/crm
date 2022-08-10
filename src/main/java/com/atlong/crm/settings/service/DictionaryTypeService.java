package com.atlong.crm.settings.service;

import com.atlong.crm.settings.domian.DictionaryType;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/10 16:50
 */
public interface DictionaryTypeService {
    List<DictionaryType> queryAll();

    int queryCountByCode(String code);

    int saveDictionaryType(DictionaryType dictionaryType);

    int deleteDictionaryTypeByCodes(String[] code);

    int editDictionaryByCode(DictionaryType dictionaryType);
}

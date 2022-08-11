package com.atlong.crm.settings.service;

import com.atlong.crm.settings.domian.DictionaryType;
import com.atlong.crm.settings.domian.DictionaryValue;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/11 14:40
 */
public interface DictionaryValueService {
    List<DictionaryValue> queryDicValuesByTypeCode(String typeCode);
}

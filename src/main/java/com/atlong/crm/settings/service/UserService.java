package com.atlong.crm.settings.service;

import com.atlong.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @Author: YunLong
 * @Date: 2022/7/22 20:01
 */
public interface UserService {
    User queryByActAndPwd(Map<String,Object> map);

    List<User> queryAllUser();
}

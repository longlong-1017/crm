package com.atlong.crm.settings.service.impl;

import com.atlong.crm.settings.domain.User;
import com.atlong.crm.settings.mapper.UserMapper;
import com.atlong.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: YunLong
 * @Date: 2022/7/22 20:04
 */
@Service("uerService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryByActAndPwd(Map<String, Object> map) {
        System.out.println("service运行了");
        return userMapper.selectByActAndPwd(map);
    }

    @Override
    public List<User> queryAllUser() {
        return userMapper.selectAllUser();
    }
}

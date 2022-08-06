package com.atlong.crm.settings.service.impl;

import com.atlong.crm.settings.domain.User;
import com.atlong.crm.settings.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Component
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    public void testQueryByActAndPwd() {
    }

    public void testQueryAllUser() {
    }

    @Test
    public void testqueryAllActAndName() {
        List<User> list = userService.queryAllActAndName();
        list.forEach(user -> {
            System.out.println(user.toString());
        });
    }
}
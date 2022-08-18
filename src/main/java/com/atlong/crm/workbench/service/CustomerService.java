package com.atlong.crm.workbench.service;

import com.atlong.crm.workbench.domian.Customer;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/18 22:12
 */
public interface CustomerService {
    List<String> queryCustomerNameByName(String customerName);
}

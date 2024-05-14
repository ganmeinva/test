package com.zhengxin.one.service;

import com.zhengxin.one.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 1. @ClassName OrderService
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/5/11 15:06
 * 5. @Version 1.0
 */
@Service
public class OrderService {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    public   void  getUser(){
         System.out.println(userMapper.getUser());
    }

}

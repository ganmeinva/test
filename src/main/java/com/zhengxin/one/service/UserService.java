package com.zhengxin.one.service;

import com.zhengxin.one.esStudy.People;
import com.zhengxin.one.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 1. @ClassName UserService
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/5/11 15:06
 * 5. @Version 1.0
 */
@Service
public class UserService {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderMapper orderMapper;
    public   void update(){
        System.out.println(orderMapper.updateOrder(new People("haha",2,16)));
    }
}

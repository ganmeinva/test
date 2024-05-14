package com.zhengxin.one.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * 1. @ClassName UserMapper
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/5/11 15:35
 * 5. @Version 1.0
 */
public interface UserMapper {

     @Select("select 'user'")
     String getUser();
}

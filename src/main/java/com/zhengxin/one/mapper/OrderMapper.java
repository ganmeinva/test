package com.zhengxin.one.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * 1. @ClassName OrderMapper
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/5/11 16:44
 * 5. @Version 1.0
 */
public interface OrderMapper {

    @Select("select 'order'")
    String getOrder();
}

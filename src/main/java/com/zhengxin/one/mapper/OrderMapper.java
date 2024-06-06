package com.zhengxin.one.mapper;

import com.zhengxin.one.esStudy.People;
import org.apache.ibatis.annotations.Update;

/**
 * 1. @ClassName OrderMapper
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/5/11 16:44
 * 5. @Version 1.0
 */
public interface OrderMapper {

    @Update("update people  ")
    String updateOrder(People haha);
}

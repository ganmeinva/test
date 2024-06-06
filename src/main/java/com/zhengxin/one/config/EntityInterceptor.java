package com.zhengxin.one.config;

import com.zhengxin.one.domain.BaseEntity;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;
import java.util.Date;

/**
 * 1. @ClassName EntityInterceptor
 * 2. @Description 拦截mybatis 增删改查操作
 * 3. @Author gcy
 * 4. @Date 2024/5/23 16:09
 * 5. @Version 1.0
 */
@Intercepts({
        @Signature(type = StatementHandler.class , // 拦截类
                method = "prepare", // 拦截方法
                args = {Connection.class, Integer.class} //对应方法参数
                 )
})
public class EntityInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        ParameterHandler parameterHandler = statementHandler.getParameterHandler();
        // 执行sql
        String sql = boundSql.getSql().toLowerCase();
        // 执行sql传入参数
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject != null) {
            if (parameterObject instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) parameterObject;
                if (sql.startsWith("insert")) {
                    baseEntity.setCreateTime(new Date());
                } else if (sql.startsWith("update")) {
                    baseEntity.setUpdateTime(new Date());
                }
            }
        }
        return invocation.proceed();
    }
}

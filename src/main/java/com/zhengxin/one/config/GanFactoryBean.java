package com.zhengxin.one.config;

import com.zhengxin.one.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 1. @ClassName GanFactoryBean
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/5/11 15:50
 * 5. @Version 1.0
 */
public class GanFactoryBean implements FactoryBean {
    private SqlSession session;

     private Class<?> mapperClass;

    public GanFactoryBean(Class<?> mapperClass) {
        this.mapperClass = mapperClass;
    }

    @Autowired
    private void setSqlSession(SqlSessionFactory sqlSessionFactory){
        sqlSessionFactory.getConfiguration().addMapper(mapperClass);
        this.session=sqlSessionFactory.openSession();
    }

    @Override
    public Object getObject() throws Exception {
        return session.getMapper(mapperClass);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperClass;
    }
}

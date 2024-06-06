package com.zhengxin.one;

import com.zhengxin.one.annotaion.GanScan;
import com.zhengxin.one.config.EntityInterceptor;
import com.zhengxin.one.service.OrderService;
import com.zhengxin.one.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAsync
@GanScan("com.zhengxin.one.mapper")
public class OneApplication {

        public static void main(String[] args) {
        AnnotationConfigApplicationContext aop = new AnnotationConfigApplicationContext(OneApplication.class);
        OrderService orderService = (OrderService) aop.getBean("orderService");
        UserService userService = (UserService) aop.getBean("userService");
        orderService.getUser();
        userService.update();
    }
//    public static void main(String[] args) {
//        SpringApplication.run(OneApplication.class, args);
//    }

    @Bean
    SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        // 增加自定义拦截器
        EntityInterceptor interceptor=new EntityInterceptor();
        build.getConfiguration().addInterceptor(interceptor);
        return build;
    }


}

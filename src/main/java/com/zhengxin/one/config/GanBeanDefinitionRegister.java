package com.zhengxin.one.config;

import com.zhengxin.one.annotaion.GanScan;
import com.zhengxin.one.annotaion.GanScanner;
import com.zhengxin.one.mapper.OrderMapper;
import com.zhengxin.one.mapper.UserMapper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Map;

/**
 * 1. @ClassName GanBeanDefinitionRegister
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/5/11 15:38
 * 5. @Version 1.0
 */
public class GanBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
       // 获取mapper文件需要扫描路径
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(GanScan.class.getName());
        String path = (String) annotationAttributes.get("value");
        System.out.println(path+"-----");
        GanScanner ganScanner=new GanScanner(beanDefinitionRegistry);
        ganScanner.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return true;
            }
        });
        ganScanner.scan(path);
//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        beanDefinition.setBeanClass(GanFactoryBean.class);
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(OrderMapper.class);
//        beanDefinitionRegistry.registerBeanDefinition("orderMapper",beanDefinition);
//        AbstractBeanDefinition beanDefinition1 = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        beanDefinition.setBeanClass(GanFactoryBean.class);
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
//        beanDefinitionRegistry.registerBeanDefinition("userMapper",beanDefinition1);
    }
}

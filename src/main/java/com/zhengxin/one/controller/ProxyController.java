package com.zhengxin.one.controller;

import com.zhengxin.one.service.UserInterface;
import com.zhengxin.one.service.UserService;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 1. @ClassName ProxyController
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/11/6 15:23
 * 5. @Version 1.0
 */
public class ProxyController {

    public  static  void cglibTest() {
        UserService target = new UserService();
        // 通过cglib技术
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        // 定义额外逻辑，也就是代理逻辑
        enhancer.setCallbacks(new Callback[]{new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy
                    methodProxy) throws Throwable {
                System.out.println("before...");
                Object result = methodProxy.invoke(target, objects);
                System.out.println("after...");
                return result;
            }
        }});
        // 动态代理所创建出来的UserService对象
        UserService userService = (UserService) enhancer.create();
        // 执行这个userService的test方法时，就会额外会执行一些其他逻辑
        userService.test();
    }

    public static void jdkTest(){
        UserService target = new UserService();
       //   UserInterface接口的代理对象
        UserInterface proxy =(UserInterface) Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class[]
                {UserInterface.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before...");
                Object result = method.invoke(target, args);
                System.out.println("after...");
                return result;
            }
        });
        proxy.xxxx();
    }

    public static void proxyFactory(){
        UserService target=new UserService();
        ProxyFactory proxyFactory=new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(new org.aopalliance.intercept.MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {
               // 可自己定义相关前置后置通知
                System.out.println("方法执行前");
                Object proceed = methodInvocation.proceed();
                System.out.println("方法执行后");
                return proceed;
            }
        });
        // Advisor 由advice和pointcut组成，advice为增强逻辑，pointcut为判断方法是否需要增强
        proxyFactory.addAdvisor(new PointcutAdvisor() {
            @Override
            public Pointcut getPointcut() {
                return new StaticMethodMatcherPointcut() {
                    @Override
                    public boolean matches(Method method, Class<?> aClass) {
                        return method.getName().equals("test")&&aClass.getName().equals("userService");
                    }
                };

            }

            @Override
            public Advice getAdvice() {
                return new org.aopalliance.intercept.MethodInterceptor() {
                    @Override
                    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                        System.out.println("方法执行前啊");
                        Object proceed = methodInvocation.proceed();
                        System.out.println("方法执行后啊");
                        return proceed;
                    }
                };
            }

            @Override
            public boolean isPerInstance() {
                return false;
            }
        });



        UserService proxy =(UserService) proxyFactory.getProxy();
        System.out.println(proxy);
       // proxy.xxxx();
    }

    public  static  void main(String [] args){
        proxyFactory();
    }

}

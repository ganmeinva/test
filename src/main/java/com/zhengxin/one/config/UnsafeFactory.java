package com.zhengxin.one.config;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 1. @ClassName UnsafeFactory
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/11/19 14:27
 * 5. @Version 1.0
 */
public class UnsafeFactory  {

    /**
     * 获取 Unsafe 对象
     *
     * @return
     */
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字段的内存偏移量
     *
     * @param unsafe
     * @param clazz
     * @param fieldName
     * @return
     */
    public static long getFieldOffset(Unsafe unsafe, Class clazz, String fieldName) {
        try {
            return unsafe.objectFieldOffset(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }
}
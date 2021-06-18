package com.lichao666.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author lichao
 * @date 2021/1/15
 */
public class ReflectionTest {

    public static void main(String[] args) throws Exception {
        Class clz = Class.forName("com.lichao666.reflection.fruit.Apple");

        Method m = clz.getDeclaredMethod("makeApplePie",String.class);
        Constructor constructor = clz.getConstructor(String.class, double.class);
        Object object = constructor.newInstance("青苹果",2.45);
        m.invoke(object,"李超");
    }
}

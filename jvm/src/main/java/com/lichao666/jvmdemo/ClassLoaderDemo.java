package com.lichao666.jvmdemo;

public class ClassLoaderDemo {
    public static void main(String[] args) {
        ClassLoader classloader1 = ClassLoader.getSystemClassLoader();
        //sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(classloader1);
        //获取到扩展类加载器
        //sun.misc.Launcher$ExtClassLoader@424c0bc4
        System.out.println(classloader1.getParent());
        //获取到引导类加载器 null
        System.out.println(classloader1.getParent().getParent());
        //获取系统的ClassLoader
        ClassLoader classloader2 = Thread.currentThread().getContextClassLoader();
        //sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(classloader2);
        String[]strArr=new String[10];
        ClassLoader classLoader3 = strArr.getClass().getClassLoader();
        //null,表示使用的是引导类加载器
        System.out.println(classLoader3);
        ClassLoaderDemo[]refArr=new ClassLoaderDemo[10];
        //sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(refArr.getClass().getClassLoader());
        int[]intArr=new int[10];
        //null,如果数组的元素类型是基本数据类型,数组类是没有类加载器的
        System.out.println(intArr.getClass().getClassLoader());
    }
}

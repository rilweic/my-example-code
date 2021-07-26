package com.lichao666.virtualstack.dynamiclinking;

public class GetClassLoader {
    public static void main(String[] args) {
        ClassLoader cl = DynamicLinkingTest.class.getClassLoader();
        System.out.println(cl);
        System.out.println("----- sun.boot.class.path\n");
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println("----- java.ext.dirs\n");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println("---- java.class.path");
        System.out.println(System.getProperty("java.class.path"));

    }
}

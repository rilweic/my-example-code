package com.lichao666.interfacetest;

public class SubClass implements A,B{
    @Override
    public void sayHi() {
        System.out.println("hi in sub class");
    }

}

package com.lichao666.myrpc.server;

/**
 * @author lichao
 * @date 2021/1/14
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHi(String name) {
        return "hi:"+name +"！";
    }
}

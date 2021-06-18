package com.lichao666.myrpc;

import com.lichao666.myrpc.client.RPCClient;
import com.lichao666.myrpc.server.HelloService;
import com.lichao666.myrpc.server.HelloServiceImpl;
import com.lichao666.myrpc.server.Server;
import com.lichao666.myrpc.server.ServiceCenter;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class RpcTest
{
    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Server serviceServer = new ServiceCenter(8088);
                    serviceServer.register(HelloService.class, HelloServiceImpl.class);
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        HelloService service = RPCClient.getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", 8088));
        System.out.println(service.sayHi("test"));
    }
}

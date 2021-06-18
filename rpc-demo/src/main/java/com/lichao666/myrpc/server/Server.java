package com.lichao666.myrpc.server;

import java.io.IOException;

/**
 * @author lichao
 * @date 2021/1/14
 */
public interface Server {
    public void stop();

    public void start() throws IOException;

    public void register(Class serviceInterface, Class impl);

    public boolean isRunning();

    public int getPort();
}
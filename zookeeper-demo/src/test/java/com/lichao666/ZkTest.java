package com.lichao666;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZkTest {

    private ZooKeeper zkCli;
    private static final String CONNECT_STRING="node1:2181,node2:2181,node3:2181";
    private static final int SESSION_TIMEOUT=2000;

    @Before
    public void before() throws IOException {
        zkCli = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, e->{
            System.out.println("默认回调函数");
        });
    }

    @Test
    public void ls() throws KeeperException, InterruptedException {
        List<String> children = zkCli.getChildren("/animals", true);
        for (String child : children) {
            System.out.println(child);
        }
    }
}

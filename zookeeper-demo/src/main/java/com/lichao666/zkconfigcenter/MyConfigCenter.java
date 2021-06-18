package com.lichao666.zkconfigcenter;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class MyConfigCenter implements Watcher {

    public static final String ZKCONNECTION = "10.124.128.202:2181,10.124.128.203:2181,10.124.128.204:2181";
//    public static final String ZKCONNECTION = "192.168.66.201:2181,192.168.66.202:2181,192.168.66.203:2181";

    CountDownLatch countDownLatch = new CountDownLatch(1);
    ZooKeeper zkCli;

    private String url;
    private String username;
    private String password;


    @Override
    public void process(WatchedEvent event) {

        if (event.getType() == Event.EventType.None) {
            if (event.getState() == Event.KeeperState.SyncConnected) {
                countDownLatch.countDown();
            }

        } else if (event.getType() == Event.EventType.NodeDataChanged) {
            System.out.println("数据发生了变化");
            initValue();
        }
    }

    public MyConfigCenter() {
        initValue();
    }

    public void initValue() {
        try {
            if(zkCli == null){
                zkCli = new ZooKeeper(ZKCONNECTION, 6000, this);
            }
            countDownLatch.await();
            this.url = new String(zkCli.getData("/mysqlconfig/url", true, null));
            this.username = new String(zkCli.getData("/mysqlconfig/user", true, null));
            this.password = new String(zkCli.getData("/mysqlconfig/password", true, null));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) throws InterruptedException {
        while (true){
            MyConfigCenter config = new MyConfigCenter();
            System.out.println("#############################");
            System.out.println("url:"+config.getUrl());
            System.out.println("username:"+config.getUsername());
            System.out.println("password:"+config.getPassword());
            System.out.println("#############################");
            Thread.sleep(6000);
        }
    }
}

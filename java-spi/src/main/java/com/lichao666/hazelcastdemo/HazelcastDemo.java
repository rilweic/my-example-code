package com.lichao666.hazelcastdemo;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.impl.HazelcastInstanceFactory;
import com.hazelcast.map.IMap;

import java.io.File;

public class HazelcastDemo {

    public static void main(String[] args) {
        Config hazelcastConfig = new Config();
        hazelcastConfig
                .getNetworkConfig()
                .getJoin()
                .getMulticastConfig()
                .setMulticastPort(56789);
        hazelcastConfig
                .getHotRestartPersistenceConfig()
                .setBaseDir(new File("/Users/lichao/tmp", "recovery").getAbsoluteFile());

        HazelcastInstance hazelcastInstance = HazelcastInstanceFactory.newHazelcastInstance(hazelcastConfig);

        // 获取一个分布式map
        IMap<String, Integer> map = hazelcastInstance.getMap("myMap");

        // 在map中存储数据
        map.put("key1", 10);
        map.put("key2", 20);
        map.put("key3", 30);

        // 从map中获取数据
        int value1 = map.get("key1");
        int value2 = map.get("key2");
        int value3 = map.get("key3");

        // 打印获取到的数据
        System.out.println(value1);  // 输出: 10
        System.out.println(value2);  // 输出: 20
        System.out.println(value3);  // 输出: 30

        // 关闭Hazelcast实例
        hazelcastInstance.shutdown();
    }
}

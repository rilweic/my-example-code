package com.lichao666.metricstest;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

public class RegistryCounterTest {
    public static void testCounter() {
        MetricRegistry registry = new MetricRegistry();
        // 1.定义counter的名字
        Counter counter = registry.counter("query_gmv_count_sucess_count");

        // 2.增加计数
        // 默认值为1
        counter.inc();
        // 增加指定的数
        counter.inc(3);

        // 3.减少计数
        // 3.1默认值是1
        counter.dec();
        // 3.2减少指定的数
        counter.dec(2);

        // 获取值
        System.out.println(counter.getCount());
    }

    public static void main(String[] args) {
        RegistryCounterTest.testCounter();
    }
}

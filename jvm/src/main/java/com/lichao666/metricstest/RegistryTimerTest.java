package com.lichao666.metricstest;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public class RegistryTimerTest {
    /**
     * 测试Timer
     */
    public  static void testTimer(){
        MetricRegistry registry = new MetricRegistry();
        // 1.定义counter的名字
        Timer timer = registry.timer("query_gmv_count_sucess_time");

        // 2.打印时间
        // 2.1开始计时
        Timer.Context context = timer.time();
        // 2.2暂停2秒,模拟执行任务
        try {
            Thread.sleep(2000);
        }catch (Exception e){

        }
        // 2.3 获取执行时间
        System.out.println(context.stop()/1000000 + " mis");
    }


    public static void main(String[] args){
        RegistryTimerTest.testTimer();
    }
}

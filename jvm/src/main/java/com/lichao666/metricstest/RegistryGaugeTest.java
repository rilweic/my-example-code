package com.lichao666.metricstest;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

public class RegistryGaugeTest {
    /**
     *自定义
     */
    public static void testGauge() {
        MetricRegistry registry = new MetricRegistry();
        Gauge gauge = registry.register("selfGuage", new Gauge<Integer>() {
            public Integer getValue() {
                return 1;
            }
        });

        System.out.println(gauge.getValue());
    }

    public static void main(String[] args){
        RegistryGaugeTest.testGauge();
    }



}

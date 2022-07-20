package com.lichao666.flink.batch;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class ReduceDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // produce 1，2，3，4，5
        DataStream<Long> source = env.fromSequence(1, 5);

        source.keyBy((KeySelector<Long, Object>) value -> 1)    // 所有数据在一个Partition
                .reduce((ReduceFunction<Long>) (value1, value2) -> {
                    // value1 保存上次迭代的结果值
                    System.out.printf("value1: %d, value2: %d\n", value1, value2);
                    return value1 + value2;
                }).print();

        env.execute();
    }
}

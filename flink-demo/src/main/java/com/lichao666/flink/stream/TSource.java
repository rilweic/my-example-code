package com.lichao666.flink.stream;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

public class TSource {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        List list = new ArrayList<Tuple3<Integer, Integer, String>>();
        list.add(new Tuple3<>(0, 1, "a"));
        list.add(new Tuple3<>(0, 3, "b"));
        list.add(new Tuple3<>(0, 2, "c"));
        list.add(new Tuple3<>(0, 4, "d"));
        list.add(new Tuple3<>(1, 5, "a"));
        list.add(new Tuple3<>(1, 2, "b"));
        list.add(new Tuple3<>(1, 7, "c"));

        DataStreamSource<Tuple3<Integer, Integer, String>> stringDataStreamSource = env.fromCollection(list);
        KeyedStream<Tuple3<Integer, Integer, String>, Integer> result = stringDataStreamSource
                .keyBy((KeySelector<Tuple3<Integer, Integer, String>, Integer>) value -> value.f0);

        result.max(1).print("最大值");
        result.maxBy(1).print("元素");

        env.execute("测试");
    }
}

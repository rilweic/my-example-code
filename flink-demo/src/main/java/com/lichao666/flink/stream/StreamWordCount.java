package com.lichao666.flink.stream;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StreamWordCount {
    public static void main(String[] args) throws Exception {
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        String host = parameterTool.get("host");
        int port = parameterTool.getInt("port");
        // 创建一个流处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 数据源，从socket中获取
        DataStream<String> text = env.socketTextStream(host, port);

        // 对数据进行转换
        DataStream<Tuple2<String, Integer>> words = text.flatMap((FlatMapFunction<String, Tuple2<String,Integer>>) (value, out) -> {
            for (String word : value.split(" ")) {
                out.collect(new Tuple2<>(word, 1));
            }
        },Types.TUPLE(Types.STRING, Types.INT));

        words.keyBy(value -> value.f0).sum(1).print().setParallelism(1);
        // 执行任务
        env.execute("Stream WordCount");
    }
}

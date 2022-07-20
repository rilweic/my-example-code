package com.lichao666.flink.batch;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class BatchWordCount {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // 从文件中读取数据
        String inputPath = "flink-data/hello.txt";
        DataSet<String> inputDataSet = env.readTextFile(inputPath);

        // 将数据进行分割，每个单词作为一个元素
        FlatMapOperator<String, Tuple2<String, Integer>> stringTuple2FlatMapOperator = inputDataSet.flatMap(new Tokenizer());
        AggregateOperator<Tuple2<String, Integer>> sum = stringTuple2FlatMapOperator.groupBy(0).sum(1);

        // 打印输出
        sum.print();

    }

    public static  class Tokenizer implements FlatMapFunction<String, Tuple2<String,Integer>> {
        @Override
        public void flatMap(String s, Collector<Tuple2<String,Integer>> collector) throws Exception {
            for (String word : s.split(" ")) {
                collector.collect(new Tuple2<>(word, 1));
            }
        }
    }
}

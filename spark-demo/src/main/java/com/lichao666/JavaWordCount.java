package com.lichao666;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import scala.Tuple2;

import java.util.Arrays;

/**
 * Hello world!
 */
public class JavaWordCount {
    public static void main(String[] args) {
        JavaWordCount jwc = new JavaWordCount();
        SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("local[*]").setAppName("JavaWordCount");

        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        String url = jwc.getClass().getClassLoader().getResource("input/word.txt").getPath();

        // 读取文件
        JavaRDD<String> fileRDD = jsc.textFile(url);
        // 将文字按空格进行分词
        JavaRDD<String> word = fileRDD.flatMap((FlatMapFunction<String, String>) s -> Arrays.stream(s.split(" ")).iterator());
        // 将文字map操作 每个单词记一次 1
        JavaPairRDD<String, Integer> wordToOne = word.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });

        // 将单词进行聚合
        JavaPairRDD<String, Integer> reduced = wordToOne.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        // 交换元组的顺序，方便排序
        JavaPairRDD<Integer, String> integerStringJavaPairRDD = reduced.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> tp) throws Exception {
                return tp.swap();
            }
        });

        // 排序
        JavaPairRDD<Integer, String> sortedValue = integerStringJavaPairRDD.sortByKey(false);
        // 排序再将数据转过来

        JavaPairRDD<String, Integer> stringIntegerJavaPairRDD = sortedValue.mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> tp) throws Exception {
                return tp.swap();
            }
        });

        stringIntegerJavaPairRDD.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> tp) throws Exception {
                System.out.println("("+tp._1 + "\t" +tp._2+")");
            }
        });

        jsc.stop();

    }
}

package com.lichao666;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

/**
 * Hello world!
 */
public class JavaWordCountWithLambda {
    public static void main(String[] args) {
        JavaWordCountWithLambda jwc = new JavaWordCountWithLambda();
        SparkConf sparkConf = new SparkConf();
        // 这里需要注意，打包的时候需要将mater设置的属性去掉
        sparkConf.setMaster("local[*]").setAppName("JavaWordCount");

        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        String url = jwc.getClass().getClassLoader().getResource("input/word.txt").getPath();

        // 读取文件
        JavaRDD<String> fileRDD = jsc.textFile(url);
        // 将文字按空格进行分词
        JavaRDD<String> word = fileRDD.flatMap( s -> Arrays.stream(s.split(" ")).iterator());
        // 将文字map操作 每个单词记一次 1
        JavaPairRDD<String, Integer> wordToOne = word.mapToPair( s -> new Tuple2<>(s, 1));

        // 将单词进行聚合
        JavaPairRDD<String, Integer> reduced = wordToOne.reduceByKey((m,n) -> m+n);

        // 交换元组的顺序，方便排序
        JavaPairRDD<Integer, String> integerStringJavaPairRDD = reduced.mapToPair(Tuple2::swap);

        // 排序
        JavaPairRDD<Integer, String> sortedValue = integerStringJavaPairRDD.sortByKey(false);
        // 排序再将数据转过来

        JavaPairRDD<String, Integer> stringIntegerJavaPairRDD = sortedValue.mapToPair(Tuple2::swap);

        stringIntegerJavaPairRDD.foreach( tp -> System.out.println("("+tp._1 + "\t" +tp._2+")"));

        jsc.stop();

    }
}

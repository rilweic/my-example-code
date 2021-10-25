package com.lichao666

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object WordCountWithSparkSession {

  def main(args: Array[String]): Unit = {
    // 创建spark session对象
    val sparkSession = SparkSession
      .builder
      .appName("Spark Pi")
      .master("local[*]")
      .getOrCreate()

//    // 创建 Spark 运行配置对象
//    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
//    // 创建 Spark 上下文环境对象(连接对象)
//    val sc : SparkContext = new SparkContext(sparkConf)
    // 读取文件数据
//    val url = args(0)
    val url = this.getClass.getClassLoader.getResource("input/word.txt").toString
    val fileRDD: RDD[String] = sparkSession.sparkContext.textFile(url)
    // 将文件中的数据进行分词
    val wordRDD: RDD[String] = fileRDD.flatMap( _.split(" ") )
    // 转换数据结构 word => (word, 1)
    val word2OneRDD: RDD[(String, Int)] = wordRDD.map((_,1))
    // 将转换结构后的数据按照相同的单词进行分组聚合reduceByKey((x,y)=> x+y)
    val word2CountRDD: RDD[(String, Int)] = word2OneRDD.reduceByKey(_+_)
    val value: RDD[(String, Int)] = word2CountRDD.sortBy(_._2,false)
    // 将数据聚合结果采集到内存中
    val word2Count: Array[(String, Int)] = value.collect()
    // 输出结果
    word2Count.foreach(println)
    // 关闭spark连接
    sparkSession.stop()

  }
}

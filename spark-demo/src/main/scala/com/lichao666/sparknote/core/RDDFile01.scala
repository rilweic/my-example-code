package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 从内存中创建RDD
 */
object RDDFile01 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[*]").setAppName("RDDFile01")
    val sparkContext = new SparkContext(conf);

    val url = this.getClass.getClassLoader.getResource("input/word.txt").toString
    // 从文件中读取
    val fileRdd = sparkContext.textFile(url);
    fileRdd.foreach(println)

    sparkContext.stop()
  }

}

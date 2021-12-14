package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 从内存中创建RDD
 */
object RDDMemory {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("RDDMemory").setMaster("local[*]")
    val sc = new SparkContext(conf)

    // 从内存中创建RDD
    val rdd = sc.parallelize(Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

    // 输出结果
    rdd.foreach(println)

    sc.stop()
  }

}

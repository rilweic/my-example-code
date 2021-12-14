package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 从内存中创建RDD
 */
object RDDOperator01Transform01 {

  def main(args: Array[String]): Unit = {

    // * 表示使用机器的所有核，1表示使用一个核
    val sparkConf = new SparkConf().setAppName("RDDOperator01Transform01").setMaster("local[1]")
    val sc = new SparkContext(sparkConf)

    val numRdd = sc.makeRDD(1 to 10)

    val doubleNumRdd = numRdd.map(_ * 2)

    doubleNumRdd.collect.foreach(println)

    sc.stop()
  }

}

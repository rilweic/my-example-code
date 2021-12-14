package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 从内存中创建RDD
 */
object RDDMemoryPar01 {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("RDDMemoryPar01").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7), 5)


    rdd.saveAsTextFile("output/" + Common.currentDateTime)

    sc.stop()
  }

}

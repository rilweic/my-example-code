package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

object RDDOperator16Transform01 {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator15Transform01")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(("a", 1), ("a", 2), ("b", 1), ("b", 2), ("b", 3)), 1)
    // 根据key值进行分组
    rdd.groupByKey().sortByKey(false).collect.foreach(tu => {
      println("key: ==== " + tu._1)
      for (elem <- tu._2) {
        println("value: ==== " + elem)
      }
    })

    sc.stop()
  }

}

package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

object RDDOperator19Transform01 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    var rdd = sc.makeRDD(Array(("A", 2), ("A", 1), ("B", 1), ("B", 2), ("C", 1), ("A", 3)), 2)

    val collect: Array[(String, String)] = rdd.combineByKey(
      (v: Int) => v + "_",
      (c: String, v: Int) => c + "@" + v, //同一分区内
      (c1: String, c2: String) => c1 + "$" + c2
    ).collect

    for (elem <- collect) {
      println(elem._1, "--", elem._2)
    }
  }

}

package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

object RDDOperator18Transform01 {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator18Transform01")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    /**
     * rdd.aggregateByKey(0)(_+_, _+_).collect.foreach(println)
     * 如果聚合计算时，分区内和分区间计算规则相同，spark提供了简化的方法
     */
    rdd.foldByKey(0)(_ + _).collect.foreach(println)

    sc.stop()
  }
}

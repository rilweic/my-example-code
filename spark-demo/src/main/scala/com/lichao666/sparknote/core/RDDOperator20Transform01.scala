package com.lichao666.sparknote.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOperator20Transform01 {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator20Transform01")
    val sc = new SparkContext(sparkConf)

    // TODO 算子 - (Key - Value类型) sortByKey join

    val dataRDD1 = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)))
    val sortRDD1: RDD[(String, Int)] = dataRDD1.sortByKey(true)
    val sortRDD2: RDD[(String, Int)] = dataRDD1.sortByKey(false)
    sortRDD1.collect().foreach(println)
    sortRDD2.collect().foreach(println)
    //(a,1)
    //(b,2)
    //(c,3)
    //(c,3)
    //(b,2)
    //(a,1)

    println("-----------------------------------")
    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("c", 3)
    ))

    val rdd2 = sc.makeRDD(List(
      ("a", 5), ("c", 6), ("a", 4)
    ))

    /**
     * join : 两个不同数据源的数据，相同的key的value会连接在一起，形成元组
     * 如果两个数据源中key没有匹配上，那么数据不会出现在结果中
     * 如果两个数据源中key有多个相同的，会依次匹配，可能会出现笛卡尔乘积，数据量会几何性增长，会导致性能降低。
     * 类似于MySql中的join
     */
    val joinRDD: RDD[(String, (Int, Int))] = rdd1.join(rdd2)

    //(a,(1,5))
    //(a,(1,4))
    //(a,(2,5))
    //(a,(2,4))
    //(c,(3,6))
    joinRDD.collect().foreach(println)

    sc.stop()
  }
}

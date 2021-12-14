package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

object RDDOperator15Transform01 {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator15Transform01")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(("a", 2), ("a", 2), ("c", 2), ("d", 2), ("c", 2)), 1)


    // 将相同key的数据加在一起
    val rdd2 = rdd.reduceByKey(_ + _).sortBy(_._2, false)
    rdd2.saveAsTextFile(Common.defaultOutputPath)
    sc.stop()
  }

}

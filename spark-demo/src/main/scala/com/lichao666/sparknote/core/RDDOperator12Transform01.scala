package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

object RDDOperator12Transform01 {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator11Transform01")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(6, 2, 3, 4, 52, 1), 2).sortBy(x => -x, true)

    rdd.saveAsTextFile(Common.defaultOutputPath)

    sc.stop()
  }

}

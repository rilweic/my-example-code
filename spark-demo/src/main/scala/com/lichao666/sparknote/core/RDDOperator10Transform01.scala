package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

object RDDOperator10Transform01 {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator10Transform01")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 3)

    /**
     * coalesce方法默认情况下不会将分区的数据打乱重新组合
     * 这种情况下的缩减分区可能会导致数据不均衡，出现数据倾斜
     * 如果想要让数据均衡，可以进行shuffle处理
     * val newRDD: RDD[Int] = rdd.coalesce(2)
     */
    val rdd2 = rdd.coalesce(2, true)

    rdd2.saveAsTextFile("output/" + Common.currentDateTime)

    sc.stop()
  }

}

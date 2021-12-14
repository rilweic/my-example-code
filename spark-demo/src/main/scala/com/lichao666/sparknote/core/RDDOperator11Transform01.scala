package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

object RDDOperator11Transform01 {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator11Transform01")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 1)

    /**
     * coalesce算子可以扩大分区的，但是如果不进行shuffle操作，是没有意义，不起作用。
     * 所以如果想要实现扩大分区的效果，需要使用shuffle操作
     * spark提供了一个简化的操作
     * 缩减分区：coalesce，如果想要数据均衡，可以采用shuffle
     * 扩大分区：repartition, 底层代码调用的就是coalesce，而且肯定采用shuffle
     * def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
     * coalesce(numPartitions, shuffle = true)
     * }
     */

    val rdd2 = rdd.repartition(3)

    rdd2.saveAsTextFile("output/" + Common.currentDateTime)

    sc.stop()
  }

}

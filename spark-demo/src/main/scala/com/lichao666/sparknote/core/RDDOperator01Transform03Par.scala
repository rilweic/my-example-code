package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 从内存中创建RDD
 */
object RDDOperator01Transform03Par {

  def main(args: Array[String]): Unit = {

    // * 表示使用机器的所有核，1表示使用一个核
    val sparkConf = new SparkConf().setAppName("RDDOperator01Transform01").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    /**
     * 1. rdd的计算一个分区内的数据是一个一个执行逻辑
     * 只有前面一个数据全部的逻辑执行完毕后，才会执行下一个数据。
     * 分区内数据的执行是有序的。
     * 2. 不同分区数据计算是无序的。
     * 3. 所有的数必定是先打印* 再打印#
     */

    val rdd1 = sc.makeRDD(1 to 5, 2)
    val rdd2 = rdd1.map(item => {
      println("*******" + item)
      item
    })
    val rdd3 = rdd2.mapPartitions(item => {
      item.map(item => {
        println("########" + item)
        item
      })
    })

    rdd3.collect()
    sc.stop()
  }

}

package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

/**
 * mapPartitionsWithIndex
 * 将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处理，哪怕是过滤数据，在处理时同时可以获取当前分区索引
 */
object RDDOperator03Transform01 {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator03Transform01")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)
    val rdd2 = rdd.mapPartitionsWithIndex(
      (index, iter) => {
        if (index == 0) {
          iter
        } else {

          Nil.iterator
        }
      })


    rdd2.collect.foreach(println)

    sc.stop()
  }

}

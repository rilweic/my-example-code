package com.lichao666.sparknote.core

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object RDDPersist03 {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator20Transform01")
    val sc = new SparkContext(sparkConf)

    val list = List("Hello Scala", "Hello Spark")

    val rdd = sc.makeRDD(list, 2)

    val flatRDD = rdd.flatMap(_.split(" "))

    val mapRDD = flatRDD.map(word => {
      println("☆☆☆☆☆")
      (word, 1)
    })

    // cache默认持久化的操作，只能将数据保存到内存中，如果想要保存到磁盘文件，需要更改存储级别
    //mapRDD.cache()

    // 持久化操作必须在行动算子执行时完成的。
    mapRDD.persist(StorageLevel.DISK_ONLY)

    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    println("**************************************")
    val groupRDD = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)

    /**
     * ☆☆☆☆☆
     * ☆☆☆☆☆
     * ☆☆☆☆☆
     * ☆☆☆☆☆
     * (Spark,1)
     * (Hello,2)
     * (Scala,1)
     * **************************************
     * (Spark,CompactBuffer(1))
     * (Hello,CompactBuffer(1, 1))
     * (Scala,CompactBuffer(1))
     */

    sc.stop()
  }
}

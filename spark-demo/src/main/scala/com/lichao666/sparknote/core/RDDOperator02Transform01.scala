package com.lichao666.sparknote.core

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 从内存中创建RDD
 */
object RDDOperator02Transform01 {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator02Transform01")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)
    /**
     * mapPartitions : 可以以分区为单位进行数据转换操作
     * 但是会将整个分区的数据加载到内存进行引用
     * 如果处理完的数据是不会被释放掉，存在对象的引用。
     * 在内存较小，数据量较大的场合下，容易出现内存溢出。
     * */
    val mpRdd = rdd.mapPartitions(x => {
      println("△△△△△△△△△△")
      x.map(x => x * 2)
    })

    mpRdd.collect().foreach(println)
    sc.stop()
  }

}

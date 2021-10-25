package com.lichao666.rddtest

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
 * rdd 常用算子
 */
object RddTest {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd 算子")
    // 创建 Spark 上下文环境对象(连接对象)
    val sc: SparkContext = new SparkContext(sparkConf)
    // map算子
    var list = List(1, 2, 3)
    val preMapRdd = sc.parallelize(list)
    val mapRdd = preMapRdd.map(_ * 3) // 将rdd中的每个元素进行函数操作
    mapRdd.collect().foreach(x => print(x + "\t"))

    println("\n======== map 算子 =======")

    // filter 算子
    list = List(1, 2, 3, 4, 5, 6)
    val preFilterRdd = sc.parallelize(list)
    val filterRdd = preFilterRdd.filter(x => x % 2 == 0)
    filterRdd.collect().foreach(x => print(x + "\t"))
    println("\n======== filter 算子 =======")

    // flatMap 算子
    val unFlatList = List(List(1, 2, 3), List(4, 5), List(67, 8), List(9))
    val preFlatmapRdd: RDD[List[Int]] = sc.parallelize(unFlatList)
    val flatmapRdd: RDD[Int] = preFlatmapRdd.flatMap(_.map(_ * 2))
    flatmapRdd.collect().foreach(x => print(x + "\t"))
    println("\n========  flatmap 算子 =======")

    // flatMap算子 + 模式匹配
    val unFlatList2 = List(List(1, 2, 3), 3, List(3, 4, 5))
    val res = sc.parallelize(unFlatList2).flatMap(
      data => {
        data match {
          case i: List[int] => i
          case i: Int => List(i)
          case _ => List()
        }
      }
    )
    res.collect().foreach(x => print(x + "\t"))
    println("\n========  flatmap 算子 + 模式匹配  =======")

    // mapPartition
    list = List(1, 2, 3, 4, 5, 6)
    val preMapPartitionWithIndex = sc.parallelize(list, 3)
    // 将每个元素所在的分区打印出来
    val mapPartitionWithIndex = preMapPartitionWithIndex
      .mapPartitionsWithIndex((index, iterator) => {
        iterator.map(i => s"元素 $i 所在分区 $index ")
      })

    mapPartitionWithIndex.foreach(println)
    // 打印结果
    // 元素 3 所在分区 1
    // 元素 4 所在分区 1
    // 元素 1 所在分区 0
    // 元素 5 所在分区 2
    // 元素 2 所在分区 0
    // 元素 6 所在分区 2

    preMapPartitionWithIndex.mapPartitionsWithIndex((index,iter) => {
      val buffer:ListBuffer[String] = new ListBuffer[String]
      buffer.append(s"分区 $index 求和 为 ${iter.sum}")
      buffer.toIterator
    }).foreach(println)

    println("\n========  mapPartitionsWithIndex 算子  =======")


    list = List(1, 2, 3, 4, 5, 6)
    val preMapPartition = sc.parallelize(list, 3)
    preMapPartition.mapPartitions(iter =>{
      iter.map(_*10)
    }).foreach(println)
    println("\n========  mapPartitions 算子  =======")

    val tupleList = List(("hadoop","2.7"),("hive","2.1"),("com/lichao666/spark","3.0"),("hadoop","3.0"),("hive","1.1"))
    val preGroupByKeyRdd = sc.parallelize(tupleList)
    preGroupByKeyRdd.groupByKey().map(x =>(x._1,x._2.toList.mkString("|"))).sortByKey(true).foreach(x => print(x + "\t"))

  }
}

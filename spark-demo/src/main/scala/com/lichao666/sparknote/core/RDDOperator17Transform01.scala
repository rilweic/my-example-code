package com.lichao666.sparknote.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOperator17Transform01 {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDDOperator17Transform01")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(("a", 1), ("a", 2), ("b", 1), ("b", 2), ("b", 3)), 2)
    // 根据key值进行聚合,aggregateByKey是柯里化函数。第一个括号参数为聚合的初始值，第二个中有两个参数，1个表示的是分区内的聚合规则，1个表示的分区间的聚合规则
    //(a,3)
    //(b,6)
    rdd.aggregateByKey(0)((x, y) => x + y, (x, y) => x + y).foreach(println)
    //(b,4)
    //(a,7)
    val rdd2 = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)), 2)
    rdd2.aggregateByKey(0)((x, y) => math.max(x, y), (x, y) => x + y).foreach(println)


    val rdd3 = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    val newRDD: RDD[(String, (Int, Int))] = rdd3.aggregateByKey((0, 0))(
      // 参数一：分区内的计算规则，这里的t是分区内的第一个参数，即（0，0），这样的话就很好理解了
      (t, v) => {
        (t._1 + v, t._2 + 1)
      },
      // 参数二：分区间的计算规则
      (t1, t2) => {
        (t1._1 + t2._1, t1._2 + t2._2)
      }
    )

    val resultRDD: RDD[(String, Int)] = newRDD.mapValues {
      case (num, cnt) => {
        num / cnt
      }
    }
    // 获取相同key的数据的平均值 => (a, 3),(b, 4)
    resultRDD.collect().foreach(println)


    val listRdd = sc.makeRDD(List(("a", 1), ("a", 2), ("b", 5), ("a", 4), ("3", 1), ("c", 1)), 2)

    listRdd.aggregateByKey((0, 0))((t, v) => (t._1 + v, t._2 + 1), (t1, t2) => (t1._1 + t2._1, t1._2 + t2._2)).mapValues(t => t._1 / t._2).collect().foreach(println)

    sc.stop()
  }

}

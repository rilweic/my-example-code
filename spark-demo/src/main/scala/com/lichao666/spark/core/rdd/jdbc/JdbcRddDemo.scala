package com.lichao666.spark.core.rdd.jdbc

import org.apache.spark.rdd.{JdbcRDD, RDD}
import org.apache.spark.{SparkConf, SparkContext}

import java.sql.DriverManager

object JdbcRddDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("JdbcRddDemo").setMaster("local[*]")

    val sc = new SparkContext(conf)

    val getConn = () => {
      DriverManager.getConnection("jdbc:mysql://localhost:3306/datax_web?characterEncoding=UTF-8", "root", "root")
    }


    //创建RDD，这个RDD会记录以后从MySQL中读数据

    //new 了RDD，里面没有真正要计算的数据，而是告诉这个RDD，以后触发Action时到哪里读取数据

    val jdbcRDD: RDD[(Int, String)] = new JdbcRDD(
      sc,
      getConn,
      "SELECT * FROM sys_permission WHERE id >= ? AND id < ?",
      101,
      403,
      2, //分区数量 这里由于业务逻辑都相同，所以上面的id的<应该换成<= 才行
      rs => {
        val id = rs.getInt(1)
        val name = rs.getString(2)
//        val age = rs.getInt(3)
        (id, name)
      }
    )

    //


    //触发Action
    val r = jdbcRDD.collect()

    println(r.toBuffer)

    sc.stop()
  }
}

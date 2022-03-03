package com.lichao666.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object Spark02_SparkSQL_UDF {

    def main(args: Array[String]): Unit = {

        // TODO 创建SparkSQL的运行环境
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
        val sparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
        import sparkSession.implicits._

        val df = sparkSession.read.json("datas/user.json")
        df.createOrReplaceTempView("user")

        sparkSession.udf.register("prefixName", (name:String) => {
            "Name: " + name
        })

        sparkSession.sql("select age, prefixName(username) from user").show


        // TODO 关闭环境
        sparkSession.close()
    }
}

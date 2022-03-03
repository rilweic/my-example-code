package com.lichao666.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql._

object Spark04_SparkSQL_JDBC {

    def main(args: Array[String]): Unit = {

        // TODO 创建SparkSQL的运行环境
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
        val spark = SparkSession.builder().config(sparkConf).getOrCreate()
        import spark.implicits._

        // 读取MySQL数据
        val df = spark.read
                .format("jdbc")
                .option("url", "jdbc:mysql://localhost:3306/log_table")
                .option("driver", "com.mysql.jdbc.Driver")
                .option("user", "root")
                .option("password", "root")
                .option("dbtable", "proc_execute_log")
                .load()
        //df.show
        df.createOrReplaceTempView("log")
        spark.sql("select domain_name from log").show

        // 保存数据
        df.write
                .format("jdbc")
                .option("url", "jdbc:mysql://localhost:3306/log_table")
                .option("driver", "com.mysql.jdbc.Driver")
                .option("user", "root")
                .option("password", "root")
                .option("dbtable", "proc_execute_log2")
                .mode(SaveMode.Append)
                .save()


        // TODO 关闭环境
        spark.close()
    }
}

package org.apache.spark.examples.common

object PathUtil {

  def getClassPath(): String = {
    val classPath = Thread.currentThread().getContextClassLoader.getResource("").getPath
    classPath
  }

  def resourcePath(f : String): String = {
    val str = "/Users/lichao/IdeaProjects/big-data/spark-demo/src/main/resources" +f
    str
  }
}

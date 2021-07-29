package com.git.chapter9

import java.io.File
import java.net.URL

import scala.io.Source

/**
 * Created by lichao on 16-4-14.
 */
object FileSource {
  def main(args: Array[String]) {
    //    val source = Source.fromURL("http://hadoop.apache.org/")
    val path: String = this.getClass.getResource("aaa.xxx").getPath
    val source = Source.fromFile(path)
    /**
     * source 相当与指针，如果执行了就相当于指针跳到文件末尾列，再使用时就没有数据了
     */
    //    val content = source.mkString
    //    println("content:"+content)

    val line = source.getLines()
    for (i <- line) {
      val arr = i.toArray
      println(i)
      for (elem <- arr) print(elem + "  ")
      println()
    }
    println()

    source.close()
  }
}


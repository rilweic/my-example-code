package com.git.chapter03

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Administrator on 2016/4/21.
 */
object MyArray {
  def main(args: Array[String]) {
    var one = new ArrayBuffer[String]()
    one += "Hello"
    for (elem <- one) println("第一次:" + elem)
    var two = new ArrayBuffer[String]()
    two += "World"
    two += "Happy"
    two += "New"
    two += "Year"

    one = two

    for (elem <- one) println("第二次：" + elem)

    println("==============================")

    var num = new ArrayBuffer[Int]()

    num += 1
    num += 2
    num += 3
    num += 4
    num += 5
    num += 6
    num += 7
    num += 8
    num += 9

    for (elem <- num) print(elem + ",")

    num = num.filter(_ > 5)
    println("second:")
    for (elem <- num) print(elem + ",")
    println()
    num.remove(2)
    for (elem <- num) print(elem + ",")
    println("========================ArrayBuffer=========================")

    val stus = ArrayBuffer[Student]()
    for (i <- 0 to 9) {
      stus += new Student(i, "lisi")
    }
    for (ele <- stus) println(ele)
  }
}

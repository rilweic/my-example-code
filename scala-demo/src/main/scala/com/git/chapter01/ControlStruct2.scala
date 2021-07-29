package com.git.chapter01

/**
 * Created by Administrator on 2016/3/28.
 */
object ControlStruct2 {
  def main(args: Array[String]) {
    for (i <- 1 to 100) {
      print(i + "\t")
    }

    //嵌套循环
    println("嵌套循环")
    for (i <- 1.to(2); j <- 1 to 3) {
      print("" + i + j + "\t");
    }
  }
}

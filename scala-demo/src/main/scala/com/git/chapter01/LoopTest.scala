package com.git.chapter01

/**
 * Created by lichao on 16-4-25.
 */
object LoopTest {
  def main(args: Array[String]) {
    val arr = Array(11, 12, 13, 14, 15, 16, 17, 18, 19)
    var flag = true
    for (i <- 0 until arr.length if (flag)) {
      if (arr(i) == 15)
        flag = false
      if (flag)
        print(arr(i) + " ")

    }
  }
}

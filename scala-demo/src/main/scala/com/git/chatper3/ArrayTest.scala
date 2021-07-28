package com.git.chatper3

import scala.collection.mutable.ArrayBuffer

/**
 * Created by lichao on 16-3-31.
 */
object ArrayTest {
  def main(args: Array[String]) {

    val fix_arr = new Array[Int](10)
    //val fix_arr  = Array[Int](10)表示有个数字为10
    fix_arr(0) = 11
    scanArray(fix_arr)
    val unfix_array = fix_arr.toBuffer
    unfix_array += 1
    scanArray(unfix_array.toArray)
    unfix_array +=(9, 7, 7) //加数组
    scanArray(unfix_array.toArray)
    unfix_array ++= Array(44, 92)
    scanArray(unfix_array.toArray)

    val arr = Array(7, 8, 9)
    val arr_2 = doubleArray(arr)
    scanArray(arr_2)

    val idol = doubleIdol(arr)
    scanArray(idol)

    val filter = filterArray(arr)
    scanArray(filter)

    val buf = ArrayBuffer[Int](23,22,43)
    val rmBuf = removeElem(buf)
    scanArray(rmBuf.toArray)
    buf.mkString(" and ")

    val ref = ArrayBuffer[Int](9,8,-1,9,-3,-6,-7,-11,99);
    val ref_res = rmMinusExceptFirst(ref)
    scanArray(ref_res.toArray)

    val ref2 = ArrayBuffer[Int](9,8,-1,9,-3,99);
    val ref_res2 = rmMinusExceptFirst2(ref2)
    scanArray(ref_res2.toArray)

  }

  /**
   * 扫描数组中的数据
   * @param array
   */
  def scanArray(array: Array[Int]): Unit = {
    print("array { ")
    for (i <- array) {
      print(i + " ")

    }
    println(" }")
  }

  /**
   * 将数组中的数据乘以2
   * @param array
   * @return 新的数组
   */
  def doubleArray(array: Array[Int]): Array[Int] = {
    val res = for (elem <- array) yield {
      elem * 2
    }
    res
  }

  /**
   * 将原有数组中的偶数乘以2,并丢掉奇数
   * @param array
   * @return
   */
  def doubleIdol(array: Array[Int]): Array[Int] = {
    val res = for (elem <- array if (elem % 2 == 0)) yield {
      elem * 2
    }
    res
  }

  /**
   * 将原有数组中的偶数乘以2,并丢掉奇数另一种做法
   */

  def filterArray(array: Array[Int]): Array[Int] = {
    val res = array.filter(_ % 2 == 0).map(_ * 2) //下划线代表集合中的每个元素
    res
  }

  /**
   * 删除一个数
   * @param array
   * @return
   */
  def removeElem(array: ArrayBuffer[Int]): ArrayBuffer[Int] = {
    array.remove(0) //返回被删除的数
    array
  }

  /**
   * 删除数组中除第一个数以外的负数
   * @param a 缓冲数组
   * @return 删除后的数组
   */
  def rmMinusExceptFirst(a: ArrayBuffer[Int]): ArrayBuffer[Int] = {
    var first = true;
    var n = a.length;
    var i = 0;
    while (i < n) {
      if (a(i) > 0) i += 1
      else {
        if (first) {
          first = false
          i += 1
        }
        else {
          a.remove(i)
          n -= 1
        }
      }
    }
    a
  }

  /**
   * 删除数组中除第一个数以外的负数
   * @param a 缓冲数组
   * @return 删除后的数组
   */
  def rmMinusExceptFirst2(a: ArrayBuffer[Int]): ArrayBuffer[Int] = {
    var first = true
    val indexs = for (i <- 0 until a.length if (first) || a(i) > 0) yield {
      if (a(i) < 0) first = false; i
    }
    for (j <- 0 until indexs.length) a(j) = a(indexs(j))
    a.trimEnd(a.length - indexs.length)
    a
  }


}

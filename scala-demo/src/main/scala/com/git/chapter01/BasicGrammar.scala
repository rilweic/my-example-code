package com.git.chapter01

object BasicGrammar {

  def main(args: Array[String]) {
    //变量定义与声明
    val num = 10;
    val num2: Int = 10;
    //循环
    val arr = Array(3, 4, 56, 7)
    for (i <- 0 until arr.length) {
      print(arr(i) + "\t")
    }

    var count = 0;
    while (count < 10) {
      println(count)
      count += 1
    }

    println(arr.sum)
    //条件判断
    val flag = 19;
    val res = if (flag > 10) "no" else 3
    //函数
    def add(num1: Double, num2: Double) = num1 + num2
    val result = add(2, 3)
    //映射
    val ages = Map("lichao" -> 24, "zhangsan" -> 44)
    val lichao = ages.getOrElse("lichao", 0)
    for ((k, v) <- ages) {
      println(k, v)
    }
    //遍历key
    for (k <- ages.keys) {
      println(k)
    }
    //遍历value
    for (v <- ages.values) {
      println(v)
    }
    //元组
    val tuple = ("A", "B", "C")
    println(tuple._1)
    println(tuple._2)
    println(tuple _3)

    val p = new Person()
    println(p.sex)
  }
}

/**
 * 类
 * @param age 年龄
 * @param name 姓名
 * @param sex 性别
 */
class Person(age: Int, name: String, val sex: String) {

  def this() {
    this(0, "zhangsan", "男")
  }


}

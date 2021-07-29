package com.git.chapter04

/**
 * Created by lichao on 16-4-1.
 */
object MapTest {
  def main(args: Array[String]) {
    val scores = Map("lichao" -> 99, "rilweic" -> 98);
    //定义并初始化固定长度的map
    //查看映射中的值
    //    val lichaoScore = scores("lichao")
    val lichaoScore = scores.getOrElse("lichao", "0")
    println("lichao :" + lichaoScore)

    val newscore = scores + ("lisi" -> 78)
    //添加映射对
    //    scores += (("zhangsan",89)) val的不能被更改,固定长度
    println("scores ：" + scores)
    println("new scores : " + newscore)
    var s = Map("lichao" -> 99, "rilweic" -> 98)
    s += ("wangwu" -> 88) //如果s定义为val 则此句会报错,因为是不可变的映射
    println("s :" + s)
    //可变的映射 注意不需要new
    val age = scala.collection.mutable.Map("lichao" -> 23, "Bob" -> 34)
    age += ("lichao" -> 24)
    println(age)
    // 改变原有的值
    age("lichao") = 25
    println("age:" + age)

    //注意需要new  object 的不需要new class的需要new
    val name = new scala.collection.mutable.HashMap[Int, String]
    name += (1 -> "lichao")

    print("name:" + name)

    println()
    println("----------------元组测试---------------")
    //元组测试
    val tuple = (1, 3.14, "hello")
    val PAI = tuple._2
    println("pai :" + PAI)

    println("-----------拉链操作-----------")
    val symbols = Array("<", "-", ">")
    val counts = Array(2, 10, 2)
    val pairs = symbols.zip(counts)

    for ((s, n) <- pairs) print(s * n) //迭代映射
    println()
    val arr = Array("hello", "world", "happy", "new", "year")
    val Array(h, _, _, n, _) = arr; //模式匹配 ,不需要的地方使用占位符
    print(h + " " + n)
  }
}

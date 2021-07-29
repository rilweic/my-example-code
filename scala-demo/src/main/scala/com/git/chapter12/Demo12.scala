package com.git.chapter12

import scala.math._

/**
 * Created by lichao on 16-5-4.
 */
object Demo12 extends App {

  val num = 3.14
  //fun 是一个包含函数的变量
  val fun = ceil _ ;
  fun(num)

  Array(3.14, 4.56, 7.8).map(fun)

  //函数定义
  val triple = (x: Double) => 3 * x
  triple(5) //得到15
  //匿名函数，将匿名函数传入map中
  Array(1.2, 3.4, 5.6, 3.7).map((x: Double) => x * 2)

  1.to(10)
  1 to 10
  //定义一个参数是函数的函数，参数函数要求参数是Double，返回值是String
  def valueAtOneQuarter(f: (Double) => String) = f(0.25)
  //此函数将作为参数传入上一个函数
  def helloDouble(num: Double) = num + ": Hello"
  //直接将函数名传入即可
  valueAtOneQuarter(helloDouble)
  //定义一个返回值是函数的函数 返回函数的参数是Double类型，方法体是factor*x
  def mulBy(factor: Double) = (x: Double) => factor * x
  //fun1是一个函数变量
  val fun1 = mulBy(3);
  println(fun1(2))


}

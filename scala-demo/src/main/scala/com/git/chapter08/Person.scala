package com.git.chapter08

abstract class Person {
  val id: Int
  //抽象字段就没有初始值的字段，带有getter方法
  var name: String

  //带有getter和setter方法
  def sing: Unit //抽象方法，没有方法体

}

class Zhangsan extends Person {
  override val id: Int = 11

  //带有getter和setter方法
  override def sing: Unit = {
    println("我叫" + name + " 我的id是 " + id)
  }

  override var name: String = "张三"
}

object Main extends App {
  val zs = new Zhangsan
  zs.sing
}

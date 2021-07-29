package com.git.chapter05

/**
 * Created by lichao on 16-5-25.
 */
class AuxiliaryConstruct(var sex: String) {
  private var name: String = _
  private var age = 0

  def this(name: String, age: Int) {
    this("女");
    this.age = age
    this.name = name
  }
}

object AuxiliaryConstruct extends App {
  val auxiliary = new AuxiliaryConstruct("男")
  //调用的主构造器
  val auxiliary2 = new AuxiliaryConstruct("lisi", 23) //调用辅助构造器
  println(auxiliary.age)
  println(auxiliary.name)
  println(auxiliary.sex)
  println(auxiliary2.age)
  println(auxiliary2.name)
  println(auxiliary2.sex)
}
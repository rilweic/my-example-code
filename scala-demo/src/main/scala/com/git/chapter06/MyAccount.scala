package com.git.chapter06

/**
 * 这个类用来测试apply方法
 * Created by lichao on 16-4-13.
 */
class MyAccount {
  def apply(): Unit = {
    println("MyAccount class中的apply方法被调用")
  }


  private var money: Int = _
  private var name: String = _

  def this(money: Int, name: String) {
    this()
    this.money = money
    this.name = name
  }
}

object MyAccount {
  def main(args: Array[String]): Unit = {

    val acc = ApplyOperation(100, "lichao")
    println(acc.name + ":" + acc.money)
    acc() //调用class中的apply方法
  }
}


class ApplyOperation {
  def apply(money: Int) = {
    println("ApplyOperation class 中的apply方法被调用")
    new MyAccount(money, "lichao")
  }
}

object ApplyOperation {
  def apply(money: Int, name: String) = {
    println("ApplyOperation object中的apply方法被调用")
    new MyAccount(money, name)
  }
}








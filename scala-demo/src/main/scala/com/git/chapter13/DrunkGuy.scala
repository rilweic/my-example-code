package com.git.chapter13

object DrunkGuy {

  var money = 10

  def drink(): Unit ={
    money -=1
  }

  def count(): Int ={
    drink()
    money
  }


  def countByValue(x:Int): Unit ={
    for(i <- 1 until 5){
      println(s"by value 第 $i 次，还剩下 $x 块钱")
    }
  }

  def countByName(x: => Int): Unit ={
    for(i <- 1 until 5){
      println(s"by name 第 $i 次，还剩下 $x 块钱")
    }
  }

  def main(args: Array[String]): Unit = {
    countByValue(count())
    println("===================")
    countByName(count())
  }

}

package com.git.chapter10

import java.util.Date

/**
 * define a logged trait
 * Created by lichao on 16-4-29.
 */
trait Logged {
  def log(msg: String) {}
}

trait ConsoleLogger extends Logged {
  override def log(msg: String) {
    println(msg)
  }
}

trait TimeStampLogger extends ConsoleLogger {
  override def log(msg: String): Unit = {
    super.log(new Date() + " " + msg)
  }
}

trait ShortLogger extends ConsoleLogger {
  val max = 15

  override def log(msg: String): Unit = {
    super.log(if (msg.length < max) msg else msg.substring(0, max - 1) + "...")
  }
}

class Myaccount extends Account with Logged {
  def withdraw(amount: Double): Unit = {
    if (amount > balance) log("Insufficent funds") else balance -= amount
  }
}

object MainClass {
  def main(args: Array[String]): Unit = {
    val acc1 = new Myaccount with ShortLogger with TimeStampLogger
    print(acc1.max)
    acc1.withdraw(2)
  }
}


object StructuralTyping extends App {
  //只给出类必须拥有的方法，而不是类的名称
  def quacker(duck: {def quack(value: String): String}) {
    println(duck.quack("Quack"))
  }

  object BigDuck {
    def quack(value: String) = {
      value.toUpperCase
    }
  }

  object SmallDuck {
    def quack(value: String) = {
      value.toLowerCase
    }
  }

  object IamNotReallyADuck {
    def quack(value: String) = {
      "prrrrrp"
    }
  }

  quacker(BigDuck)
  quacker(SmallDuck)
  quacker(IamNotReallyADuck)

  //  object NoQuaker {
  //  }
  //
  //  quacker(NoQuaker) // 编译错误！！！！

  val x = new AnyRef {
    def quack(value: String) = {
      "No type needed " + value
    }
  }
  quacker(x)

}

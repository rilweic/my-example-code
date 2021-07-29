package com.git.chapter06


/**
 * Created by lichao on 16-4-13.
 */
class Accounts {
  val id = Accounts.newUniqueNumber //调用伴生对象中的方法,类和它的伴生对象可以互相访问私有属性
}

object Accounts {
  private var lastNumber = 0

  private def newUniqueNumber = {
    lastNumber += 1; lastNumber
  }

  def main(args: Array[String]) {
    val class_accounts = new Accounts();
    print(class_accounts.id)
  }
}

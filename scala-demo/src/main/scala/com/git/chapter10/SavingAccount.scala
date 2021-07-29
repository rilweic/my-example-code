package com.git.chapter10

/**
 * Created by lichao on 16-4-27.
 */
class SavingAccount extends Account with MyLogger {
  def withdraw(amount: Double): Unit = {
    if (amount > balance) log("金额不足") else balance -= amount
  }
}

trait MyLogger {
  def log(msg: String) {
    print(msg)
  }
}

object Main{
  def main(args: Array[String]) {
    val acc = new SavingAccount
    acc.withdraw(2)
  }
}
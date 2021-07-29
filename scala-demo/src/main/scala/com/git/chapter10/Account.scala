package com.git.chapter10

/**
 * Created by lichao on 16-4-27.
 */
class Account {
  var balance = 0.0


  def deposit(amount: Double) {
    balance += amount
  }

}

object Account {
  private var lastNumber = 0

  private def newUniqueNumber() = {
    lastNumber += 1
  }
}




package com.git.chapter10

/**
 * Created by lichao on 16-4-26.
 */
trait Logger {
  def log(msg: String)
}

class Console extends Logger {
  def apply(): Unit = {
    log("Hello")
  }

  override def log(msg: String): Unit = {
    println("class Console:" + msg)
  }
}

object Client extends App {
  var console = new Console()
  console()

}
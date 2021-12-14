package chapter06

class Test18_TraitConflict extends A with B{
  override def sayHi(): Unit = {
    println("hello world")
  }
}

trait A{
  def sayHi():Unit
}

trait B{
  def sayHi():Unit
}

object Test18_TraitConflict{
  def main(args: Array[String]): Unit = {

    val c = new Test18_TraitConflict()
    c.sayHi()

  }
}
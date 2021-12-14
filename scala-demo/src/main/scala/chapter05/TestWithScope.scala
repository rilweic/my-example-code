package chapter05

object TestWithScope {
  def withScope(func: => String):String = {
    println("withscope")
    func
  }

  def bar(foo: String):String = withScope {
    println("Bar: " + foo)
    "BBBB"
  }

  def main(args: Array[String]): Unit = {

    val bbb = bar("aaa")
    println("------- " + bbb)
  }
}
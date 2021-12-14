package chapter05

object Test14_MyTest {
  def main(args: Array[String]): Unit = {

    val mf = (n1: Int, n2: Int) => {
      n1 + n2
    }
    println(mf(2, 3))

    // 传统定义方法
    def add(a: Int, b: Int): Int = {
      a + b
    }

    // 简化定义
    def addSimple(a: Int, b: Int) = a + b

    println(add(1, 2))
    println(addSimple(3, 4))

    // 嵌套定义
    // 定义一个函数 func，它接收一个 Int 类型的参数，返回一个函数(记作 f1)。
    // 它返回的函数 f1，接收一个 String 类型的参数，同样返回一个函数(记作 f2)。
    // 函数 f2 接 收一个 Char 类型的参数，返回一个 Boolean 的值。
    // 要求调用函数 func(0) (“”) (‘0’)得到返回值为 false，其它情况均返回 true


    def func(i: Int): String => (Char => Boolean) = {
      def f1(s: String): Char => Boolean = {
        def f2(c: Char): Boolean = {
          if (i == 0 && s == "" && c == '0') false else true
        }

        f2
      }

      f1
    }

    def and(x: Boolean, y: Boolean) =
      if (x) y else false


    println(and(x = true, y = true))


    // 自定义while关键字
    // 自带while关键字
    var n = 10;
    //    while (n >= 1) {
    //      println(n)
    //      n -= 1
    //    }

    // 柯里化实现，定义一个函数，函数有2个参数，这两个参数都是传入的代码片段，返回值为Unit
    // 第一个参数传入的片段返回值为布尔类型，第二个参数传入的片段返回值为Unit类型
    // 递归调用该函数，直到条件变为false
    // 【=>类型】 这种格式特指代码块

    def mywhile(flag: => Boolean)(op: => Unit): Unit = {
      if (flag) {
        op
        mywhile(flag)(op)
      }
    }

    n = 10
    mywhile(n >= 1)({
      println(n)
      n -= 1
    })

    println("=======柯里化实现======")


    // 闭包实现
    def myWhile2(flag: => Boolean): (=> Unit) => Unit = {
      def loop(op: => Unit): Unit = {
        if (flag) {
          op
          myWhile2(flag)(op)
        }
      }

      loop _
    }

    println("=======函数作为参数======")


    def funcAsFuncParams(func: (Int, Int) => Int): Unit = {
      val c = func(8, 9)
      println(c)
    }

    def funcAsParams(a: Int, b: Int): Int = {
      a + b
    }

    funcAsFuncParams(funcAsParams)

    println("=======函数作为返回值======")


    def funcAsReturnParams(): (Int, Int) => Int = {
      def funcAsReturn(a: Int, b: Int): Int = {
        a + b
      }

      funcAsReturn
    }

    println(funcAsReturnParams()(6, 7))


    def myfun(fun : =>Int):Int={
      fun
    }

    myfun({
      println("我是一个代码块")
      12
    })
  }


}

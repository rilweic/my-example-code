package chapter05

object Test08_Practice {
  def main(args: Array[String]): Unit = {
    // 1. 练习1
    val fun = (i: Int, s: String, c: Char) => {
      if (i == 0 && s == "" && c == '0') false else true
    }

    println(fun(0, "", '0'))
    println(fun(0, "", '1'))
    println(fun(23, "", '0'))
    println(fun(0, "hello", '0'))

    println("1 ===========================")

    // 2. 练习2
    // 定义一个函数 func，它接收一个 Int 类型的参数，返回一个函数(记作 f1)。
    // 它返回的函数 f1，接收一个 String 类型的参数，同样返回一个函数(记作 f2)。
    // 函数 f2 接 收一个 Char 类型的参数，返回一个 Boolean 的值。
    // 要求调用函数 func(0) (“”) (‘0’)得到返回值为 false，其它情况均返回 true

    def func0(num:Int):String => Char =>Boolean= {
      (str:String)=>{
        (c:Char) =>{
          if(num == 0 && str == "" && c == '0') false else true
        }
      }
    }


    def func(i: Int): String => (Char => Boolean) = {
      def f1(s: String): Char => Boolean = {
        def f2(c: Char): Boolean = {
          if (i == 0 && s == "" && c == '0') false else true
        }

        f2
      }

      f1
    }

    def func3(i: Int): String => Char => Boolean = {
      (s: String) => {
        (c: Char) => {
          if (i == 0 && s == "" && c == '0') false else true
        }
      }
    }

    println(func(0)("")('0'))
    println(func(0)("")('1'))
    println(func(23)("")('0'))
    println(func(0)("hello")('0'))

    println("2 ===========================")

    // 匿名函数简写
    def func1(i: Int): String => (Char => Boolean) = {
      s => c => if (i == 0 && s == "" && c == '0') false else true
    }

    println(func1(0)("")('0'))
    println(func1(0)("")('1'))
    println(func1(23)("")('0'))
    println(func1(0)("hello")('0'))

    println("3 ===========================")

    // 柯里化
    def func2(i: Int)(s: String)(c: Char): Boolean = {
      if (i == 0 && s == "" && c == '0') false else true
    }

    println(func2(0)("")('0'))
    println(func2(0)("")('1'))
    println(func2(23)("")('0'))
    println(func2(0)("hello")('0'))
    println("4 ===========================")
  }
}

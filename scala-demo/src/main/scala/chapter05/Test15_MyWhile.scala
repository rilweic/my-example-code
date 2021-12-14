package chapter05

object Test15_MyWhile {
  def main(args: Array[String]): Unit = {

    // 自定义实现while功能

    // while 功能
    var n: Int = 10
    while (n > 0) {
      println(n)
      n -= 1
    }

    def whileFunc(condition: => Boolean): (=> Unit) => Unit = {

      op => {
        if (condition) {
          op
          whileFunc(condition)(op)
        }

      }
    }

    n = 20
    println("=====================")
    whileFunc(n>0){
      println(n)
      n -=1
    }

    println("=====================")
    // 柯里化
    def whileFunc2(condition : => Boolean)(op : =>Unit) : Unit = {
      if (condition) {
        op
        whileFunc2(condition)(op)
      }
    }

    n=10
    whileFunc2(n>0){
      println(n)
      n -=1
    }


  }
}


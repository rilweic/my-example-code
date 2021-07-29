package com.git.chapter06

/**
 * Created by lichao on 16-4-7.
 */
//类
class OOP {

}

//对象
object OOP {
  private var lastNumber = 0;

  def newUniqueNumber() = {
    lastNumber += 1;
    lastNumber
  }

  def main(args: Array[String]) {
    OOP.newUniqueNumber();
    print(OOP.lastNumber)

    println()

  }

  def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
}

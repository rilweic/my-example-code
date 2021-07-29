package com.git.chapter06

/**
 * 该类有2个成员变量，分子和分母
 * Created by lichao on 16-4-8.
 */

class Rational(numerator: Int, denominator: Int) {
  require(denominator != 0)

  //辅助构造方法
  def this(n: Int) = this(n, 1)

  //获取传入参数的最大公约数
  private val GREATEST_COMMON_DIVISOR = gcd(numerator.abs, denominator.abs) //获取最大公倍数，用来约分

  val numer: Int = numerator / GREATEST_COMMON_DIVISOR
  // 分子
  val denom: Int = denominator / GREATEST_COMMON_DIVISOR
  // 分母


  // 重写toString，必须加上override关键字
  override def toString = {
    if (denom == 1)
      numer.toString
    else
      numer + "/" + denom
  }

  //分数相加
  def add(that: Rational): Rational = {
    new Rational(
      numer * that.denom + that.numer * denom, //各自的分子乘以对方的分母
      denom * that.denom //分母相乘
    )
  }

  // 操作符形式的加法
  def +(that: Rational): Rational = {
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )
  }

  //操作符形式的乘法
  def *(that: Rational): Rational = {
    new Rational(
      numer * that.numer,
      denom * that.denom
    )
  }

  //操作符形式的减法
  def -(that: Rational): Rational = {
    new Rational(
      numer * that.denom - that.numer * denom,
      denom * that.denom
    )
  }

  //操作符形式的除法
  def /(that: Rational): Rational = {
    new Rational(
      numer * that.denom,
      that.numer * denom
    )
  }

  // 求倒数的前缀操作符，必须加上unary_修饰~
  def unary_~(that: Rational): Rational = {
    new Rational(
      denom, numer
    )
  }

  // 递归求分子分母的最大公约数，必须指明结果类型
  def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)

  // 有理数的比较：小于
  def lessThan(that: Rational) =
    numer * that.denom < that.numer * denom

  // 求两数中的较大数
  def max(that: Rational): Rational =
  //if(lessThan(that)) that else this //省略this也可以
    if (this lessThan that) that else this

}

object TestRational {
  def main(args: Array[String]) {
    val ra = new Rational(12, 36)
    val res = ra.gcd(12, 36)
    print(res)
  }
}

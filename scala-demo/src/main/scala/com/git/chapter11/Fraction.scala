package com.git.chapter11

class Fraction(n: Int, d: Int) {

  private val num = this.n
  private val den = this.d

  def *(other: Fraction) = new Fraction(num * other.num, den * other.den)

  override def toString = this.num+","+this.den
}

object Fraction extends App {
  def apply(n: Int, d: Int) = new Fraction(n, d)

  var result = Fraction(3, 4) * Fraction(5, 6)
  print(result.toString())
}
package com.git.chapter08

/**
 * Created by lichao on 16-4-14.
 */
class Square(var sideLength: Double) extends Shape {
  override def area(): Double = math.pow(sideLength, 2)
}

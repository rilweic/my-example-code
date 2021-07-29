package com.git.chapter08

/**
 * Created by lichao on 16-4-14.
 */
class Circle(var radius: Double) extends Shape {
  override def area(): Double = 3.14 * radius * radius
}


package com.git.chapter08

/**
 * Created by lichao on 16-5-25.
 */
object TypeCheck extends App {
  val circle = new Circle(2.0)
  if (circle.isInstanceOf[Square]) {
    circle.asInstanceOf[Square]
    println("circle is shape")
  } else {
    println("circle is not shape")
  }

  if (circle.getClass == classOf[Shape]) {
    circle.asInstanceOf[Square]
    println("circle is shape")
  } else {
    println("circle is not shape")
  }

}

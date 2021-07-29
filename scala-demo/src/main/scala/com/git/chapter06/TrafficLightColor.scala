package com.git.chapter06

/**
 * Created by lichao on 16-4-13.
 */
object TrafficLightColor extends Enumeration {
  type TrafficLightColor = Value
  /**
   * 等价下面的一条语句
   * val Red = Value
   * val Yello = Value
   * val Green = Value
   */
  val Red, Yellow, Green = Value

  def main(args: Array[String]) {
    //遍历枚举类中的所有属性
    for (i <- 0 until TrafficLightColor.maxId) print(TrafficLightColor(i) + "\t") //Red	Yello	Green
    println()
    for (j <- TrafficLightColor.values) print(j.id + "\t") //0	1	 2
    println()
    print(TrafficLightColor.withName("Red"))
  }

}


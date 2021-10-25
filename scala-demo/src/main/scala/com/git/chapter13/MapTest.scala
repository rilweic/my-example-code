package com.git.chapter13

import scala.collection.JavaConversions._

/**
 * Created by lichao on 16-5-27.
 */
object MapTest extends App {
  var obj = new MyMap
  //  var map :scala.collection.mutable.Map[String,String] = obj.getMap;
  val map: scala.collection.mutable.Map[String, String] = obj.getMap()
  for ((k, v) <- map) {
    println(k + " : " + v)
  }


  val list1 = List(5.0, 20, 9.95)
  val list2 = List(10, 2, 1)
  val pairs = list1.zip(list2)
  for (p <- pairs) print(p._1 * p._2 + "\t")
}

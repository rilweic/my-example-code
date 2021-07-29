package com.git.chapter05

import scala.beans.BeanProperty

/**
 * Created by lichao on 16-4-4.
 */
package OOP {

  class OOPInScala {

  }

class Person {
  private var name: String = _
  private var age: Int = 25
  private val gender = "mela"

  def getName = name

  def setName = (newName: String) => name = newName

  def getAge = age;

  def setAge(newAge: Int): Unit = {
    if (newAge > age) age = newAge
  }

  def getGender = gender
}

object OOPInScala {
  def main(args: Array[String]): Unit = {

    val p = new Person()
    val age = p.getAge
    print("Person Info " + age + " " + p.getAge)

    val student = new Student()
    student.getName

  }

  //辅助构造器
  class Student {
    @BeanProperty var name: String = _
    @BeanProperty var age: Int = _

    //辅助构造器  辅助构造器以this作为名称，必须先调用主构造器或其他辅助构造器
    def this(name: String) {
      this() //主构造器
      this.name = name
    }

    def this(name: String, age: Int) {
      this(name) //调用上一个辅助构造器
      this.age = age
    }

  }

  class Emp(var name: String, var age: Int, var salary: Int) {
    def this(name1: String, age1: Int) {
      this(name1, age1, 0)
    }

    def this() {
      this(null, 0, 0)
    }
  }

}

abstract class Person2 {
  val id: Int
  var name: String

}

class Person3(val id: Int) extends Person2 {
  var name = ""

}

}
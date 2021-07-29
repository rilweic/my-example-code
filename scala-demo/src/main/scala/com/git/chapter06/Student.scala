package com.git.chapter06

/**
 * Created by lichao on 16-4-21.
 */
class Student {
  var name: String = _
  //_表示占位符
  var age: Int = _
  var grade: String = _

  def this(name: String, age: Int, grade: String) {
    this()
    this.name = name
    this.age = age
    this.grade = grade
  }

  //重写父类的toString方法
  override def toString = "姓名:" + this.name + " 年龄：" + this.age + "" + " 班级:" + this.grade
}

object Student {
  def main(args: Array[String]) {
    val stu = new Student("lichao ", 25, "3班")
    stu.age = 24
    print(stu)

  }
}
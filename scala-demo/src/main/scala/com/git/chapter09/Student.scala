package com.git.chapter9

/**
 * Created by lichao on 16-4-15.
 */
/**
 * 伴生类和伴生对象
 * class Student是伴生对象Object Student的伴生类
 * object Student是伴生类class Student的伴生对象
 */


//伴生类
class Student(var name: String, var address: String) {

  private var phone = "110"

  //直接访问伴生对象的私有成员
  def infoCompObj() = println("伴生类中访问伴生对象：" + Student.sno)
}

//伴生对象
object Student {

  private var sno: Int = 100

  def incrementSno() = {

    sno += 1 //加1
    sno //返回sno
  }

  def main(args: Array[String]): Unit = {

    println("单例对象：" + Student.incrementSno()) //单例对象

    //实例化伴生类
    val obj = new Student("yy", "bj")
    obj.infoCompObj();
  }
}
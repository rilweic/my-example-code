package com.git.chapter05

import scala.beans.BeanProperty

/**
 * Created by lichao on 16-5-25.
 */
//类不用public修饰，所有类具有共有可见性
class Student {
  var age = 0
  private var name = ""
}

//对象 ，存放拥有的静态属性
object Main extends App {
  val stu = new Student();
  stu.age = 8
  //  stu.name="lisi"
}

object Student {
  val stu = new Student()
  stu.name = "lisi"
}

class Person {
  private var age = 0;
  var name = ""
  val sex = "male"
}

class Ref {
  val p = new Person()
  //  val age = p.age
  val name = p.name
  val sex = p.sex
  p.name = "lisi"
  //  p.sex = "female"

}

class Bean {
  @BeanProperty var name: String = _
}

class BeanTest {
  val bean = new Bean()
  bean.setName("gg")
  bean.getName
}

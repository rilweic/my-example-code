package com.git.chapter05

/**
 * Created by lichao on 16-5-25.
 */
class Construct(name: String, val age: Int) {

}

class RefConstruct() {
  val con = new Construct("lisi", 19)
  val age = con.age

}



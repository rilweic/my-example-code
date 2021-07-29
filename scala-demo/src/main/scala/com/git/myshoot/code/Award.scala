package com.git.myshoot.code

/**
 * Created by lichao on 16-4-14.
 *
 * 奖励类型
 */
trait Award {

  //获取奖励类型(0或1)
  def getType(): Int

}

object Award {
  val DOUBLE_FIRE = 0
  //双倍火力
  val LIFE = 1 //生命值
}
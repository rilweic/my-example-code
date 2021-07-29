package com.git.myshoot.code

import scala.util.Random

/**
 * Created by lichao on 16-4-14.
 *
 * 敌机，即是飞行物又是敌人
 */
class Airplane(var speed: Int = 2 /*敌机飞行速度*/) extends FlyingObject with Enemy {
  private var xSpeed = 1 //横向飞行速度

  /**
   * 获得分数
   * @return 分数
   */
  override def getScore = 5

  def this() {
    this(2) //先调用主构造器
    this.image = ShootGame.airplane
    width = image.getWidth()
    height = image.getHeight()
    y = -height
    val random = new Random()
    x = random.nextInt(ShootGame.WIDTH - width)
    xSpeed = random.nextInt(3)
    if (xSpeed <= 1) xSpeed = -1
  }

  /**
   * 步进操作
   */
  override def step: Unit = {
    y += speed
    x += xSpeed
    if (x > ShootGame.WIDTH - width) xSpeed = -1
    if (x < 0) xSpeed = 1
  }

  /**
   *
   * @return true 表示越界 false表示没越界
   */
  override def outOfBounds: Boolean = y > ShootGame.HEIGHT

  /**
   * 判断飞行物是否越界
   * @return true 表示没越界 false表示越界
   */
  override def notOutOfBounds: Boolean = !outOfBounds

}

package com.git.myshoot.code

import scala.util.Random

/**
 * 蜜蜂类，蜜蜂即是飞行物也是奖励
 * Created by lichao on 16-4-14.
 */
//这里定义列一个属性 awardType
class Bee(var xSpeed: Int /*横向速度*/ , var ySpeed: Int /*纵向速度*/ , var awardType: Int /*奖励类型*/) extends FlyingObject with Award {

  //获取奖励类型,重写父类的方法
  override def getType() = awardType

  //辅助构造器
  def this() {
    this(1, 3, 0)

    this.image = ShootGame.bee //初始化图片
    width = image.getWidth() //宽
    height = image.getHeight() //高
    y = -height

    val random = new Random() //随机数对象
    x = random.nextInt(ShootGame.WIDTH - width)
    awardType = random.nextInt(2)

  }

  /**
   * 步进操作
   */
  override def step: Unit = {
    x += xSpeed
    y += ySpeed

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

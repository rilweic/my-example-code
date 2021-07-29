package com.git.myshoot.code

/**
 * 子弹类，包含速度的属性
 * Created by lichao on 16-4-14.
 */
class Bullet(private val speed: Int /*子弹速度*/) extends FlyingObject {

  /**
   * 辅助构造方法
   * @param x x坐标
   * @param y y坐标
   */
  def this(x: Int, y: Int) {
    this(3) //子弹速度初始化为3
    this.x = x
    this.y = y
    this.image = ShootGame.bullet

  }

  /**
   * 步进操作
   */
  override def step: Unit = {
    y -= speed
  }

  /**
   *
   * @return true 表示越界 false表示没越界
   */
  override def outOfBounds: Boolean = y < -height

  /**
   * 判断飞行物是否越界
   * @return true 表示没越界 false表示越界
   */
  override def notOutOfBounds: Boolean = !outOfBounds
}

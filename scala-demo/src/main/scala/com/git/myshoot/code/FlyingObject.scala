package com.git.myshoot.code

import java.awt.image.BufferedImage

/**
 * 飞行物类，所有飞行物的父类
 * Created by lichao on 16-4-14.
 */
abstract class FlyingObject() {

  //自动生成getter setter方法
  // x坐标
  protected var x: Int = 0
  //如果不进行初始化，则子类中强制要求初始化
  //y坐标
  protected var y: Int = 0
  //图片宽
  protected var width: Int = 0
  //图片高
  protected var height: Int = 0
  //图片
  protected var image: BufferedImage = null

  /**
   * 步进操作，通过定时器实现的操作
   */
  def step: Unit

  /**
   * 判断是否被击中
   * @param bullet
   * @return 被击中 true
   *         未被击中 false
   */
  def shootBy(bullet: Bullet): Boolean = {
    val x = bullet.x
    val y = bullet.y
    //当子弹的x,y坐标在当前物体的范围内时就是被击中了
    this.x < x && x < this.x + width && this.y < y && y < this.y + height
  }

  /**
   * 使用了protected关键字后就不能直接访问x的位置了
   * @return x坐标
   */
  def getX(): Int = {
    x
  }

  def setX(x: Int): Unit = {
    this.x = x
  }

  def getY(): Int = {
    y
  }

  def setY(y: Int): Unit = {
    this.y = y
  }

  def getWidth(): Int = {
    width
  }

  def setWidth(width: Int): Unit = {
    this.width = width
  }

  def getHeight(): Int = {
    height
  }

  def setHeight(height: Int): Unit = {
    this.height = height
  }

  def getImage(): BufferedImage = {
    image
  }

  def setImage(image: BufferedImage): Unit = {
    this.image = image
  }

  /**
   * 判断飞行物是否越界
   * @return true 表示越界 false表示没越界
   */
  def outOfBounds: Boolean

  /**
   * 判断飞行物是否越界
   * @return true 表示没越界 false表示越界
   */
  def notOutOfBounds: Boolean
}

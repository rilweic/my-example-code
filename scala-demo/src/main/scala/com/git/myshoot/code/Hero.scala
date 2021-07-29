package com.git.myshoot.code

import java.awt.image.BufferedImage

/**
 * Created by lichao on 16-4-14.
 */
class Hero(protected var index: Int, private var doubleFire: Int, private var life: Int) extends FlyingObject {
  //定义固定长度的图形数组，就2张图片进行轮换
  protected var images = new Array[BufferedImage](2)

  def this() {
    this(0, 0, 3)
    this.image = ShootGame.hero0
    images(0) = ShootGame.hero0
    images(1) = ShootGame.hero1
    width = image.getWidth()
    height = image.getHeight()
    x = 150 //初始的英雄机的x坐标
    y = 400 //初始的英雄机的y坐标

  }

  /**
   * 步进操作，飞机图片轮换
   */
  override def step: Unit = {
    index += 1
    if (images.length > 0) image = images(index / 10 % images.length)
  }

  /**
   * 英雄机发射子弹的方法
   * @return 子弹的集合
   */
  def shoot(): Array[Bullet] = {
    val xStep = width / 4
    val yStep = 20
    if (doubleFire > 0) {
      val bullets = new Array[Bullet](2)
      bullets(0) = new Bullet(x + xStep, y - yStep) //左一条子弹发射位置
      bullets(1) = new Bullet(x + 3 * xStep, y - yStep) //右一条子弹发射位置
      doubleFire -= 2 //TODO
      bullets
    } else {
      val bullets = new Array[Bullet](1) //没有双倍的时候就发射一条子弹，单倍火力
      bullets(0) = new Bullet(x + 2 * xStep, y - yStep) // 子弹发射位置
      bullets
    }

  }

  /**
   * 英雄机移动操作
   * @param x 鼠标x坐标
   * @param y 鼠标y坐标
   */
  def moveTo(x: Int, y: Int) {
    this.x = x - width / 2
    this.y = y - height / 2
  }

  /**
   * 增加双倍火力
   */
  def addDoubleFire(): Unit = {
    doubleFire += 40
  }

  /**
   * 增加生命值
   */
  def addLife(): Unit = {
    life += 1
  }

  /**
   * 得到生命值
   * @return 生命值
   */
  def getLife = life

  /**
   *
   * @return true 表示越界 false表示没越界
   */
  override def outOfBounds: Boolean = false

  /**
   * 判断飞行物是否越界
   * @return true 表示没越界 false表示越界
   */
  override def notOutOfBounds: Boolean = true

  /**
   * 减命操作
   */
  def subStractLife(): Unit = {
    life -= 1
  }

  /**
   * 设置双倍火力
   * @param doubleFire
   */
  def setDoubleFire(doubleFire: Int) {
    this.doubleFire = doubleFire
  }

  def hit(other: FlyingObject): Boolean = {
    val x1 = other.getX() - this.width / 2
    val x2 = other.getX() + other.getWidth() + this.width / 2
    val y1 = other.getY() - this.height / 2
    val y2 = other.getY() + other.getHeight() + this.height / 2
    this.x + this.width / 2 > x1 && this.x + this.width / 2 < x2 &&
      this.y + this.height / 2 > y1 && this.y + this.width < y2
  }
}

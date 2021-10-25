package com.git.myshoot.code

import java.awt.image.BufferedImage
import java.awt.{Color, Font}
import java.util.{Timer, TimerTask}
import javax.imageio.ImageIO

import scala.collection.mutable.ArrayBuffer
import scala.swing.{Font, _}
import scala.swing.event.{MouseClicked, MouseExited, MouseEntered, MouseMoved}
import scala.util.Random

/**
 * Created by lichao on 16-4-14.
 */


class ShootGame extends Panel {
  /** 飞行物：敌机和小蜜蜂 */
  @volatile var flyings = ArrayBuffer[FlyingObject]()
  /** 子弹类 */
  @volatile var bullets = ArrayBuffer[Bullet]()
  /** 英雄机 */
  private var hero = new Hero()
  /** 分数 */
  var score = 0

  /**
   * panel面板方法重写
   * @param g 画笔
   */
  override def paint(g: Graphics2D): Unit = {
    g.drawImage(ShootGame.background, 0, 0, null) //画背景图
    paintHero(g)
    paintFlyingObjects(g)
    paintBullets(g)
    paintScore(g)
    paintState(g)
  }

  /**
   * 画飞机
   * @param g 画笔对象
   */
  def paintHero(g: Graphics2D): Unit = {
    g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null)
  }

  /**
   * 画子弹
   * @param g 画笔对象
   */
  def paintBullets(g: Graphics2D): Unit = {
    for (iter <- bullets.indices) {
      if (iter < bullets.length) {
        val b = bullets(iter)
        g.drawImage(b.getImage(), b.getX(), b.getY(), null)
      }
    }
  }

  /**
   * 画飞行物
   * @param g 画笔对象
   */
  def paintFlyingObjects(g: Graphics2D): Unit = {
    for (elem <- flyings if (elem != null)) g.drawImage(elem.getImage(), elem.getX(), elem.getY(), null)
  }

  /** 飞行物进场计数 */
  var flyEnteredIndex = 0

  /**
   * 每调用该方法40次将随机产生一个蜜蜂或敌机放入flyings数组中
   */
  def enterAction(): Unit = {
    flyEnteredIndex += 1
    //  400毫秒调用一次
    if (flyEnteredIndex % 40 == 0) {
      var obj = nextOne()
      flyings += obj // 将新生成的飞行物加入到飞行物数组中
    }
  }

  /**
   * 实现所有飞行物的移动操作
   */
  def stepAction(): Unit = {
    for (elem <- flyings) elem.step
    for (elem <- bullets) elem.step
    hero.step
  }

  // 创建定时器
  private var timer: Timer = null
  // 时间间隔 10毫秒
  private val interval = 10

  /**
   * 定时器
   */
  def action(): Unit = {
    //添加鼠標事件
    listenTo(mouse.moves) //监听鼠标移动事件
    reactions += {
      case MouseMoved(_, p, _) => {
        if (ShootGame.state == ShootGame.RUNNING) {
          hero.moveTo(p.x, p.y)
        }
      }
      case MouseEntered(_, _, _) => {
        if (ShootGame.state == ShootGame.PAUSE) ShootGame.state = ShootGame.RUNNING
      }
      case MouseExited(_, _, _) => {
        if (ShootGame.state != ShootGame.GAME_OVER) ShootGame.state = ShootGame.PAUSE
      }

    }
    listenTo(mouse.clicks) //监听鼠标点击事件

    reactions += {
      case e: MouseClicked => {
        if (ShootGame.state == ShootGame.START) {
          ShootGame.state = ShootGame.RUNNING
        } else if (ShootGame.state == ShootGame.GAME_OVER) {
          flyings = ArrayBuffer[FlyingObject]()
          bullets = ArrayBuffer[Bullet]()
          hero = new Hero()
          score = 0
          ShootGame.state = ShootGame.START
        }
      }
    }

    timer = new Timer()
    timer.schedule(new TimerTask {
      override def run(): Unit = {
        //如果状态为运行状态
        if (ShootGame.state == ShootGame.RUNNING) {
          enterAction()
          stepAction()
          shootAction()
          bangAction()
          //判断飞行物出界的操作
          outOfBoundsAction()
          checkGameOverAction()
          repaint() // 重绘
        }
      }

    }, interval, interval)


  }

  var shootIndex = 0 //射击计数
  /**
   * 发射子弹操作
   */
  def shootAction(): Unit = {
    shootIndex += 1
    if (shootIndex % 20 == 0) {
      var bs = hero.shoot()
      bullets ++= bs
    }
  }

  /**
   * 一群子弹打飞机
   */
  def bangAction(): Unit = {
    for (elem <- bullets) {
      val bullet = elem
      bang(bullet)
    }
  }

  /**
   * 一个子弹打飞机
   * @param bullet 子弹
   */
  def bang(bullet: Bullet): Unit = {
    var index = -1
    var flag = true // 循环标志变量
    val len = flyings.length
    for (i <- 0 until len if (flag)) {
      val obj = flyings(i)
      if (obj.shootBy(bullet)) {
        flag = false //循环标识设置为false
        index = i
      }
    }

    if (index != -1) {
      val one = flyings(index) //获取子弹击中的飞行物
      flyings.remove(index) // 将被子弹击中的飞行物从飞行物中删除
      if (one.getClass == classOf[Airplane]) {
        val s = one.asInstanceOf[Airplane].getScore
        score += s
      }

      if (one.getClass == classOf[Bee]) {
        val a = one.asInstanceOf[Bee]
        val t = a.getType()
        if (t == Award.DOUBLE_FIRE) hero.addDoubleFire() else hero.addLife()
      }
    }

  }

  /**
   * 画分数
   */
  def paintScore(g: Graphics2D): Unit = {
    val x = 10
    var y = 25

    font = new Font(Font.SANS_SERIF, Font.BOLD, 14)
    g.setColor(Color.RED)
    g.setFont(font)
    g.drawString("分数:" + score, x, y)
    y += 20
    g.drawString("生命:" + hero.getLife, x, y)

  }

  /**
   * 飞行物超出界限操作
   */
  def outOfBoundsAction(): Unit = {
    flyings = flyings.filter(_.notOutOfBounds) //保留没有越界的
    bullets = bullets.filter(_.notOutOfBounds) //保留没有越界的
  }

  /**
   * 判断游戏是否结束
   * @return true 游戏结束 false游戏继续
   */
  def isGameOver(): Boolean = {
    for (i <- 0 until flyings.length) {
      var index = -1
      if (i < flyings.length) {
        val obj = flyings(i)
        //检测英雄机和敌机是否碰撞
        if (hero.hit(obj)) {
          //减一条命
          hero.subStractLife()
          //将双倍火力设置为0
          hero.setDoubleFire(0)
          index = i
        }
        if (index != -1) {
          flyings.remove(index)
        }
      }
    }
    hero.getLife < 0
  }

  /**
   * 判断游戏是否结束
   */
  def checkGameOverAction(): Unit = {
    if (isGameOver()) ShootGame.state = ShootGame.GAME_OVER
  }

  /**
   * 画游戏状态
   * @param g
   */
  def paintState(g: Graphics2D): Unit = {
    if (ShootGame.state == ShootGame.START) g.drawImage(ShootGame.start, 0, 0, null)
    else if (ShootGame.state == ShootGame.PAUSE) g.drawImage(ShootGame.pause, 0, 0, null)
    else if (ShootGame.state == ShootGame.GAME_OVER) g.drawImage(ShootGame.gameover, 0, 0, null)
  }

  /**
   * 随机产生下一个飞行物
   * @return 飞行物，飞机或蜜蜂
   */
  def nextOne(): FlyingObject = {
    val random = new Random()
    val typ = random.nextInt(20)
    val res = if (typ == 0) new Bee() else if (typ == 9) new Airplane(6) else new Airplane()
    res
  }
}

object ShootGame {

  //界面宽
  final val WIDTH = 400;
  //界面高
  final val HEIGHT = 654;
  //开始状态
  final val START = 0
  //运行状态
  final val RUNNING = 1
  //暂停状态
  final val PAUSE = 2
  //结束状态
  final val GAME_OVER = 3
  //状态
  var state = 0

  val background: BufferedImage = ImageIO.read(ShootGame.getClass.getClassLoader.getResource("shootimg/background.png"))
  val airplane: BufferedImage = ImageIO.read(ShootGame.getClass.getClassLoader.getResource("shootimg/airplane.png"))
  val bee: BufferedImage = ImageIO.read(ShootGame.getClass.getClassLoader.getResource("shootimg/bee.png"))
  val bullet: BufferedImage = ImageIO.read(ShootGame.getClass.getClassLoader.getResource("shootimg/bullet.png"))
  val hero0: BufferedImage = ImageIO.read(ShootGame.getClass.getClassLoader.getResource("shootimg/hero0.png"))
  val hero1: BufferedImage = ImageIO.read(ShootGame.getClass.getClassLoader.getResource("shootimg/hero1.png"))
  val pause: BufferedImage = ImageIO.read(ShootGame.getClass.getClassLoader.getResource("shootimg/pause.png"))
  val gameover: BufferedImage = ImageIO.read(ShootGame.getClass.getClassLoader.getResource("shootimg/gameover.png"))
  val start: BufferedImage = ImageIO.read(ShootGame.getClass.getClassLoader.getResource("shootimg/start.png"))

}

/**
 * 主运行类
 */
object Main extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "scala版-打飞机"
    val shoot = new ShootGame()
    contents = shoot
    //设置界面大小
    size = new Dimension(ShootGame.WIDTH, ShootGame.HEIGHT)
    centerOnScreen() //界面居中
    shoot.action()
  }
}

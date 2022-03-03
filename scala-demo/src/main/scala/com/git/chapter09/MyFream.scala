package com.git.chapter09

import scala.swing._

/**
 * Created by lichao on 16-4-14.
 */
class MyFream {

}


object GUI_Panel extends SimpleSwingApplication {
  def top = new MainFrame {
    //重写框架
    title = "second GUI"
    //界面名称
    val button = new Button {
      //定义按钮
      text = "scala"
    }

    val label = new Label {
      //定义标签
      text = "Here is Spark!!!"
    }

    contents = new BoxPanel(Orientation.Vertical) {
      //定义格局
      contents += button //将按钮和标签加入格局中
      contents += label
      border = Swing.EmptyBorder(100, 100, 50, 50) //设置边界
    }
  }


}
package com.git.chapter06

/**
 * Created by lichao on 16-4-13.
 */
/**
 * 抽象类
 * @param description
 */
abstract class UndoableAction(val description: String) {
  //抽象类类中有个变量为description
  def undo(): Unit

  def redo(): Unit
}

class DonothingAction extends UndoableAction("Do nothing") {
  override def undo() {}

  override def redo() {}
}

package com.git.chapter09

/**
 * Created by lichao on 16-4-14.
 */

import scala.swing._
import scala.swing.event._

object SwingApp extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "SwingApp"
    var numclicks = 0

    object label extends Label {
      val prefix = "Number of button clicks: "
      text = prefix + "0  "
      listenTo(button)
      reactions += {
        case ButtonClicked(button) =>
          numclicks = numclicks + 1
          text = prefix + numclicks
      }
    }

    object button extends Button {
      text = "I am a button"
    }

    contents = new FlowPanel {
      contents.append(button, label)
      border = Swing.EmptyBorder(5, 5, 5, 5)
    }
  }
}


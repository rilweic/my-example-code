package com.git.chapter09

/**
 * Created by lichao on 16-4-14.
 */

import scala.swing._
import java.io.File
import scala.swing.event.ButtonClicked
import scala.swing.Label

object UI extends SimpleSwingApplication {
  val chooser = new FileChooser(new File("."))
  chooser.title = "chooser"
  val button = new Button {
    text = "Choose a File"
  }
  val label = new Label {
    text = "No file selected yet."
  }
  val mainPanel = new FlowPanel {
    contents += button
    contents += label
  }

  def top = new MainFrame {
    title = "Simple GUI"
    contents = mainPanel

    listenTo(button)

    reactions += {
      case ButtonClicked(b) => {
        val result = chooser.showOpenDialog(mainPanel)
        if (result == FileChooser.Result.Approve) {
          label.text = chooser.selectedFile.getPath()
        }
      }
    }
  }
}

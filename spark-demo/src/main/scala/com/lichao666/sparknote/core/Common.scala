package com.lichao666.sparknote.core

import java.util.Date

object Common {
  def currentDateTime: String = {
    val now = new Date()
    val dateFormat = new java.text.SimpleDateFormat("yyyy_MM_dd__HH_mm_ss")
    dateFormat.format(now)
  }

  def defaultOutputPath: String = {
    "output".concat("/").concat(currentDateTime)
  }
}

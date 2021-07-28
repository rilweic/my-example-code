package com.git.chapter1

import java.util.Scanner
import scala.util.control.Breaks._

object ControlStruct {

  def main1(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in);
    var input: BigInt = null;
    breakable {
      while (true) {
        input = scanner.nextInt();
        if (input > 0) {
          print("大于0");
        } else if (input < 0) {
          print("小于0");
        } else {
          break();
        }
      }

    }

  }
}

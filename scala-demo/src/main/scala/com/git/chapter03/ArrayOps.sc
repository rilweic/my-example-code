import scala.io.Source

val arr = Array(1, 2, 4, 54, 21)
for (elem <- arr) {
  print(elem + " ")
}

//var file = Source.fromFile("/home/hadoop/coding/cc.xxx")
var file = Source.fromURL("http://www.baidu.com")
for (line <- file.getLines()) {
  println(line.length + "\t" + line + "\t")
}
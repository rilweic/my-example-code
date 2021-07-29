val left = List(1, 2, 3)
val right = List(4, 5, 6)

//下面四个方法一样的
val all1 = left ++ right

val all2 = left ++: right

val all3 = right.++:(left)

val all4 = right.:::(left)

val leftadd = 0 +: left

val rightadd = left.+:(0)

val rightadd2 = left :+ 4

val rightadd3 = left.:+(4)

val leftadd1 = 0 :: left

val leftadd2 = left.::(0)

val double = leftadd2.map(_ * 2)
//将匿名函数赋值
val squre = (x: Int) => x * x

val squre2 = double.map(squre)

val squre3 = double.map(math.pow(_, 2))

val squre4 = double.map((x: Int) => x * x)

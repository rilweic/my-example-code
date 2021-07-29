val digits = List(1, 2, 3, 4, 5)
9 :: List(4, 2)
digits.head
digits.tail
digits.tail.head
digits.tail.tail
9 :: 4 :: 2 :: Nil

def sum(lst: List[Int]): Int = if (Nil == lst) 0 else lst.head + sum(lst.tail)
sum(digits)

digits.sum

def p1: PartialFunction[Int, Int] = {
  case x if x > 1 => 1
}

//operation 1:
val freq = scala.collection.mutable.Map[Char,Int]()
for (c <- "Mississippi") freq(c) = freq.getOrElse(c, 0) + 1

//operation 2:
(Map[Char,Int]()/:"Mississippi"){
  (m,c)=>m+(c->(m.getOrElse(c,0)+1))
}

(1 to 10).scanLeft(0)(_ + _)

val scores = Map("lichao" -> 99, "rilweic" -> 98)
scores("lichao")
val gg = scores.getOrElse("lichao", 0)

val price = Array(5.0, 20, 9.95)
val quantities = Array(10, 2, 1)
//拉链操作
val zips = price.zip(quantities)
for ((p, q) <- zips) Console.print(p * q)

val list1 = List(5.0, 20, 9.95)
val list2 = List(10, 2, 1)
val pairs = list1.zip(list2)
pairs.map(p => p._1 * p._2)


def numsFrom(n: BigInt): Stream[BigInt] = n #:: numsFrom(n + 1)
val tenOrmore = numsFrom(1) //(1,?)
tenOrmore.tail.tail.tail //(4,?)
//stream is lazy execute,view is used to make it lazy

val powers = (0 until 100).view.map(Math.pow(10, _))

var coll = (1 to 123).toList
coll.par.sum


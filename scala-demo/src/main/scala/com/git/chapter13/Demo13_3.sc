/**
 * reduce operation
 * 定义一个变换f, f把两个列表的元素合成一个，遍历列表，最终把列表合并成单一元素
 */

val nums = List(1, 2, 3)
val sum1 = nums.reduce((a, b) => a + b)
val sum2 = nums.reduce(_ + _)
val sum3 = nums.sum

/**
 * reduceLeft,reduceRight

 * reduceLeft: reduceLeft[B >: A](f: (B, A) ⇒ B): B

 * reduceRight: reduceRight[B >: A](op: (A, B) ⇒ B): B
 */

val nums2 = List(2.0, 2.0, 3.0)
// pow( pow(2.0,2.0) , 3.0) = 64.0
val resultLeftReduce = nums2.reduceLeft(math.pow)
// pow(2.0, pow(2.0,3.0)) = 256.0
val resultRightReduce = nums2.reduceRight(math.pow)

val nums_head = nums2.head //head is only one
val nums_tail = nums2.tail //tail is list except head

/**
 * fold,foldLeft,foldRight

 * fold: fold[A1 >: A](z: A1)(op: (A1, A1) ⇒ A1): A1 带有初始值的reduce,从一个初始值开始，从左向右将两个元素合并成一个，最终把列表合并成单一元素。

 * foldLeft: foldLeft[B](z: B)(f: (B, A) ⇒ B): B 带有初始值的reduceLeft

 * foldRight: foldRight[B](z: B)(op: (A, B) ⇒ B): B 带有初始值的reduceRight
  */
val nums3 = List(2,3,4)

val sum = nums3.fold(1)((a,b)=>a+b)
val sum4 = nums3.fold(1)(_+_)

val nums4 = List(2.0,3.0)
val result1= nums4.foldLeft(2.0)(math.pow)
val result2 = nums4.foldRight(1.0)(math.pow)

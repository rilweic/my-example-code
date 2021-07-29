/**
 * sortBy,sortWith,sorted

 * sortBy: sortBy[B](f: (A) ⇒ B)(implicit ord: math.Ordering[B]): List[A] 按照应用函数f之后产生的元素进行排序

 * sorted： sorted[B >: A](implicit ord: math.Ordering[B]): List[A] 按照元素自身进行排序

 * sortWith： sortWith(lt: (A, A) ⇒ Boolean): List[A] 使用自定义的比较函数进行排序
 *
 * Sorts this $coll according to a comparison function.
 */

val nums = List(3, 2, 5, 4)
val sortedList = nums.sorted;
//tuple
val users = List(("HomeWay", 25), ("XSDYM", 23))
val sortedByAge = users.sortBy { case (user, age) => age } //List(("XSDYM",23),("HomeWay",25))
val sortedWith = users.sortWith { case (user1, user2) => user1._2 < user2._2 } //List(("XSDYM",23),("HomeWay",25))


/**
 * 7.filter, filterNot

 * filter: filter(p: (A) ⇒ Boolean): List[A]

 * filterNot: filterNot(p: (A) ⇒ Boolean): List[A]

 * filter 保留列表中符合条件p的列表元素 ， filterNot，保留列表中不符合条件p的列表元素
 *
 **/

val filterNums = List(1, 2, 3, 4, 5, 6)
val odd = filterNums.filter(_ % 2 != 0) // odd
val odd2 = filterNums.filter((x: Int) => x % 2 == 0)
def p(x: Int): Boolean = {
  x % 2 == 0
}

val odd3 = filterNums.filter(x => x % 2 == 0)
val odd4 = filterNums.filter(p)
val even = filterNums.filterNot(_ % 2 != 0) //even
val even2 = filterNums.filter(_ % 2 == 0) //even

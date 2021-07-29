//http://blog.csdn.net/pzw_0612/article/details/45936165
val text = List("Homeway,25,Male", "XSDYM,23,Female")
//将list中的每个进行分割，并取第一个
val usersList = text.map(_.split(",")(0))

val usersWithAgeList = text.map(line => {
    val fields = line.split(",")
    val user = fields(0)
    val age = fields(1).toInt
    val sex = fields(2)
    (user,age,sex)
})

val text2 = List("A,B,C","D,E,F")
val textMapped = text2.map(_.split(",").toList)
val iter = textMapped.iterator

while(iter.hasNext){
    println(iter.next())
}
//flatten: flatten[B]: List[B] 对列表的列表进行平坦化操作
val textFlattened = textMapped.flatten

val textFlatMapped = text2.flatMap(_.split(","))
println(textFlattened)
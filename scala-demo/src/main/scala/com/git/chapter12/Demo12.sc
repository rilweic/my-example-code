import scala.math._

println("------作为值的函数------")
val num = 3.14
//向上取整
val fun = ceil _
fun(num)
println("------参数为函数的函数------")
//定义一个函数，传入一个（函数参数）
// 真正执行的是传入的函数，返回 double类型
def numberTriple(f: (Double) => Double) = f(3)
//会被调用的函数
def function(num: Double): Double = 3 * num
numberTriple(function _)
numberTriple(sqrt _) //传入求平方根的函数，将11行的f(3)作为被求的函数
//匿名函数，直接传入numberTriple需要的函数，直接定义的匿名函数
numberTriple((num: Double) => num * 4)
println("------返回结果也是函数的函数,传入int类型，接上double类型------")
def add(fac: String) = (x: Double) => fac + " + " + x
val jia = add("2")
jia(3)
println("------参数是函数，返回值也是函数的函数")
def fun1(x: Double) = x + "i am fun1"
def fun2(f: (Double) => String) = (y: Double) => y + " i am fun2"
println("调用参数是函数，返回类型也是函数的函数")
val res101 = fun2(fun1)
res101(11)
//返回值为函数
println("返回值为函数")
def dd(x:Int,y:Int) = (z:Double) => x+y+z
val re_s = dd(2,3)(5)
/**柯里化*/
println("柯里化")

def mul(x:Int,y:Int) = x+y;
val f_mul = mul(2,3)
def mul2(x:Int) = (y:Int) => x+y
val f_mul2  = mul2(2)(7)
def mul3(x:Int)(y:Double) = x*y
val  f_mul3 = mul3(3)(4)


numberTriple((x: Double) => x * 3) // 完整写法
numberTriple((x) => x * 3) // 已知参数类型，可以省掉Double
numberTriple(x => x * 3) // 只有一个参数时，可以省去()
numberTriple(_ * 3) // 参数只在右侧出现一次，可以用_替换

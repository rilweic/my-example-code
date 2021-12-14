package chapter09plus

object Test03_Generics {
  def main(args: Array[String]): Unit = {
    // 1. 协变和逆变
    val child: Parent = new Child
//    val childList: MyCollection[Parent] = new MyCollection[Child]
    val childList: MyCollection[SubChild] = new MyCollection[Child]

    // 2. 上下限
    def test[A <: Child](a: A): Unit = {
      println(a.getClass.getName)
    }

    test[SubChild](new SubChild)
  }
}

// 定义继承关系
class Parent {}
class Child extends Parent {}
class SubChild extends Child {}

// 定义带泛型的集合类型
class MyCollection[-E] {}

/**
 * 1,scala的类和方法、函数都可以是泛型。
 *

 * 2,关于对类型边界的限定分为上边界和下边界（对类进行限制）
 * 上边界：表达了泛型的类型必须是"某种类型"或某种类型的"子类"，语法为“<:”,
 * 下边界：表达了泛型的类型必须是"某种类型"或某种类型的"父类"，语法为“>:”,
 *

 * 3, "<%" :view bounds可以进行某种神秘的转换，把你的类型在没有知觉的情况下转换成目标类型，
 * 其实你可以认为view bounds是上下边界的加强和补充，语法为："<%"，要用到implicit进行隐式转换（见下面例子）
 *

 * 4,"T:classTag":相当于动态类型，你使用时传入什么类型就是什么类型，（spark的程序的编译和运行是区分了Driver和Executor的，只有在运行的时候才知道完整的类型信息）
 * 语法为："[T:ClassTag]"
 *

 * 5,逆变和协变：-T和+T（下面有具体例子）+T可以传入其子类和本身（与继承关系一至）-T可以传入其父类和本身（与继承的关系相反），
 *

 * 6,"T:Ordering" :表示将T变成Ordering[T],可以直接用其方法进行比大小,可完成排序等工作
 */
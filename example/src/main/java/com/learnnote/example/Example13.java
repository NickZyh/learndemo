package com.learnnote.example;

/**
 * Created by Zyh on 2019/9/4.
 */
@SuppressWarnings("all")
public class Example13 {

    /**
     * final不会影响修饰属性在内存中的位置,内存分配位置只与static有关
     * final修饰的属性一定要在对象构造完成之前赋值完毕,否则会报错;可以
     * 直接在成员变量中赋值,也可以在构造方法中赋值.
     * 1 直接在成员变量赋值
     * 2 构造代码块赋值
     * 3 构造方法中赋值
     * 以上三个只能在一个地方调用,因为final修饰的属性不能被修改
     */

    // 此处是为了证实直接赋值的成员变量是在什么是否被赋值的;事实证明,直接声明赋值的属性会在
    // 初始化零值时被赋值.
    private final int index1 = 0;
    private final int index2;

    /**
     * 对象创建有四个步骤：
     *  1 类加载检查
     * 	2 分配内存
     * 	3 初始化零值
     * 	4 设置对象头
     * 	5 执行init方法
     * 	这里的init方法是由构造代码块和构造方法一起构成的,并且,构造代码块会在构造方法前执行
     * 	从虚拟机的视角来看，执行到第四步的时候一个新的对象已经产生,只不过此时这个对象的所有
     * 	成员变量都为初始化值,所以此处输出0和1两个结果;另外,之所以能在init方法中调用成员方
     * 	法这是因为JVM层面对象已经产生,所以成员方法可以被调用.
     *
     * 	final的语义是一旦被初始化过的值就无法再次赋值.初始化指的就是对象中的数据已不是初始化
     * 	零值了.
     */
    // 构造代码块的赋值
//    {
//        // 执行结果：0 1
//        method1();
//        this.index = 1;
//        method1();
//    }

    // 构造方法的赋值
    Example13() {
        // 执行结果：0 1
        method1();
        // index1由于已经被赋值过了(初始化完成),所以无法再赋值
        // this.index1 = 2;
        this.index2 = 1;
        method1();
    }

    void method1() {
        System.out.println("index1:" + index1 + ";" + "index2:" + index2);
    }

    public static void main(String[] args) {
        Example13 example13 = new Example13();
    }
}

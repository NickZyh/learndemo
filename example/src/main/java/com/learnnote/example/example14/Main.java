package com.learnnote.example.example14;

/**
 * Created by Zyh on 2019/9/9.
 */
public class Main {

    public static void main(String[] args) {
        // 抽象类不能直接调用构造函数被初始化;但是它可以定义构造方法
        // Person person = new Person();

        // 可以通过创建子类对象,然后调用抽象类构造方法来赋值,然后访问父类属性.
        // 相当于间接创建了一个抽象类对象.
        // 所以,抽象类不能直接new对象,但是可以通过子类调用父类构造方法来创建出抽象类
        // 对象.
        /**
         * 抽象类其实是可以被实例化的，但是它的实例化方式并不是通过普通的new方式来创建对象，而是通过
         * 父类的应用来指向子类的实例间接地实现父类的实例化，因为子类在实例化之前，一定会先实例化它的
         * 父类。这样创建了继承抽象类的子类对象，也就把其父类（抽象类）给实例化了
         */

        Teacher teacher = new Teacher("张三", 20, "老师", "教书");
        System.out.println(teacher.toString());
        teacher.doSomething();
    }
}

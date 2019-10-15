package com.learnnote.example;

import lombok.extern.slf4j.Slf4j;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Zyh on 2019/9/19.
 */
@SuppressWarnings("all")
@Slf4j
public class Example16 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }

        /**
         * .filter(str -> { ... }) : 对list变成的流进行过滤
         * .collect(Collectors.toList()) : 将stream重新变成流
         *
         */
        list.stream().filter(str -> {
            // filter的return的意思是对stream中的所有元素都进行遍历,然后留下所有返回true的元素,返回false的元素都会被过滤掉.
            // 备注：即使某个元素返回了一次,但是接下来的每一个元素都会执行一次return
            // return str.equals("1") || str.equals("2");  // 输出结果 ： 1 2
            return false; // 什么都不输出
        }).forEach(System.out::println);

        // allMatch:从流中的第一个元素开始匹配,如果第一个元素执行后返回true,那么就继续遍历第二个...一直遍历到
        // 第n个返回false的元素;那么匹配结束,返回false.如果整个流都被遍历完,并且每次都返回true,则最终结果返回true.
        // 这个方法的作用是判断整个集合所有元素在执行条件后返回true.只要有一个返回了false则返回false,都满足的话则返回true.
        // 与之相对应的是anyMatch,整个集合只要有一个元素在执行条件后返回true,那么它就直接返回true.
        // 还有一个noneMatch,它与allMatch是相反的.整个集合只要有一个元素在执行条件后返回true,那么它就直接返回false.但是只要
        // 所有的都返回false,那么它最终返回true.
        // allMatch：所有元素都符合条件
        // anyMatch：只要有一个符合条件
        // noneMatch：都不符合条件
        System.out.println(list.stream().allMatch(str -> {
            // 输出结果： 0 1  因为0返回true,1返回false;所以整体返回false.
            System.out.println(str);
            return str.equals("0");
        }));
    }
}
/**
 * 由于只能根据时间段获取到所有的航班,所以我们需要过滤所有的航班来拿到符合pnr航班号的航班信息,然后根据这个航班信息去拿到这个航班对应的政策.
 * 所以我们要做的事情,遍历所有航班的信息,拿到查出来的航班的数据,然后去过滤掉所有与pnr航班号不相等的数据,最终获取到航班信息.
 * 尝试改成 anyMatch试一下
 *
 * 这段代码的意思是：先遍历IBE中的数据.由于IBE中的每一条数据都有多个航班信息,所以
 * IBE不被过滤掉的条件是它里面的List<AirTicketLegs>的每一条数据的航班号都与pnr的
 * 航班号相等,这个IBEBean才不会被过滤掉,否则都会被过滤掉.
 * 至于那两个往返程判断的差别仅仅是pnr中的两个航班号的区别.
 *
 */

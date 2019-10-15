package com.nio.buffer;

/**
 * Created by Zyh on 2019/9/8.
 */
public class NioExample4 {

    /**
     * 看源码的备注：
     *      如果继承了父类,那么就先去看父类的构造方法;如果调用了父类的构造方法则去查看;
     *      如果显示的调用了父类的构造方法,那么就说明肯定会去调用或者使用父类中的元素或
     *      方法.面向对象的基本思想.
     *
     * Buffer的三个核心概念
     * position:
     *      the index of the next element to be read or written.
     *          - 下一个被读或被写的元素的索引
     *      never negative and is never greater than its limit.
     *          - 不会为负数,不会超过limit
     * limit:
     *      the index of the first element that should not be read or written.
     *          - 第一个不应该(不能)被读和被写的元素的索引
     *      never negative and is never greater than its capacity.
     *          - 不会为负数,以及不会超过capacity
     * capacity:
     *      the number of elements it contains.
     *          - 能放置的容量数,最大容量
     *      The capacity of a buffer is never negative and never changes.
     *          - 不会变化(change)和不会为负数(negative)
     *
     * 初始化时三个元素的指向 - 初始化一个长度为6的buffer
     * capacity的值被初始化为6.由于索引位置是从0开始的,所以capacity的值指向最后一个元素的
     * 下一个位置(虚拟的).
     * 初始化时,limit的位置和capacity的位置是一样的,也可能为0,这取决于如何创建buffer.
     * 任意时刻,position都指向下一个将要被读或写的元素.所以此时position指向索引为0的位置.
     *
     * 向buffer中写入两个元素
     * position:此时为写状态,指向下一个元素位置,所以position指向索引为2的地方.
     * capacity和limit此时不变.
     *
     * 调用flip()将读写状态进行转换,源码如下：
     * public final Buffer flip() {
     *      // 将limit的位置修改为position的位置.就相当于记录下当前buffer中可以被操作的
     *      // 元素个数,相当于一个限制.
     *         limit = position;
     *      // 将position的位置置为0.position不管读或者写,都会指向下一个将要被读写的元素,
     *      // 所以说不论是读或者写,当改变状态(flip)时,都会重新进行.也就是一次操作要完整执行完.
     *         position = 0;
     *         mark = -1;
     *         return this;
     * }
     *  理解：
     *  limit表示的是buffer中可以操作的元素个数.每次读或写元素时,都是改变position的值,相当于
     *  一个操作的动态索引.
     *  position <= limit <= capacity
     *
     *  标记和重置 - mark和reset
     *  假设此时读取到第五个元素,此时调用一个mark.然后继续读第6,第7等等元素.此时调用reset的话
     *  就会将position的位置修改为mark,然后重新读取.
     *  mark属性是一个类似于标记的元素,可以通过mark()方法将position的值赋给mark,用于记录
     *  position.mark只会与position有关,并且mark<=position.mark主要就是用于记录一下
     *  此时position的操作位置.mark的值初始化为-1(未被定义的mark的值就是-1).
     *
     *  relative:相对读或者写,会改变position的位置.
     *  absolute:绝对读,以绝对位置来进行读或写操作.
     *
     *  clear,flip,rewind
     *  clear: makes a buffer ready for a new sequence of channel-read or relative
     *  put operations:It sets the limit to the capacity and the position to zero.
     *      - 将buffer设置为初始化状态
     *  flip: makes a buffer ready for a new sequence of channel-write or relative
     *  get operations: It sets the limit to the current position and then sets the
     *  position to zero.
     *      - 读写状态的转化
     *  rewind: makes a buffer ready for re-reading the data that it already contains:
     *  It leaves the limit unchanged and sets the position to zero.
     *      - 重新读取.limit值保持不变,position值设置为0.
     *
     *  Thread safety - buffer并不是线程安全的
     *  Buffers are not safe for use by multiple concurrent threads.If a buffer
     *  is to be used by more than one thread then access to the buffer
     *  should be controlled by appropriate synchronization.
     *
     *
     *
     */
}

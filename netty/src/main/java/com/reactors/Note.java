package com.reactors;

public class Note {

    /**
     * 什么是Reactor模型：是一个事件驱动的设计模式。用于处理一个或多个输入同时传递给服务处理器的
     * 请求的事件处理模式。它根据请求产生的不同事件,通过分发交给不同的处理器进行处理。
     * 所以,Reactor就是一个 接收 - 分发 - 触发handler处理的一个模型。
     *
     * 前提：当某个请求到来时,会产生对应的事件。这个事件可以理解为一个标识符,表示发生了。
     * 流程：请求到来 -> selector监听到请求到来时有事件产生 -> 获取到事件,通过分发器分发给对应的处理器.
     * 分发给对应的处理器是因为selector中将不同请求与不同事件进行绑定。不同的事件有不同的处理器。
     *
     * wakeup()：
     * 某个线程调用select()方法后阻塞了，即使没有通道已经就绪，也有办法让其从select()方法返回。只要让其
     * 它线程调用对应Selector.wakeup()方法,那么阻塞在select()上的线程会立马返回。
     * 如果有其它线程调用了wakeup()方法，但当前没有线程阻塞在select()方法上,下一个(wakeup只作用一次)调
     * 用select()方法的线程将不会阻塞,而是直接返回。
     * 原理：NIO中的Selector封装了底层的系统调用，其中wakeup用于唤醒阻塞在select方法上的线程，它的实现
     * 很简单，在linux上就是创建一 个管道并加入poll的fd集合，wakeup就是往管道里写一个字节，那么阻塞的poll
     * 方法有数据可读就立即返回。
     * 这个方法主要用于刷新的作用,因为假设select()正在阻塞,现在我往selector中加入一个socket,如果不将select()
     * 唤醒,则它仍然是监听的之前的socket,这是因为之前是被阻塞等待的,无法监听到新加入或修改的socket
    *
     * jdk-nio的bug：https://www.jianshu.com/p/3ec120ca46b2
     *     正常情况下，selector.select()操作是阻塞的，只有被监听的fd有读写操作时才被唤醒.但是，在这个
     *     bug中，没有任何fd有读写请求，但是select()操作依旧被唤醒.很显然，这种情况下，selectedKeys()
     *     返回的是个空数组,然后按照逻辑执行到while(true)处，循环执行，导致死循环(这个bug一旦产生,将持续
     *     发生,所以导致死循环)。
     *
     *  Reactor的三种角色：
     *      1 Reactor：将I/O事件分派给对应的Handler
     *      2 Acceptor：处理客户端新连接，并分派请求到处理器链中
     *      3 Handlers：处理对应事件的逻辑
     *
     * 单Reactor单线程模型：指的是 accept,read,dispatcher,handler 所有的操作都在一个线程上执行.
     *      缺点：当请求较多的时候,很显然单线程无法处理过来.
     * 备注：单Reactor的意思是只有一个线程进行接收和分页.
     *
     * 单Reactor多线程模型：这种模型将处理请求利用多线程进行处理.在处理业务逻辑即获取到IO的读写事件,得到
     * channel中的数据之后,具体业务逻辑交由线程池来处理。这样可以减小主reactor的性能开销(不负责处理)，更
     * 专注的做事件分发，提升整个应用的吞吐.
     *      问题：为什么不把接收Socket也提取出来用多线程处理？
     *
     * 多Reactor多线程模型：这种模型将Reactor分为两个部分,一部分专门用于负责接收accept,这部分也叫boss.
     * 另一部分用于分发,这部分叫做worker.一般bossReactor只有一个,subReactor有多个,用来处理接收并创建
     * Socket,读取Socket数据,然后交给每个subReactor的线程池进行处理。
     * mainReactor(boss)：负责监听ServerSocketChannel,当有连接事件发生时,将该事件交给subReactor进行
     * 处理.
     * subReactor(worker):subReactor负责Socket的创建,读取数据,以及交给每个subReactor独有的线程池处理.
     * 每个subReactor单独维护一个selector.
     */
}

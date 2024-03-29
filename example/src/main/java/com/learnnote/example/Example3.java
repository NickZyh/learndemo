package com.learnnote.example;

/**
 * @Author nick.zhou
 * @Date 2019/7/24 11:26
 * @Description <p>TODO</p>
 **/
public class Example3 {
    public static void main(String[] args) {
        /**
         * 移位运算后，数的大小会变成原来的【进制数^移动位数】倍，其中以左移位数为正，右移位数为负。所以左移可以代替乘法(扩大多少倍)，
         * 右移可以代替除法(缩小多少倍，因为此时移动位数为负)。究其原因是因为进制数是以位权定义的，如果左移，则数据会向高位移动，每
         * 一位的位权值增大，数则增大。右移则向低位移动，每一位的位权值变小，数则缩小
         *
         * 1 请用最有效率的方法计算出2乘以8等于几？
         * 解答：2 << 3
         * 2 请用最有效率的方法计算出2乘以7等于几?
         * 解答：2 << 3 - 2
         */
        int number = 5;
        int number1 = -5;
        // 0 0000000 00000000 00000000 00000101
        printBinaryString(number);
        // 1 1111111 11111111 11111111 11111011 从这里也可以看出,存储负数是以补码进行存储的
        printBinaryString(number1);

        // 左移,除符号位外,其余所有位数同时向高位移动,低位补0
        // 0 0000000 00000000 00000000 00001010
        printBinaryString(number << 1);
        // 符号位不动
        // 1 1111111 11111111 11111111 11110110
        printBinaryString(number1 << 1);

        // 右移,除符号位外,其余所有位数同时向低位移动,高位补符号位
        // 0 0000000 00000000 00000000 00000010
        printBinaryString(number >> 1);
        // 1 1111111 11111111 11111111 11111101
        printBinaryString(number1 >> 1);

        // 无符号右移(符号位一起右移),高位补0,也称逻辑右移,没有逻辑左移
        // 0 0000000 00000000 00000000 00000010
        printBinaryString(number >>> 1);
        // 0 1111111 11111111 11111111 11111101
        printBinaryString(number1 >>> 1);
    }

    private static void printBinaryString(int num) {
        System.out.println(Integer.toBinaryString(num));
    }

    /**
     * 5和-5(int型)的二进制码(原码)如下：
     *  5：00000000 00000000 00000000 00000101
     * -5：10000000 00000000 00000000 00000101  备注：最高位为1,表示负数
     * 原码的弱点：
     *      1 有2个0,即+0和-0（10000000和00000000）
     *      2 进行异号相加或同号相减时,先要判断2个数的绝对值大小(除最高位的二进制码),然后进行加减操作,最后运算结果的符号还要与大的符号相同
     *
     * 正数无反码,负数的反码为对该数的原码 除符号位外 其余的每一位取反[每一位取反(除符号位)]。
     *      正数
     *          原码：00000000 00000000 00000000 00000101
     *          反码：00000000 00000000 00000000 00000101
     *      负数
     *          原码：10000000 00000000 00000000 00000101
     *          反码：11111111 11111111 11111111 11111010
     *      反码是相互的，所以也可称：
     *          10000000 00000000 00000000 00000101 和
     *          11111111 11111111 11111111 11111010 互为反码
     *  反码的弱点：还是有+0和-0,没过多久，反码就成为了过滤产物,也就是,后来补码出现了
     *
     *  补码基于反码而来,正数的补码与原码相同,负数的补码为对该数的原码除符号位外各位取反,然后在最后一位加1.
     *      负数
     *          原码：10000000 00000000 00000000 00000101
     *          反码：11111111 11111111 11111111 11111010
     *          补码：11111111 11111111 11111111 11111011
     *  反码规定,只存在一个0;
     *  在计算机中,负数都是以反码的形式存储的,如 11111111 11111111 11111111 11111011 = -5,所以计算也都是基于反码来计算的,如下为5 - 5的计算
     *        00000000 00000000 00000000 00000101
     *     +  11111111 11111111 11111111 11111011
     *     = 100000000 00000000 00000000 00000000  从低位向高位取32位数,剩余去除,最终得到
     *     =  00000000 00000000 00000000 00000000
     * 源码：优点在于换算简单 缺点在于两个零 加减法需要独立运算
     * 反码：有点在于表示清晰 缺点在于两个零 加减法同样需要独立运算
     * 补码：优点在于一个零 范围大  减法可以转为加法 缺点在于理解困难
     *
     */
}

package com.decode;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by Zyh on 2019/9/17.
 */
@SuppressWarnings("all")
@Slf4j
public class DecodeExample1 {

    public static void main(String[] args) throws Exception {
        String inputFile = "netty/DecodeExample1_In.txt";
        String outputFile = "netty/DecodeExample2_Out.txt";


        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile, "rw");

        // 获取文件内容长度
        long inputLength = new File(inputFile).length();
        log.info("读取的文件字节长度：{}", inputLength);

        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        // 获得文件系统的文件的内存映射 - 映射出来的内容存储在ByteBuffer中
        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);

        log.info("=============charset所有的可用编码============================");
        Charset.availableCharsets().forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
        log.info("=============charset所有的可用编码============================");
        /**
         * 获得到utf-8的编码器和解码器
         *  utf8下,传递内容有中文时没有出现乱码;当使用iso-8859-1时有中文也没有出现乱码.
         *  这是因为由于iso-8859-1是以1字节(8bit)进行存储的,并没有丢失一个字节中的任何
         *  bit.
         *  原因：
         *  源文件在磁盘中是以utf8进行存储.当使用iso-8859-1对读取的文件流解码并读取到中
         *  文时,由于utf-8是以三个字节进行存储,而iso-8859-1是以一个字节进行解码的,所以
         *  实际上在使用Decoder解码出的CharBuffer中,原来的中文字符一定是被解析成了三个
         *  iso-8859-1编码的字符,并且这三个字符是utf8存储格式下的每个字节的编码在iso-8859-1
         *  中对应的字符.
         *  当重新编码时,由于又是使用iso-8859-1进行编码,即整数倍的字节进行编码,所以字节序列
         *  又被原封不动的被转回成原来的字节序列,并不会打乱原有的编码格式.所以再次使用utf-8
         *  解码时就不会出现中文乱码.出现字符乱码的核心原因有两个：
         *      1 编解码的格式不一致.
         *      2 在转码过程中,字节数组中的字节序列被打乱.
         *
         *  备注：当出现编码格式解析时位数不够的位数时,位数会被直接丢弃.
         */
        Charset charset = Charset.forName("utf-8");
        // Charset charset = Charset.forName("iso-8859-1");
        // byte[] -> String
        CharsetDecoder decoder = charset.newDecoder();
        // String -> byte[]
        CharsetEncoder encoder = charset.newEncoder();

        // 将ByteBuffer解析成CharBuffer
        CharBuffer charBuffer = decoder.decode(inputData);
        // 将Char[]数组转成ByteBuffer,准备写出
        ByteBuffer byteBuffer = encoder.encode(charBuffer);

        // 将ByteBuffer写到Output文件中
        outputFileChannel.write(byteBuffer);

        // 随机访问资源关闭
        inputRandomAccessFile.close();
        outputRandomAccessFile.close();
    }

    /**
     * 字符集编解码分析
     * 1 byte = 8 bit,计算机以bit(位)来存储数据.数据存储在磁盘上时会按照编码格式来存储.这是因为
     * 文件首先是需要人来输入,然后在存储到磁盘上的.既然由人类来输入那么就一定是按照某种编码格式来将
     * 内容转换成字节数组,然后存储起来的.
     * 编码：将字符串(人读得懂的数据)转换成byte[](机器读的懂的数据).
     * 解码：将byte[](机器读的懂的数据)转换成字符串(人读得懂的数据).
     * 由于人读得懂的数据有很多种(多种语言),所以人读的懂的数据与机器读得懂的数据之间的转换
     * 会有很多种转换规则,这种规则被称为编码格式.
     *
     * ASCII(a si ke) - American Standard Code for Information Interchange,美国信息交换标准代码.
     * 最早的编码格式,它主要支持英文的编码.用7bit来表示一个字符,共计可以表示 2^7 = 128种字符.所以
     * 英文使用这种编码格式没有问题.
     *
     * 当表达的语言不止英文的时候,这个时候7bit就不足以表达所有的字符.这个时候就出现了一种新的编码格式.
     * ISO-8859-1 - 在原有的ASCII码的基础上对第八位进行扩充,即用完整的一个字节(8bit)来表示一个字符,
     * 共计可以表示256个字符.并且由于是基于ASCII码进行扩容,所以ISO-8859-1完全兼容ASCII.目前,ASCII
     * 已经完全被ISO-8859-1代替.
     *
     * GB2312(国标) - 由于汉字很多,ISO-8859-1不足以表达所有的汉字.所以中国使用了一种新的编码格式 - GB2312
     * 来表示所有的汉字.它的特点是使用2个字节(16bit)来表示一个汉字.
     *
     * GBK - 由于汉字的生僻字特别多,GB2312在设计的时候并没有考虑生僻字的问题,所以在GB2312的基础上扩容出了
     * GBK.GBK比GB2312能多表达大约2000多个字符,并且GBK由于是对GB2312的扩展,所以完全兼容GB2312.
     *
     * GB18030 - 当GBK表示汉字仍旧不够时,这个时候GB18030被设计出来了.GB18030是对简体中文表述最全的一种
     * 编码格式.
     *
     * Big5 - 以上的中文说的都是简体中文.像台湾等使用繁体中文的地区来说则设计出来了Big5编码格式来提供对繁体
     * 中文的支持.
     *
     * 由此发现,仅针对中文的编码格式就有如此多种,那么像世界上的其他语言都会有其对应的编码格式.如果让操作系统
     * 支持如此多种的编码格式,则显得特别的混乱.所以国际标准化组织针对 字符集种类 混乱的问题,设计出来了一种
     * 支持全球所有字符的编码格式 - unicode.unicode采用了两个字节来表示一个字符.
     * 但是由于unicode对于每个字符都使用两个字节来进行存储.那么这就使得存储空间进行了膨胀.例如像原来的英文
     * 仅仅使用7位就能表述完全的字符,向高位全部填充0达到16位来进行存储,那么原先1M的英文文件现在就需要占用
     * 2M的存储空间来进行存储.所以unicode并不适合于文件的存储.
     *
     * UTF:UTF的全称是unicode translation format(unicode传输格式).unicode本身是一种编码方式,对于
     * 任意一个字符都对应一个编码.而UTF则是一种存储格式,不论是UTF8、UTF16还是UTF32,它们都是unicode的一种
     * 实现方式.
     *
     * UTF-16中的大端和小端
     * 在unicode表示的文件中,存储在磁盘上的每一个文件的最前面会加上一个根本不存在的字符,这个字符称为 - Zero
     * Width No-Break Space.当这个字符表示为0xFEFF时,称这个文件为大端文件(BE);当表示为0xFFFE时称为小端
     * 文件(LE).
     *
     * UTF-8
     * UTF-16对于任意字符都是以两个字节来表示.这个时候就出现了UTF8编码格式,UTF8编码格式的特点是变长字节表示
     * 形式.常见的像英文等字符的编码格式,utf8和ASCII与ISO-8859-1的表示方式是一样的,即采用一个字节进行存储,
     * 所以说完全兼容.对于中文来说,utf8一般会通过三个字节来表示一个中文.
     * 简而言之,UTF-8使用变长字节的特点既保证了能够高效的在磁盘上存储,又能够表示全世界所有的字符.所以现在国际
     * 基本都通用UTF-8.
     *
     * BOM - 字节序标记(Byte Order Mark)
     * BOM就类似于大端小端等实际不存在的字符的存储形式.它的作用就是在文件开头对文件进行一种标识.实际上BOM是
     * 一个历史遗留问题.对于主流操作系统像Linux,Mac等系统存储文件时根本不需要使用BOM来标识,因为UTF8文件根
     * 本就不需要使用BOM来进行标识.只有windows才会需要使用BOM进行标识.BOM有个问题就是说如果对于某些读取文
     * 件的软件根本就没有考虑BOM的问题,那么当进行读取时读取到第一个编码就发现根本就没有对应的字符,那么这个时
     * 候文件就根本无法继续读下去了.
     *
     */
}
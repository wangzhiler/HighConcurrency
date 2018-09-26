package com.high_concurrency.hc_008_containers;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * 有界队列
 * 能装的元素数量固定
 */
public class T06_ArrayBlockingQueue {
    static BlockingQueue<String> strs =   new ArrayBlockingQueue<>(10);

    static Random r = new Random();

    public static void main(String[] args) throws Exception {
        for(int i=0; i<10; i++) {
            strs.put("a" + i);
        }

        strs.put("aaa");  //满了就会等待，程序阻塞
//        strs.add("aaa"); //报异常：Queue full
//        strs.offer("aaa"); //不报异常 返回值告诉你加成功没
//        strs.offer("aaa", 1, TimeUnit.SECONDS); //隔一段时间没加进去，就不加了

        System.out.println(strs);
    }
}

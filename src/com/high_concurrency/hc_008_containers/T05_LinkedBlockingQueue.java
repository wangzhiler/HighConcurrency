package com.high_concurrency.hc_008_containers;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * Producer-Consumer
 * 无界队列
 */
public class T05_LinkedBlockingQueue {
    //使用链表实现的阻塞式容器
    static BlockingDeque<String> strs = new LinkedBlockingDeque<>();
    static Random r = new Random();

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    //put 放入 如果满了就会等待
                    strs.put("a" + i);
                    TimeUnit.MILLISECONDS.sleep(r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "p1").start();

        for(int i=0; i<5; i++) {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        //take 取 如果空了就会等待
                        System.out.println(Thread.currentThread().getName() + " take -" + strs.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "c" + i).start();
        }
    }
}

package com.high_concurrency.hc_001;

import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/6.
 *
 * volatile 关键字，使一个变量在多个线程间可见
 * A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了该变量，则A线程未必知道
 * 使用volatile关键字，会让所有线程都会读到变量的修改值
 *
 * 在下面的代码中，running是存在于堆内存的t对象中
 * 当线程t1开始运行的时候，会把 running值从内存中读到t1线程的工作区，在运行过程中直接使用这个copy，并不会每次都去
 * 读取堆内存，这样，当主线程修改running的值之后，t1线程感知不到，所以不会停止运行
 *
 * 使用volatile,将会强制所有线程都去堆内存中读取running的值
 *
 * volatile并不能保证多个线程共同修改running变量时带来的不一致问题，也就是说volatile(可见性)不能替代synchronized(可见性+原子性)
 *
 */
public class T011_volatile {

    /**
     * 不用volatile只能用synchronized
     * volatile效率高的多得多
     *
     * volatile 无锁同步，保持线程间变量的可见性
     * synchronized 既有可见性又有原子性
     */

    volatile boolean running = true; //对比一下有无volatile的情况下，整个程序运行结果的区别
    void m() {
        System.out.println("m start");
        while (running) {
            /*
            不用volatile 只用sleep也可能可以成功
            本来cpu一直在读从内存中取出的running的缓存，修改内存中running为false的时候，缓存不变一直是true
            如果sleep cpu空出，再次读取running的时候，又从内存中取出，则取出的running为false

            如果CPU一直在忙，可能一直只读cpu，空闲一点的时候，可能去取内存里数据

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
*/
        }
        System.out.println("m end");

    }

    public static void main(String[] args) {
        T011_volatile t = new T011_volatile();
        new Thread(t::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.running=false;
    }
}

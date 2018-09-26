package com.high_concurrency.hc_002;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/6.
 * <p>
 * 曾经的面试题 （淘宝）
 * 实现一个容器，提供两个方法，add size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * <p>
 * 分心下面这个程序，能完成这个功能吗
 *
 * 1） List没有volatile 则无法实现
 * 2） 给list加了volatile之后，t2能够接到通知，但是，t2线程的死循环很浪费cpu，如果不用死循环，怎么做？
 *
 * 3） 使用wait和notify做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是，运用这种方式，必须要保证t2先执行，也就是首先让t2监听才可以
 *
 * 只有wait释放锁，sleep、notify不释放
 *
 * 可以读到输出结果并不是size=5时t2退出，而是t1结束时t2才接收到通知而退出
 *
 * 整个通信过程比较繁琐
 */
public class MyContainer3 {
    //添加volatile 使t2能够得到通知
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer3 c = new MyContainer3();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2启动");
                if (c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束");
                /////////////////////////
                //通知t1继续执行
                lock.notify();
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            System.out.println("t1启动");
            synchronized (lock) {
                for(int i=0; i<10; i++) {
                    c.add(new Object());
                    System.out.println("add" + i);

                    if (c.size() == 5) {
                        lock.notify();
                        /////////////////////////
                        //释放锁，让t2得以执行
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t1").start();

    }
}













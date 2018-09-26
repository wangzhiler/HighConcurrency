package com.high_concurrency.hc_004_producer_consumer;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 使用wait和notifyAll来实现
 *
 */
public class MyContainer1<T> {
    final private LinkedList<T> lists = new LinkedList<T>();
    final private int MAX = 10; //最多10个元素
    private int count = 0;

    public synchronized void put(T t) {
        while (lists.size() == MAX) {
            //想想为什么用while而不是用if？
            //effective java 中说 wait 大部分情况都是跟while一起用的，而不if
            //因为 如果两个线程都在等待一个资源，资源被释放，他们同时被唤醒
            //while 会再检查一遍资源是否可用
            //if 会直接往下执行，list.add(t).两个线程都会执行，这样就出问题了
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lists.add(t);
        ++count;
        this.notifyAll(); //通知消费者线程进行消费
        //如果用notify()会怎么样？
        //有可能会叫醒另一个生产者，然后这生产者就wait了，然后程序就执行不动了
    }

    public synchronized T get() {
        T t=null;
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        count--;
        this.notifyAll();  //通知生产者进行生产
        return t;
    }

    public static void main(String[] args) {
        MyContainer1<String> c = new MyContainer1<>();
        //启动消费者线程
        for(int i=0; i<10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) System.out.println(c.get());
            }, "c" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //启动生产者线程
        for(int i=0; i<2; i++) {
            new Thread(()->{
                for (int j = 0; j < 25; j++) c.put(Thread.currentThread().getName() + " " + j);
            },"p"+i).start();
        }

    }
}

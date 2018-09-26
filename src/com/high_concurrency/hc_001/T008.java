package com.high_concurrency.hc_001;

import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/6.
 *
 * 一个同步方法可以调用另一个同步方法
 * 一个线程以及拥有某个对象的锁，再次申请时仍会得到该对象的锁
 *
 * 也就是说synchronized获得的锁是可重入的
 *
 */
public class T008 {
    synchronized void m1() {
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
    }

    synchronized void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2");

    }
}

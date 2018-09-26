package com.high_concurrency.hc_001;

import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/6.
 * <p>
 * 一个同步方法可以调用另一个同步方法
 * 一个线程以及拥有某个对象的锁，再次申请时仍会得到该对象的锁
 * <p>
 * 也就是说synchronized获得的锁是可重入的
 * <p>
 * 这里是继承中有可能发生的情形，子类调用父类的同步方法
 */
public class T009 {

    synchronized void m() {
        System.out.println("m start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        new TT().m();
    }
}

class TT extends T009 {
    @Override
    synchronized void m() {
        System.out.println("child m start");
        super.m();
        System.out.println("child m end");

    }
}

package com.high_concurrency.hc_006_singleton;

import java.util.Arrays;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * 线程安全的单例模式
 * www.cnblogs.com/xudong-bupt/p/3433643.html
 * 更好地是采用下面的方式，既不用加锁，也能实现懒加载
 *
 *
 */
public class Singleton {
    private Singleton() {
        System.out.println("single");
    }

    private static class Inner {
        private static Singleton s = new Singleton();
    }

    public static Singleton getSingle() {
        return Inner.s;
        //需要的时候才new
    }

    public static void main(String[] args) {
        Thread[] ths = new Thread[200];
        for(int i=0; i<ths.length; i++) {
            ths[i] = new Thread(() -> {
                Singleton.getSingle();
            });
        }
        Arrays.asList(ths).forEach(o->o.start());
    }

}

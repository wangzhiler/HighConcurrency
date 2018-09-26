package com.high_concurrency.hc_005_ThreadLocal;

import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 2018/4/7.
 *
 * ThreadLocal线程局部变量
 */
public class ThreadLocal1 {
    //不写volatile可能发生问题，也可能不发生
    //写了一定不发生
    volatile static Person2 p=new Person2();

    public static void main(String[] args) {
        new Thread(()->{

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(p.name);

        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.name = "lisi";
        }).start();
    }
}


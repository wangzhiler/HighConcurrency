package com.high_concurrency.hc_001;

/**
 * Created by thinkpad on 2018/4/6.
 */
public class T002 {
    private int count=10;

    public void m(){
        synchronized (this) {
            //任何线程要执行下面的代码，必须先拿到this的锁
            //synchronized锁的是一个对象
            count--;
            System.out.println(Thread.currentThread().getName()+"count="+count);
        }
    }

}

package com.high_concurrency.hc_001;

/**
 * Created by thinkpad on 2018/4/6.
 */
public class T003 {
    private int count=10;
    public synchronized void m() {
        //等同于在方法的代码执行时要synchronized(this)
        //方法结束时释放锁
        //并非锁定代码，而是在当前代码锁定对象
        count--;
        System.out.println(Thread.currentThread().getName()+"count="+count);
    }
}

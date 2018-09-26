package com.high_concurrency.hc_001;

/**
 * Created by thinkpad on 2018/4/6.
 */
public class T001 {
    private int count=10;
    private Object o=new Object();

    //锁是new出来的，new出来不干别的，只当锁，太浪费

    public void m(){
        synchronized (o) {
            //任何线程要执行下面的代码，必须先拿到o的锁
            count--;
            System.out.println(Thread.currentThread().getName()+"count="+count);
        }
    }
}

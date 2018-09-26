package com.high_concurrency.hc_001;

/**
 * Created by thinkpad on 2018/4/6.
 */
public class T004 {

    private static int count=10;

    public synchronized static void m() {
        //这里等同于synchronized(T001.class)
        count--;
        System.out.println(Thread.currentThread().getName()+"count="+count);
    }

    public static void mm() {
        synchronized (T004.class) {
            //锁定一个静态方法时，相当于锁定静态类的对象
            //不能在这里写synchronized(this)
            count--;
        }
    }
}

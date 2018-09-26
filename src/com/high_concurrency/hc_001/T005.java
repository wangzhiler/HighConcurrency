package com.high_concurrency.hc_001;

/**
 * Created by thinkpad on 2018/4/6.
 *
 * synchronized相当于原子操作，不能被打断
 */
public class T005 implements Runnable {

    private int count=10;

    @Override
//        public /*synchronized*/ void run() {  则可能会出错
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + "count=" + count);
    }

    public static void main(String[] args) {
        T005 t = new T005();
        for(int i=0; i<5; i++) {
            new Thread(t, "Thread" + i).start();
        }
    }
}

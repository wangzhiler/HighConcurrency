package com.high_concurrency.hc_001;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2018/4/6.
 *
 * 对比上一个程序，可以用synchronized解决，synchronized可以保证可见性和原子性，volatile只能保证可见性
 */
public class T013 {
    int count=0;

    synchronized void m() {
        for(int i=0; i<10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        T013 t = new T013();

        List<Thread> threads = new ArrayList<Thread>();

        for(int i=0; i<10; i++) {
            threads.add(new Thread(t::m, "thread-" + i));
        }

        threads.forEach((o) -> o.start());

        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);

    }
}

















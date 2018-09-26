package com.high_concurrency.hc_001;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2018/4/6.
 *
 * volatile并不能保证多个线程共同修改running变量时所带来的的不一致问题
 * 也就是说volatile不能替代synchronized
 *
 */
public class T012 {

    volatile int count=0;
    void m() {
        for(int i=0 ;i<10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        T012 t = new T012();
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

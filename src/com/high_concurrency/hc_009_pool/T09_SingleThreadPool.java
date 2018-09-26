package com.high_concurrency.hc_009_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by thinkpad on 2018/4/7.
 * 第三种线程池
 * newSingleThreadExecutor
 * 线程池永远只有一个线程，扔五个任务，也只有一个线程
 * 保证任务一定是先后执行的，因为线程本身不一定会顺序执行，所以可以用这个
 *
 */
public class T09_SingleThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        for(int i=0; i<5; i++) {
            final int j=i;
            service.execute(()->{
                System.out.println(j + " " + Thread.currentThread().getName());
            });
        }
    }
}
